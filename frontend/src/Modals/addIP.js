import React, {useEffect, useState} from "react";
import './modalStyle.css';
import '../userdashboard';

export default function AddIP ({ open, onClose }) {

    const [applications, setApplications] = useState([]);
    useEffect(() => {
        const userApplications = sessionStorage.getItem('userApplications');
        //Use existing userApplications
        if (userApplications) {
            const parsedUserApplications = JSON.parse(userApplications);
            setApplications(parsedUserApplications);
            console.log("Loaded user Applications from Session Storage");
        }
        //Do a fetch for the userApplications
        else {
            const userId = JSON.parse(sessionStorage.getItem('userInfo')).userId
            fetch("http://localhost:8080/api/userApplication/get", {
                method: 'POST',
                headers: {'content-type': 'application/json; charset=UTF-8'},
                body: JSON.stringify({"user" : {'userId' : userId}})
            })
                .then(response => response.json())
                .then(json => {
                    console.log("Loading userApplications from Fetch")
                    sessionStorage.setItem('userApplications', JSON.stringify(json))
                    setApplications(json)
                })
        }
    }, [])

    if (!open) {return null}

    const submitNewIP = async (event) => {
        event.preventDefault(); // Prevent form submission

        const appId = document.getElementById('app_id').value;
        const srcHost = document.getElementsByName('src_host')[0].value;
        const srcIp = document.getElementsByName('src_ip')[0].value;
        const dstHost = document.getElementsByName('dst_host')[0].value;
        const dstIp = document.getElementsByName('dst_ip')[0].value;
        const dstPort = document.getElementsByName('dst_port')[0].value;
        const appStatus = document.getElementById('app_status').value;

        const jsonBody = {
            "application": {
                "applicationId": appId
            },
            "sourceHostName": srcHost,
            "sourceIpAddress": srcIp,
            "destinationHostName": dstHost,
            "destinationIpAddress": dstIp,
            "destinationPort": dstPort,
            "ipStatus": appStatus,
            "createdBy": JSON.parse(sessionStorage.getItem("userInfo")).userId
        };

        try {
            const response = await fetch("http://localhost:8080/api/ipEntry", {
                method: 'POST',
                headers: {'Content-Type': 'application/json; charset=UTF-8'},
                body: JSON.stringify(jsonBody)
            })

            if (response.ok) {
                console.log('IP entry successfully posted')
                let ipEntries = JSON.parse(sessionStorage.getItem('ipEntries')).ipEntries
                let json = await response.json()
                ipEntries.push(json)
                sessionStorage.setItem('ipEntries', JSON.stringify({'ipEntries' : ipEntries}))
                alert("Successfully Added IP Entry")
                onClose()
            }
            else {
                console.error('Failed to post IP entry');
                alert("Error Adding IP Entry")
            }
        } catch (error) {
            console.error(error);
        }
    };


    return (
        <>
            {/* Create IP Popup */}
            <div className={'popupBackground'}>
                <div className={'popup'} id={'createIP'}>
                    <h3 className={"fullWidth"}>Add IP</h3>
                    <label className={'halfWidth'}>
                        Choose App ID:
                        <select
                            name={'applicationId'}
                            className={'item'}
                            id={'app_id'}
                            defaultValue={applications[0]} // Set default value to the first application ID
                        >
                            {applications.map(appId => (
                                <option key={appId} value={appId}>{appId}</option>
                            ))}
                        </select>
                    </label>
                    <label className={'halfWidth'}>
                        Set Status:
                        <select
                            name={'app_status'}
                            className={'item'}
                            id={'app_status'}
                            defaultValue={'Active'}
                        >
                            <option value={'Active'}>Active</option>
                            <option value={'Inactive'}>Inactive</option>
                        </select>
                    </label>
                        <div className={'halfWidth'} id={'sourceInfo'}>
                            <h4>Source</h4>
                            <label>
                                <h5>Hostname:</h5>
                                <input type={"text"} name={"src_host"}/>
                            </label>
                            <label>
                                <h5>IP Address:</h5>
                                <input type={"text"} name={"src_ip"}

                                       pattern={"^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"}/>
                            </label>
                        </div>

                        <div className={"halfWidth"} id={"destinationInfo"}>
                            <h4>Destination</h4>

                            <label>
                                <h5>Hostname:</h5>
                                <input type={"email"} name={"dst_host"}/>
                            </label>
                            <label>
                                <h5>IP Address:</h5>
                                <input type={"text"} name={"dst_ip"}

                                       pattern={"^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"}/>
                            </label>
                            <label>
                                <h5>Port:</h5>
                                <input type={"text"} name={"dst_port"}

                                       pattern={"^(6553[0-5]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{0,3})$"}/>
                            </label>
                        </div>
                        <div className={"halfWidth"}>
                            <button className={"popupButton"}
                                    id={"cancelButton"} onClick={onClose}>Cancel</button>
                        </div>
                        <div className={"halfWidth"}>
                            <button className={"popupButton"}
                                    type="submit" onClick= {submitNewIP}>Create</button>
                        </div>
                </div>
            </div>
        </>
    )



}
