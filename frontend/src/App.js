import {BrowserRouter, Route, Routes} from 'react-router-dom'
import Login from './login'
import UserDashboard from './userdashboard'
import AdminDashboard from './admindashboard'
import './styles/App.css'
import React, { useState } from 'react'
import UserManagement from "./userManagement";
import AppManagement from "./appManagement";

export default function App() {
    const [loggedIn, setLoggedIn] = useState(false)
    const [uid, setUid] = useState('')

    return (
        <div className={'mainContainer'}>

            <BrowserRouter>
                <Routes>
                    {/*<Route path="/" element={<Home uid={uid} loggedIn={loggedIn} setLoggedIn={setLoggedIn}/>}/>*/}
                    <Route path="/" element={<Login setLoggedIn={setLoggedIn} setUid={setUid}/>}/>
                    <Route path="/userdashboard" element={<UserDashboard/>}/>
                    <Route path="/admindashboard" element={<AdminDashboard/>}/>
                    <Route path="/appManagement" element={<AppManagement/>}/>
                    <Route path="/userManagement" element={<UserManagement/>}/>
                </Routes>
            </BrowserRouter>
        </div>
    )
}


