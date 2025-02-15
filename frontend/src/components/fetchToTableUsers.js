import { useEffect, useState } from "react"

export default function FetchToTableIP() {

    const myStyle = {
        fontWeight: "normal",
        fontSize: "medium",
        textAlign: "left",
        margin: "0",
        padding: "0 0.5em",
        backgroundColor: "white",
        color: "black",
        border: "1px solid black"
    };

    const [users, setUsers] = useState([])
    const [loading, setLoading] = useState(false)
    useEffect(() => {
        setLoading(true)

        const userArray = JSON.parse(sessionStorage.getItem('users')).users
        if(userArray.length === 0) {
            fetch("http://localhost:8080/api/user")
                .then(response => response.json())
                .then(json => {
                    console.log("Loading users from fetch")
                    sessionStorage.setItem('users', JSON.stringify({'users': json}))
                    setUsers(json)
                })
        }
        else {
            console.log("Loading users from session Storage")
            setUsers(userArray)
        }

        setLoading(false)
    }, [])

    function selectUser(userUid){

        //If there's already a selected user, reset the colors to white
        const oldUid = JSON.parse(sessionStorage.getItem('userUid')).userUid
        if(oldUid !== 0) {
            let oldTableRow = document.getElementById(oldUid)
            let oldTableCells = oldTableRow.children

            for (let i = 0; i < oldTableCells.length; i++)
                oldTableCells[i].style.setProperty('background-color', 'white')
            oldTableRow.style.setProperty('background-color', 'white')
        }

        let newTableRow = document.getElementById(userUid)
        let newTableCells = newTableRow.children
        let backgroundColor = 'paleturquoise'

        //If the selected row is already selected, set it to white and set the userUid for session storage to 0
        if(oldUid === userUid) {
            backgroundColor = 'white'
            userUid = 0
        }

        for (let i = 0; i < newTableCells.length; i++)
            newTableCells[i].style.setProperty('background-color', backgroundColor)
        newTableRow.style.setProperty('background-color', backgroundColor)
        sessionStorage.setItem('userUid', JSON.stringify({'userUid' : userUid}))
    }

    return (
        <>
            {loading ? (
                <div>Loading...</div>
            ) : (
                <>
                    {users.map(user => (
                        <tr key={user.userUid} className={'entryRow'} id={user.userUid}
                            onClick={mouseEvent => selectUser(user.userUid, mouseEvent)}>
                            <td style={myStyle}>{user.userId}</td>
                            <td style={myStyle}>{user.userPassword}</td>
                            <td style={myStyle}>{user.userRole}</td>
                            <td style={myStyle}>{user.modifiedAt.replace('T', "_").substring(0, user.modifiedAt.length-7)}</td>
                        </tr>
                    ))}
                </>
            )}
        </>
    )
}