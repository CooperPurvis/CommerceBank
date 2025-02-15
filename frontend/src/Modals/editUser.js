import React, {useState} from "react";
import './modalStyle.css';
import '../userdashboard';

export default function EditUser({open, onClose}) {

    let selectedUserUid = JSON.parse(sessionStorage.getItem('userUid')).userUid

    let selectedUser
    let userArrayIndex
    let userArray = JSON.parse(sessionStorage.getItem('users')).users
    for (let i = 0; i < userArray.length; i++) {
        if (userArray[i].userUid === selectedUserUid) {
            selectedUser = userArray[i]
            userArrayIndex = i
        }
    }


    const editCurrentUser = async (event) => {
        event.preventDefault(); //Prevent form submission

        //Pull values from the form and create a json object for the update request body
        const jsonBody = {
            "userUid" : selectedUser.userUid,
            "userId": document.getElementsByName('user_id')[0].value,
            "userPassword": document.getElementsByName('user_password')[0].value,
            "userRole": document.getElementsByName('user_role')[0].value,
            "createdBy": JSON.parse(sessionStorage.getItem("userInfo")).userId
        }

        try {
            const response = await fetch("http://localhost:8080/api/user", {
                method: 'PUT',
                headers: {'Content-Type': 'application/json; charset=UTF-8'},
                body: JSON.stringify(jsonBody)
            })

            if (response.ok) {
                console.log("User successfully put")

                //Update users session storage
                userArray[userArrayIndex] = await response.json()
                sessionStorage.setItem('users', JSON.stringify({'users': userArray}))

                alert("Successfully Updated User")
                onClose()
            } else {
                console.error('Failed to put User');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }

    return (
        <>
            {/* Create User Popup */}
            <div className={'popupBackground'}>
                <div className={'popup'} id={'createUser'}>
                    <h3 className={"fullWidth"}>Edit User</h3>
                    <label className={'halfWidth'}>
                        Choose User Role:
                        <select
                            name={'user_role'}
                            className={'item'}
                            defaultValue={selectedUser.userRole}
                        >
                            <option value={'User'}>User</option>
                            <option value={'Admin'}>Admin</option>
                        </select>
                    </label>
                    <label className={"halfWidth"}>
                    </label>
                    <label className={"halfWidth"}>
                        <h5>User Id:</h5>
                        <input type={"text"} name={"user_id"} defaultValue={selectedUser.userId}/>
                    </label>
                    <label className={"halfWidth"}>
                        <h5>User Password</h5>
                        <input type={"text"} name={"user_password"} defaultValue={selectedUser.userPassword}/>
                    </label>
                    <div className={"halfWidth"}>
                        <button className={"popupButton"} id={"cancelButton"} onClick={onClose}>Cancel</button>
                    </div>
                    <div className={"halfWidth"}>
                        <input type={"submit"} value={"Amend"} className={"popupButton"} onClick={editCurrentUser}/>
                    </div>
                </div>
            </div>
        </>
    )
}
