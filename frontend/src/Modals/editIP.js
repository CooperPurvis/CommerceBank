import './modalStyle.css';
import '../userdashboard';
export default function EditIP({open, onClose}) {

    let selectedIpEntryUid = JSON.parse(sessionStorage.getItem('ipEntryUids')).ipEntryUids[0]

    let selectedEntry
    let entriesArrayIndex
    let ipEntriesArray = JSON.parse(sessionStorage.getItem('ipEntries')).ipEntries
    for(let i = 0; i < ipEntriesArray.length; i++) {
        if(ipEntriesArray[i].ipEntryUid === selectedIpEntryUid) {
            selectedEntry = ipEntriesArray[i]
            entriesArrayIndex = i
        }
    }


    const editCurrentIP = async (event) => {
        event.preventDefault(); // Prevent form submission

        //Pull values from the popup for the update request body
        const jsonBody = {
            "application": {
                "applicationId" : selectedEntry.application.applicationId
            },
            "ipEntryUid" : selectedEntry.ipEntryUid,
            "sourceHostName": document.getElementsByName('src_host')[0].value,
            "sourceIpAddress": document.getElementsByName('src_ip')[0].value,
            "destinationHostName": document.getElementsByName('dst_host')[0].value,
            "destinationIpAddress": document.getElementsByName('dst_ip')[0].value,
            "destinationPort": document.getElementsByName('dst_port')[0].value,
            "ipStatus": document.getElementById('app_status').value,
            "createdBy": JSON.parse(sessionStorage.getItem("userInfo")).userId
        }

        try {
            const response = await fetch("http://localhost:8080/api/ipEntry", {
                method: 'PUT',
                headers: {'Content-Type': 'application/json; charset=UTF-8'},
                body: JSON.stringify(jsonBody)
            })

            if (response.ok) {
                console.log('IP entry successfully put');

                //Update ipEntries session storage
                ipEntriesArray[entriesArrayIndex] = await response.json()
                sessionStorage.setItem('ipEntries', JSON.stringify({'ipEntries' : ipEntriesArray}))

                alert("Successfully Updated IP Entry")
                onClose()
            }
            else {
                console.error('Failed to put IP entry');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };


    return (
        <>
            {/* Create IP Popup */}
            <div className={'popupBackground'}>
                <div className={'popup'} id={'createIP'}>
                    <h3 className={"fullWidth"}>Edit IP</h3>
                    <label className={'halfWidth'}>
                        App Id:
                        <label>
                            <h4>{selectedEntry.application.applicationId}</h4>
                        </label>
                    </label>
                    <label className={'halfWidth'}>
                        Set Status:
                        <select
                            name={'app_status'}
                            className={'item'}
                            id={'app_status'}
                            defaultValue={selectedEntry.ipStatus}
                        >
                            <option value={'Active'}>Active</option>
                            <option value={'Inactive'}>Inactive</option>
                        </select>
                    </label>
                        <div className={'halfWidth'} id={'sourceInfo'}>
                            <h4>Source</h4>
                            <label>
                                <h5>Hostname:</h5>
                                <input type={"text"} name={"src_host"} defaultValue={selectedEntry.sourceHostName}/>
                            </label>
                            <label>
                                <h5>IP Address:</h5>
                                <input type={"text"} name={"src_ip"} defaultValue={selectedEntry.sourceIpAddress}
                                       pattern={"^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"}/>
                            </label>
                        </div>

                        <div className={"halfWidth"} id={"destinationInfo"}>
                            <h4>Destination</h4>

                            <label>
                                <h5>Hostname:</h5>
                                <input type={"text"} name={"dst_host"}
                                       defaultValue={selectedEntry.destinationHostName}/>
                            </label>
                            <label>
                                <h5>IP Address:</h5>
                                <input type={"text"} name={"dst_ip"} defaultValue={selectedEntry.destinationIpAddress}
                                       pattern={"^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"}/>
                            </label>
                            <label>
                                <h5>Port:</h5>
                                <input type={"text"} name={"dst_port"} defaultValue={selectedEntry.destinationPort}

                                       pattern={"^(6553[0-5]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[1-9][0-9]{0,3})$"}/>
                            </label>
                        </div>
                        <div className={"halfWidth"}>
                            <button className={"popupButton"} id={"cancelButton"} onClick={onClose}>Cancel</button>
                        </div>
                        <div className={"halfWidth"}>
                            <button type={"submit"} value={"Amend"} className={"popupButton"} onClick={editCurrentIP}>Submit</button>
                        </div>
                </div>
            </div>
        </>
    )
}
