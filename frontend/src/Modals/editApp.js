import './modalStyle.css';
import '../userdashboard';
export default function EditApp({open, onClose}) {

    let selectedApplicationUid = JSON.parse(sessionStorage.getItem('appUid')).appUid

    let selectedApp
    let appArrayIndex
    let applicationArray = JSON.parse(sessionStorage.getItem('applications')).applications
    for (let i = 0; i < applicationArray.length; i++) {
        if (applicationArray[i].applicationUid === selectedApplicationUid) {
            selectedApp = applicationArray[i]
            appArrayIndex = i
        }
    }


    const editCurrentApp = async (event) => {
        event.preventDefault(); //Prevent form submission

        //Pull values from the form and create a json object for the update request body
        const jsonBody = {
            "applicationUid" : selectedApp.applicationUid,
            "applicationId": document.getElementsByName('app_id')[0].value,
            "applicationDescription": document.getElementsByName('app_desc')[0].value,
            "createdBy": JSON.parse(sessionStorage.getItem("userInfo")).userId
        }

        try {
            const response = await fetch("http://localhost:8080/api/application", {
                method: 'PUT',
                headers: {'Content-Type': 'application/json; charset=UTF-8'},
                body: JSON.stringify(jsonBody)
            })

            if (response.ok) {
                console.log('Application successfully put')

                //Update applications session storage
                applicationArray[appArrayIndex] = await response.json()
                sessionStorage.setItem('applications', JSON.stringify({'applications': applicationArray}))
                console.log("Applications Session Storage updated to: " + sessionStorage.getItem('applications'))

                //Update ipEntries session storage to make it use a fetch
                sessionStorage.setItem('ipEntries', JSON.stringify({'ipEntries' : []}))

                //Reset list of AppIds
                sessionStorage.removeItem("appIds")

                alert("Successfully Updated Application")
                onClose()
            } else {
                console.error('Failed to put Application');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }


    return (
        <>
            {/* Create IP Popup */}
            <div className={'popupBackground'}>
                <div className={'popup'} id={'addApplication'}>
                    <h3 className={"fullWidth"}>Edit Application</h3>

                    <form>
                        <label className={"fullWidth"}>
                            <h5>Application Id:</h5>
                            <input type={"text"}
                                   name={"app_id"}
                                   pattern={"^([0-9]?[0-9])$"}
                                   defaultValue={selectedApp.applicationId}/>
                        </label>
                        <label className={"fullWidth"}>
                            <h5>Application Description:</h5>
                            <textarea name={"app_desc"}
                                      defaultValue={selectedApp.applicationDescription}>
                            </textarea>
                        </label>
                        <div className={"halfWidth"}>
                            <button className={"popupButton"} id={"cancelButton"} onClick={onClose}>Cancel</button>
                        </div>
                        <div className={"halfWidth"}>
                            <button type={"submit"} value={"Amend"} className={"popupButton"} onClick={editCurrentApp}>Submit</button>
                        </div>
                    </form>
                </div>
            </div>
        </>
    )
}
