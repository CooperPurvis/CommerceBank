import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import logo from "./images/commerceLogo.png";
import passwordHiddenIcon from "./images/hidePassword.png"
import passwordVisibleIcon from "./images/showPassword.png"

export default function Login() {
    const [uid, setUid] = useState('')
    const [password, setPassword] = useState('')
    const [uidError, setUidError] = useState('')
    const [passwordError, setPasswordError] = useState('')
    const [passwordVisible, setPasswordVisible] = useState(false);


    const navigate = useNavigate()

    const onButtonClick = async () => {
        // Set initial error values to empty
        setUidError('')
        setPasswordError('')

        // Check if the user has entered both fields correctly
        if ('' === uid) {
            setUidError('Please enter your user id')
            return
        }


        if ('' === password) {
            setPasswordError('Please enter a password')
            return
        }

        async function loginCheck() {
            const response = await fetch("http://localhost:8080/api/login", {
                method: 'POST',
                headers: {'content-type': 'application/json; charset=UTF-8'},
                body: JSON.stringify({"userId" : uid, "userPassword" : password})
            })
            try {
                const json = await response.json();

                sessionStorage.setItem('userInfo', JSON.stringify(json))
                sessionStorage.setItem('ipEntries', JSON.stringify({'ipEntries' : []}))

                let userRole = json.userRole;
                //sessionStorage.setItem('selectedIpEntries', [].toString())
                if(userRole === "User" || userRole === "user") {
                    navigate("/userdashboard")
                }
                else if(userRole === "Admin" || userRole === "admin") {
                    sessionStorage.setItem('users', JSON.stringify({'users' : []}))
                    sessionStorage.setItem('applications', JSON.stringify({'applications' : []}))
                    navigate("/admindashboard")
                }

                return json.result;
            }
            catch (error) {
                setPasswordError("Sorry, Invalid Login")
                console.log(error)
            }
        }
        await loginCheck()
    }

    return (
        <>
            <div className={'headerContainer'}>
                <img src={logo} className="App-logo" alt="logo"/>
                <h1>IP Whitelist App</h1>
            </div>
            <div className={'bodyContainerLogin'}>
                <div className={'indentContainer'}>
                    <div className={'titleContainer paddingTop'}>
                        <div>Login</div>
                    </div>
                    <div className={'inputContainer'}>
                        <input
                            value={uid}
                            placeholder="Username"
                            onChange={(ev) => setUid(ev.target.value)}
                            className={'inputBox'}
                        />
                        <label className="errorLabel">{uidError}</label>
                    </div>
                    <div className={'inputContainer'}>
                        <input
                            type={passwordVisible ? 'text' : 'password'}
                            value={password}
                            placeholder="Password"
                            onChange={(ev) => setPassword(ev.target.value)}
                            className={'inputBox'}
                        />
                        <img
                            src={passwordVisible ? passwordVisibleIcon : passwordHiddenIcon}
                            alt="toggle password visibility"
                            className="passwordVisibilityIcon"
                            onClick={() => setPasswordVisible(!passwordVisible)}
                        />
                        <label className="errorLabel">{passwordError}</label>
                    </div>
                    <input className={'commerceButton'} type="button" onClick={onButtonClick} value={'Log in'}/>
                </div>
            </div>
        </>
    );
}