import React, {useEffect, useState} from "react";
import { useNavigate } from 'react-router-dom'
import AddUser from "./Modals/addUser";
import EditUser from "./Modals/editUser";
import AssignApps from "./Modals/assignApps";
import FetchToTableUsers from "./components/fetchToTableUsers";
import logo from "./images/commerceLogo.png";
import back from "./images/BackButton.png";

export default function UserManagement() {
    const navigate = useNavigate()

    const resetUserUid = () => {
        sessionStorage.setItem('userUid', JSON.stringify({'userUid' : 0}))
        console.log("userUid in session storage: " + sessionStorage.getItem('userUid'));
    }


    const [addUser, setAddUser] = useState(false);


    const [editUser, setEditUser] = useState(false);
    const toggleEditUser = () => {
        const userUid = JSON.parse(sessionStorage.getItem('userUid')).userUid
        if(userUid !== 0)
            setEditUser(true)
        else
            alert("Please select a User to Edit")
    }

    const [assignApps, setAssignApps] = useState(false);
    const toggleAssignApps = () => {
        const userUid = JSON.parse(sessionStorage.getItem('userUid')).userUid
        if(userUid !== 0)
            setAssignApps(true)
        else
            alert("Please select One User Assign Apps")
    }

    async function deleteSelectedUser(userUid) {
        const response
            = await fetch("http://localhost:8080/api/user", {
            method: 'DELETE',
            headers: {'content-type': 'application/json; charset=UTF-8'},
            body: JSON.stringify({'userUid': userUid})
        })
        try {
            if(response.ok) {
                //Update session storage for selected UserUid
                sessionStorage.setItem('userUid', JSON.stringify({'userUid' : 0}))

                //Update session storage of all users
                const userArray = JSON.parse(sessionStorage.getItem('users')).users
                console.log("Searching through array: " + userArray)

                for(let i = 0; i < userArray.length; i++) {
                    if(userArray[i].userUid === userUid) {
                        userArray.splice(i, 1)
                        console.log("Users after splice: " + JSON.stringify(userArray))
                    }
                }
                sessionStorage.setItem('users', JSON.stringify({'users' : userArray}))


                //Reload page to update table
                window.location.reload()
                alert("Successfully Deleted User")
            }
            else
                alert("User Not Found")
        }
        catch(error) {
            alert(error.message)
        }
    }

    const deleteClick = async () => {
        //Call Defined Function to delete applicationUid
        try {
            let userUid = JSON.parse(sessionStorage.getItem('userUid')).userUid
            if(userUid === 0) {
                alert("Please Select a User to Delete")
                return
            }

            await deleteSelectedUser(userUid)
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
        resetUserUid();
    }, [])

    return (
        <>
            <div className={'headerContainer'}>
                <img src={logo} className="App-logo" alt="logo"/>
                <h1>IP Whitelist App</h1>
                <input className={'commerceButton'} type="button" onClick={logout} value={'Log out'}/>
            </div>
            <div className={'bodyContainer'}>
                {addUser && (
                    <AddUser open={addUser} onClose={() => {
                        setAddUser(false);
                        window.location.reload()
                    }}/>
                )}
                {editUser && (
                    <EditUser open={editUser} onClose={() => {
                        setEditUser(false);
                        window.location.reload()
                    }}/>
                )}
                {assignApps && (
                    <AssignApps open={assignApps} onClose={() => {
                        setAssignApps(false);
                        window.location.reload()
                    }}/>
                )}
                <h2 className={"headerContainer"}>User List</h2>
                <div className='stickyContainer'>
                    <input className={'commerceButton'} type="button" onClick={() => {
                        setAddUser(true)
                    }}
                           value={'Add User'}/>
                    <input className={'commerceButton'} type="button" onClick={toggleEditUser}
                           value={'Edit User'}/>
                    <input className={'commerceButton'} type="button" onClick={toggleAssignApps}
                           value={'Assign Apps'}/>
                    <input className={'commerceButton'} type="button" onClick={deleteClick}
                           value={'Delete User'}/>
                </div>
                <div className={'infoContainer'}>
                    <table>
                        <thead className={'stickyContainer'}>
                        <tr>
                            <th>User ID</th>
                            <th>User Password</th>
                            <th>User Role</th>
                            <th>Modified At</th>
                        </tr>
                        </thead>
                        <tbody>
                        <FetchToTableUsers/>
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