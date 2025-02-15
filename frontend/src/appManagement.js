import React, {useEffect, useState} from "react";
import { useNavigate } from 'react-router-dom';
import AddApp from "./Modals/addApp";
import EditApp from "./Modals/editApp";
import FetchToTableApps from "./components/fetchToTableApps";
import logo from './images/commerceLogo.png';
import back from "./images/BackButton.png";

export default function AppManagement() {
    const navigate = useNavigate()

    const resetApplicationUid = () => {
        sessionStorage.setItem('appUid', JSON.stringify({'appUid' : 0}))
    }

    const [addApp, setAddApp] = useState(false);


    const [editApp, setEditApp] = useState(false);
    const toggleEditApp = () => {
        const applicationUid = JSON.parse(sessionStorage.getItem('appUid')).appUid
        if(applicationUid !== 0)
            setEditApp(true)
        else
            alert("Please Select an Application to Edit")
    }

    async function deleteSelectedApp(appUid) {
        const response = await fetch("http://localhost:8080/api/application", {
            method: 'DELETE',
            headers: {'content-type': 'application/json; charset=UTF-8'},
            body: JSON.stringify({'applicationUid': appUid})
        })
        try {
            if(response.ok) {
                //Update session storage for selected ApplicationUid
                sessionStorage.setItem('appUid', JSON.stringify({'appUid' : 0}))

                //Update session storage of all applications
                const applicationArray = JSON.parse(sessionStorage.getItem('applications')).applications
                console.log("Searching through array: " + applicationArray)

                for(let i = 0; i < applicationArray.length; i++) {
                    if(applicationArray[i].applicationUid === appUid) {
                        applicationArray.splice(i, 1)
                        console.log("Applications after splice: " + JSON.stringify(applicationArray))
                    }
                }
                sessionStorage.setItem('applications', JSON.stringify({'applications' : applicationArray}))

                //Update ipEntries session storage to make it use a fetch on next load
                sessionStorage.setItem('ipEntries', JSON.stringify({'ipEntries' : []}))

                /**User Applications?*/

                //Reload page to update table
                window.location.reload()
                alert("Successfully Deleted Application")
            }
            else
                alert("Application Not Found")
        }
        catch(error) {
            alert(error.message)
        }
    }

    const deleteClick = async () => {
        //Call Defined Function to delete applicationUid
        try {
            let applicationUid = JSON.parse(sessionStorage.getItem('appUid')).appUid
            if(applicationUid === 0) {
                alert("Please select an Application to Delete")
                return
            }

            await deleteSelectedApp(applicationUid)
        }
        catch(error) {
            console.log(error)
        }
    }

    const logout = () => {
        sessionStorage.clear()
        navigate("/");
    }

    const backClick = () =>{
        navigate("/admindashboard");
    }

    useEffect(() => {
        resetApplicationUid();
    }, [])

    return (
        <>
            <div className={'headerContainer'}>
                <img src={logo} className="App-logo" alt="logo"/>
                <h1>IP Whitelist App</h1>
                <input className={'commerceButton'} type="button" onClick={logout} value={'Log out'}/>
            </div>
            <div className={'bodyContainer'}>
                {addApp && (
                    <AddApp open={addApp} onClose={() => {
                        setAddApp(false);
                        window.location.reload()
                    }}/>
                )}
                {editApp && (
                    <EditApp open={editApp} onClose={() => {
                        setEditApp(false);
                        window.location.reload()
                    }}/>
                )}

                <h2 className={"pageTitle"}>Application List</h2>
                <div className='stickyContainer'>
                    <input className={'commerceButton'} type="button" onClick={() => {
                        setAddApp(true)
                    }}
                           value={'Add App'}/>
                    <input className={'commerceButton'} type="button" onClick={toggleEditApp}
                           value={'Edit App'}/>
                    <input className={'commerceButton'} type="button" onClick={deleteClick}
                           value={'Delete App'}/>
                </div>
                <div className={'infoContainer'}>
                    <table>
                        <thead className={'stickyContainer'}>
                        <tr>
                            <th>Application Id</th>
                            <th>Application Description</th>
                            <th>Modified At</th>
                        </tr>
                        </thead>
                        <tbody>
                        <FetchToTableApps/>
                        </tbody>
                    </table>
                </div>
            </div>
            <div onClick={backClick} className={'footerContainer'}>
                <input className={'backPicture'} type="image" src={back} alt={'Back Arrow'}/>
            </div>
        </>
    );
}