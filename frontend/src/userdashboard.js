import React, {useEffect, useState} from "react";
import { useNavigate } from 'react-router-dom'
import AddIP from "./Modals/addIP";
import EditIP from "./Modals/editIP";
import FetchToTableIP from "./components/fetchToTableIP";
import logo from "./images/commerceLogo.png";

export default function UserDashboard() {
    const navigate = useNavigate()

    const resetIpEntryUids = () => {
        sessionStorage.setItem('ipEntryUids', JSON.stringify({'ipEntryUids' : []}))
    }

    const [addIP, setAddIP] = useState(false);


    const [editIP, setEditIP] = useState(false);
    const toggleEditIP = () => {
        const ipEntryUids = JSON.parse(sessionStorage.getItem("ipEntryUids"));
        if (ipEntryUids.ipEntryUids.length === 1) {
            setEditIP(true)
        }
        else{
            alert("Please Select 1 Entry to Edit")
        }
    }

    async function deleteSelectedEntry(ipEntryUid) {
        const response = await fetch("http://localhost:8080/api/ipEntry", {
            method: 'DELETE',
            headers: {'content-type': 'application/json; charset=UTF-8'},
            body: JSON.stringify({"ipEntryUid": ipEntryUid})
        })
        try {
            if(response.ok) {
                //Update session storage for selected entries
                resetIpEntryUids()

                //Update session storage for all entries to take out the newly deleted
                const ipEntryArray = JSON.parse(sessionStorage.getItem('ipEntries')).ipEntries
                console.log("Searching through array: " + ipEntryArray)

                for(let i = 0; i < ipEntryArray.length; i++) {
                    if(ipEntryArray[i].ipEntryUid === ipEntryUid) {
                        ipEntryArray.splice(i, 1)
                        console.log("ipEntries after splice: " + JSON.stringify(ipEntryArray))
                    }
                }
                sessionStorage.setItem('ipEntries', JSON.stringify({'ipEntries' : ipEntryArray}))

                //Reload page to update table with correct ip entries
                window.location.reload()
                alert("Successfully Deleted IP Entry")
            }
            else
                alert("Entry not Found")
        }
        catch (error) {
            alert(error.message)
        }
    }

    const deleteClick = async () => {
        //Call Defined Function to delete ipEntryUid
        try {
            let ipEntryUids = JSON.parse(sessionStorage.getItem("ipEntryUids"));
            if(ipEntryUids.ipEntryUids.length !== 1) {
                alert("Please Select One Entry to Delete")
                return
            }

            await deleteSelectedEntry(ipEntryUids.ipEntryUids[0])
        }
        catch(error) {
            console.log(error)
        }
    }

     const exportClick = async () => {
        async function exportToExcel() {
            let fileType = ".xlsx";
            let a = document.createElement("a");
            document.body.appendChild(a);
            a.style = {'display':'none'};

            const response = await fetch("http://localhost:8080/api/ipEntry/export", {
                method: 'POST',
                headers: {'content-type': 'application/json; charset=UTF-8'},
                body: JSON.stringify(JSON.parse(sessionStorage.getItem('ipEntryUids')))
            })
            try {
                let blob = await response.blob()

                a.href = window.URL.createObjectURL(blob);
                a.download = "Ip_Entry_List_" + new Date().toDateString().replaceAll(' ', '_') + fileType;
                a.click();

            }
            catch (error) {console.log(error)}
        }
        await exportToExcel();
    }

    const logout = () => {
        sessionStorage.clear()
        navigate("/");
    }

    useEffect(() => {
        resetIpEntryUids();
    }, [])

    return (
        <>
            <div className={'headerContainer'}>
                <img src={logo} className="App-logo" alt="logo"/>
                <h1>IP Whitelist App</h1>
                <input className={'commerceButton'} type="button" onClick={logout} value={'Log out'}/>
            </div>
            <div className={'bodyContainer'}>
                <AddIP open={addIP} onClose={() => {
                    setAddIP(false);
                    window.location.reload()
                }}/>
                {editIP && (
                    <EditIP open={addIP} onClose={() => {
                        setEditIP(false);
                        window.location.reload()
                    }}/>
                )}
                <h2 className={"headerContainer"}>User Dashboard</h2>
                <div className='stickyContainer'>
                    <input className={'commerceButton'} type="button" onClick={() => {
                        setAddIP(true)
                    }}
                           value={'Add Entry'}/>
                    <input className={'commerceButton'} type="button" onClick={toggleEditIP}
                           value={'Edit Entry'}/>
                    <input className={'commerceButton'} type="button" onClick={deleteClick}
                           value={'Delete Entry'}/>
                    <input className={'commerceButton'} type="button" onClick={exportClick}
                           value={'Export'}/>
                </div>
                <div className={'infoContainer'}>
                    <table>
                        <thead className={'stickyContainer'}>
                        <tr>
                            <th>Application ID</th>
                            <th>Source Hostname</th>
                            <th>Source IP</th>
                            <th>Destination Hostname</th>
                            <th>Destination IP</th>
                            <th>Destination Port</th>
                            <th>Status</th>
                            <th>Modified At</th>
                        </tr>
                        </thead>
                        <tbody>
                        <FetchToTableIP/>
                        </tbody>
                    </table>
                </div>
            </div>
        </>
    );
}