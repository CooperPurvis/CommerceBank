import React, {useState} from "react";
import './modalStyle.css';
import '../userdashboard';

export default function AddUser({open, onClose}) {

    const submitNewUser = async (event) => {
        event.preventDefault(); // Prevent form submission

        const jsonBody = {
            "userId": document.getElementsByName('user_id')[0].value,
            "userPassword": document.getElementsByName('user_password')[0].value,
            "userRole": document.getElementsByName('user_role')[0].value,
            "createdBy": JSON.parse(sessionStorage.getItem("userInfo")).userId
        };

        console.log("JSON Body: " + JSON.stringify(jsonBody))

        try {
            const response = await fetch("http://localhost:8080/api/user", {
                method: 'POST',
                headers: {'Content-Type': 'application/json; charset=UTF-8'},
                body: JSON.stringify(jsonBody)
            });

            if (response.ok) {
                console.log('User Created Successfully');

                //Update session storage of user list
                let userArray = JSON.parse(sessionStorage.getItem('users')).users
                let json = await response.json()
                userArray.push(json)
                console.log("Storing jsonArray: " + JSON.stringify(userArray))
                sessionStorage.setItem('users', JSON.stringify({'users' : userArray}))
                alert("Successfully Added User")
                onClose()
            }
            else {
                console.error('Failed to Post User')
                alert("Error Adding User")
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <>
            {/* Create User Popup */}
            <div className={'popupBackground'}>
                <div className={'popup'} id={'createUser'}>
                    <h3 className={"fullWidth"}>Add User</h3>
                    <label className={'halfWidth'}>
                        Choose User Role:
                        <select
                            name={'user_role'}
                            className={'item'}
                            defaultValue={'User'}
                        >
                            <option value={'User'}>User</option>
                            <option value={'Admin'}>Admin</option>
                        </select>
                    </label>
                    <label className={"halfWidth"}>
                    </label>
                    <label className={"halfWidth"}>
                        <h5>User Id:</h5>
                        <input type={"text"} name={"user_id"}/>
                    </label>
                    <label className={"halfWidth"}>
                        <h5>User Password:</h5>
                        <input type={"text"} name={"user_password"}/>
                    </label>
                    <div className={"halfWidth"}>
                        <button className={"popupButton"} id={"cancelButton"} onClick={onClose}>Cancel</button>
                    </div>
                    <div className={"halfWidth"}>
                        <input type={"submit"} value={"Create"} className={"popupButton"} onClick={submitNewUser}/>
                    </div>
                </div>
            </div>
        </>
    )
}
