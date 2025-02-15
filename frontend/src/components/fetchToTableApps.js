import React, { useEffect, useState } from "react"

export default function FetchToTableApps() {
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

    const [applications, setApplications] = useState([])
    const [loading, setLoading] = useState(false)
    useEffect(() => {
        setLoading(true)

        const applicationArray = JSON.parse(sessionStorage.getItem('applications')).applications
        if(applicationArray.length === 0) {
            fetch("http://localhost:8080/api/application")
                .then(response => response.json())
                .then(json => {
                    console.log("Loading applications from fetch")
                    sessionStorage.setItem('applications', JSON.stringify({'applications': json}))
                    setApplications(json)
                })
        }
        else {
            console.log("Loading applications from session Storage")
            setApplications(applicationArray)
        }

        setLoading(false)
    }, [])

    function selectApp(appUid){

        //If there's already a selected app, reset the colors to white
        const oldUid = JSON.parse(sessionStorage.getItem('appUid')).appUid
        if(oldUid !== 0) {
            let oldTableRow = document.getElementById(oldUid)
            let oldTableCells = oldTableRow.children

            for (let i = 0; i < oldTableCells.length; i++)
                oldTableCells[i].style.setProperty('background-color', 'white')
            oldTableRow.style.setProperty('background-color', 'white')
        }

        let newTableRow = document.getElementById(appUid)
        let newTableCells = newTableRow.children
        let backgroundColor = 'paleturquoise'

        //If the selected row is already selected, set it to white and set the appUid for session storage to 0
        if(oldUid === appUid) {
            backgroundColor = 'white'
            appUid = 0
        }

        for (let i = 0; i < newTableCells.length; i++)
            newTableCells[i].style.setProperty('background-color', backgroundColor)
        newTableRow.style.setProperty('background-color', backgroundColor)
        sessionStorage.setItem('appUid', JSON.stringify({'appUid' : appUid}))
    }


    return (
        <>
            {loading ? (
                <div>Loading...</div>
            ) : (
                <>
                    {applications.map(application => (
                        <tr key={application.applicationUid} className={'entryRow'} id={application.applicationUid}
                            onClick={mouseEvent => selectApp(application.applicationUid, mouseEvent)}>
                            <td style={myStyle}>{application.applicationId}</td>
                            <td style={myStyle}>{application.applicationDescription}</td>
                            <td style={myStyle}>{application.modifiedAt.replace('T', "_").substring(0, application.modifiedAt.length-7)}</td>
                        </tr>
                    ))}
                </>
            )}
        </>
    )
}