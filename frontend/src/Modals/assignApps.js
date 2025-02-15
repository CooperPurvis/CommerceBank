import './modalStyle.css';
import '../userdashboard';
import React, {useEffect, useState} from "react";
export default function AssignApps({open, onClose}) {
    const [userAppIds, setUserAppIds] = useState([]);
    const [appIds, setAppIds] = useState([]);

    const myStyle = {
        fontWeight: "normal",
        fontSize: "2.5vh",
        textAlign: "left",
        margin: "0",
        padding: "0 0.5em",
        backgroundColor: "white",
        color: "black",
        border: "1px solid black",
        width: "fit-content",
        height: "fit-content"
    };

    //Get the selected UserUid out of session storage
    const selectedUserUid = JSON.parse(sessionStorage.getItem('userUid')).userUid

    //Get the selected UserId out of the array of users
    let selectedUserId
    let userArray = JSON.parse(sessionStorage.getItem('users')).users
    for (let i = 0; i < userArray.length; i++)
        if (userArray[i].userUid === selectedUserUid)
            selectedUserId = userArray[i].userId

    const getUserAppIds = () => {
        //Get a list of User Application Ids
        console.log("Getting User Applications with id: " + selectedUserId)
        //Get the selected User's assigned appIds
        fetch("http://localhost:8080/api/userApplication/get", {
            method: 'POST',
            headers: {'content-type': 'application/json; charset=UTF-8'},
            body: JSON.stringify({"user" : {'userId' : selectedUserId}})
        })
            .then(response => response.json())
            .then(json => {
                console.log("Returned User Apps from Fetch: " + JSON.stringify(json))
                setUserAppIds(json)
                console.log("After setUserAppIds: " + JSON.stringify(userAppIds))
            })
    }

    const getDisplayAppIds = () => {
        //Get a complete list of appIds
        const applications = sessionStorage.getItem('appIds')
        //Use existing appIds
        if (applications) {
            let parsedApplications = JSON.parse(applications);

            parseOutDuplicates(parsedApplications)
            console.log("Loaded List of App Ids from Session Storage");
        }
        //Do a fetch for AppIds
        else {
            fetch("http://localhost:8080/api/application/idList")
                .then(response => response.json())
                .then(json => {
                    console.log("Loading List of App Ids from Fetch")

                    sessionStorage.setItem('appIds', JSON.stringify(json))
                    parseOutDuplicates(json)
                })
        }
    }

    const parseOutDuplicates = (json) => {
        console.log("Parsed Apps Before Loop: " + JSON.stringify(json))

        console.log("Before Loop. User Apps: " + JSON.stringify(userAppIds))

        //Remove AppIds already assigned to the user
        for(let i = 0; i < json.length; i++)
            for(let j = 0; j < userAppIds.length; j++)
                if(userAppIds[j] === json[i])
                    json.splice(i, 1)

        console.log("Parsed Apps After Loop: " + JSON.stringify(json))

        //Set the updated info into session storage and appIds
        setAppIds(json)
    }

    useEffect(() => {
        getUserAppIds()
        getDisplayAppIds()
    }, [])

    if (!open) {return null}

    const addUserApplication = async (event) => {
        event.preventDefault(); // Prevent form submission


        const jsonBody = {
            "user": {
                "userId": selectedUserId
            },
            "application": {
                "applicationId": document.getElementById('app_id').value
            },
            "createdBy": JSON.parse(sessionStorage.getItem("userInfo")).userId
        };

        console.log("JSON Body: " + JSON.stringify(jsonBody))
        try {
            const response = await fetch("http://localhost:8080/api/userApplication", {
                method: 'POST',
                headers: {'Content-Type': 'application/json; charset=UTF-8'},
                body: JSON.stringify(jsonBody)
            })

            if (response.ok) {
                alert("Successfully Assigned Application");
                onClose()
            } else {
                alert("Error Adding User Application")
            }
        } catch (error) {
            console.log(error)
        }
    };

    const deleteUserApp = async (appId, event) => {
        event.preventDefault()

        const jsonBody = {
            "user" : {
                "userId" : selectedUserId
            },
            "application" : {
                "applicationId" : appId
            }
        }

        console.log("jsonBody: " + JSON.stringify(jsonBody))

        try {
            const response = await fetch("http://localhost:8080/api/userApplication", {
                method: 'DELETE',
                headers: {'content-type': 'application/json; charset=UTF-8'},
                body: JSON.stringify(jsonBody)
            });

            if(response.ok) {

                //Remove Deleted appId from userAppIds
                let jsonArray = userAppIds
                for(let i = 0; i < jsonArray.length; i++)
                    if(jsonArray[i] === appId)
                        jsonArray.splice(i, 1)
                setUserAppIds(jsonArray)

                alert("Successfully Deleted User Application")
                onClose()
            }
            else {
                console.log("Failed to DELETE User Application")
                alert("Error Deleting User Application")
            }
        }
        catch (error) {
            console.log(error)
        }
        console.log("Clicked appId: " + appId)
    }

    return (
        <>
            {/* Assign Apps Popup*/}
            <div className={'popupBackground'}>
                <div className={'popup'} id={'assignApps'}>
                    <label className={'fullWidth'}>
                        <label>
                            <h3>Assign Apps For: {selectedUserId}</h3>
                        </label>
                    </label>

                    <label className={'halfWidth'}>
                        <table>
                            <thead className={'stickyContainer'}>
                                    <tr>
                                        <th style={{fontSize:'3vh'}}>Application Id</th>
                                        <th style={{fontSize:'3vh'}}>Delete</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {userAppIds.map(appId => (
                                        <tr key={appId} className={'entryRow'}>
                                            <td className={'entryCell'} style={myStyle}>{appId}</td>
                                            <td className={'entryCell'} style={myStyle}>
                                                <button style={{margin: 'auto', width: "fit-content",
                                                    height: "fit-content", fontSize: '1.5vh'}} className={"popupButton"} id={"cancelButton"}/* was appId*/
                                                        onClick={mouseEvent => deleteUserApp(appId, mouseEvent)}>X
                                                </button>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </label>

                    <label className={'halfWidth'}>
                        Choose App Id to Assign:
                        <select
                            name={'applicationId'}
                            className={'item'}
                            id={'app_id'}
                            defaultValue={appIds[0]} // Set default value to the first application ID
                        >
                            {appIds.map(appId => (
                                <option key={appId} value={appId}>{appId}</option>
                            ))}
                        </select>
                    </label>

                        <div className={"halfWidth"}>
                            <button className={"popupButton"} id={"cancelButton"} onClick={onClose}>Close</button>
                        </div>
                        <div className={"halfWidth"}>
                            <input type={"submit"} value={"Add"} className={"popupButton"}
                                   onClick={addUserApplication}/>
                        </div>
                </div>
            </div>
        </>
    )
}