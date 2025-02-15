import React, {useEffect} from "react";
import { useNavigate } from 'react-router-dom'
import FetchToTableIP from "./components/fetchToTableIP";
import logo from "./images/commerceLogo.png";

export default function AdminDashboard() {
    const navigate = useNavigate()

    const resetIpEntryUids = () => {
        sessionStorage.setItem('ipEntryUids', JSON.stringify({'ipEntryUids' : []}))
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

    const users = () => {
        navigate("/userManagement")
    }

    const applications = () => {
        navigate("/appManagement")
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
                <h2 className={"pageTitle"}>Admin Dashboard</h2>
                <div className='stickyContainer'>
                    <input className={'commerceButton'} type="button" onClick={users}
                           value={'Users'}/>
                    <input className={'commerceButton'} type="button" onClick={applications}
                           value={'Applications'}/>
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