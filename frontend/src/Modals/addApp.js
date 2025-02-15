import React, {useState} from "react";
import './modalStyle.css';
import '../userdashboard';

export default function AddApp({ open, onClose}) {

    const submitNewApp = async (event) => {
        event.preventDefault(); //Prevent form submission

        const jsonBody = {
            "applicationId": document.getElementsByName('app_id')[0].value,
            "applicationDescription": document.getElementsByName('app_desc')[0].value,
            "createdBy": JSON.parse(sessionStorage.getItem("userInfo")).userId
        }

        try {
            const response = await fetch("http://localhost:8080/api/application", {
                method: 'POST',
                headers: {'Content-Type': 'application/json; charset=UTF-8'},
                body: JSON.stringify(jsonBody)
            })

            if (response.ok) {
                console.log("Application Created Successfully")

                //Update session storage of applications list
                let applicationArray = JSON.parse(sessionStorage.getItem('applications')).applications
                let json = await response.json()
                applicationArray.push(json)
                console.log("Storing jsonArray: " + JSON.stringify(applicationArray))
                sessionStorage.setItem('applications', JSON.stringify({'applications' : applicationArray}))

                //Reset list of AppIds
                sessionStorage.removeItem("appIds")

                alert("Successfully Added Application")
                onClose()
            }
            else {
                console.log("Failed to Post Application")
                alert("Error Adding Application")
            }
        }
        catch(error) {
            console.log(error)
        }
    };

    return (
        <>
            {/* Create Application Popup */}
            <div className={'popupBackground'}>
                <div className={'popup'} id={'addApplication'}>
                    <h3 className={"fullWidth"}>Add Application</h3>
                    <form>
                        <label className={"fullWidth"}>
                            <h5>Application Id:</h5>
                            <input type={"text"} name={"app_id"}/>
                        </label>
                        <label className={"fullWidth"}>
                            <h5>Application Description:</h5>
                            <textarea name={"app_desc"}></textarea>
                        </label>
                        <div className={"halfWidth"}>
                            <button className={"popupButton"} id={"cancelButton"} onClick={onClose}>Cancel</button>
                        </div>
                        <div className={"halfWidth"}>
                            <input type={"submit"} value={"Create"} className={"popupButton"} onClick={submitNewApp}/>
                        </div>
                    </form>
                </div>
            </div>
        </>
    )
}
