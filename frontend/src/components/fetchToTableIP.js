import React, {useEffect, useState} from "react";
// import {getValue} from "@testing-library/user-event/dist/utils";

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

    const [ipEntries, setIpEntry] = useState([])
    const [loading, setLoading] = useState(false)
    useEffect(() => {
        setLoading(true)

        const ipEntryArray = JSON.parse(sessionStorage.getItem('ipEntries')).ipEntries
        if(ipEntryArray.length === 0) {
            fetch("http://localhost:8080/api/ipEntry/get", {
                method: 'POST',
                headers: {'content-type': 'application/json; charset=UTF-8'},
                body: JSON.stringify({"ipEntryUids": []})
            })
                .then(response => response.json())
                .then(json => {
                    console.log("Loading IPs from fetch")
                    sessionStorage.setItem('ipEntries', JSON.stringify({'ipEntries' : json}))
                    setIpEntry(json)
                })
        }
        else {
            console.log("Loading IPs from Session Storage")
            setIpEntry(ipEntryArray)
        }

        setLoading(false)
    } , [])


    const selectEntry = (ipEntryUid, event) => {
        let selectedIpEntries = Array.from(JSON.parse(sessionStorage.getItem('ipEntryUids')).ipEntryUids)

        let tableRow = document.getElementById(ipEntryUid)
        let tableCells = tableRow.children
        let tableRowColor = tableRow.style.getPropertyValue('background-color')

        if(tableRowColor === 'white' || tableRowColor === '') {
            for (let i = 0; i < tableCells.length; i++)
                tableCells[i].style.setProperty('background-color', 'paleturquoise')
            tableRow.style.setProperty('background-color', 'paleturquoise')
            selectedIpEntries.push(ipEntryUid)
        }

        else {
            for(let i = 0; i < tableCells.length; i++)
                tableCells[i].style.setProperty('background-color', 'white')
            tableRow.style.setProperty('background-color', 'white')
            selectedIpEntries.splice(selectedIpEntries.indexOf(ipEntryUid), 1)
        }
        console.log(selectedIpEntries)

        sessionStorage.setItem('ipEntryUids', JSON.stringify({'ipEntryUids' : selectedIpEntries}))
    }


    return (
        <>
            {loading ? (
                <div>Loading...</div>
            ) : (
                <>
                    {ipEntries.map(ipEntry => (
                        <tr key={ipEntry.ipEntryUid} className={"entryRow"} id={ipEntry.ipEntryUid}
                            onClick={mouseEvent => selectEntry(ipEntry.ipEntryUid, mouseEvent)}>
                            <td className={"entryCell"} style={myStyle}>{ipEntry.application.applicationId}</td>
                            <td className={"entryCell"} style={myStyle}>{ipEntry.sourceHostName}</td>
                            <td className={"entryCell"} style={myStyle}>{ipEntry.sourceIpAddress}</td>
                            <td className={"entryCell"} style={myStyle}>{ipEntry.destinationHostName}</td>
                            <td className={"entryCell"} style={myStyle}>{ipEntry.destinationIpAddress}</td>
                            <td className={"entryCell"} style={myStyle}>{ipEntry.destinationPort}</td>
                            <td className={"entryCell"} style={myStyle}>{ipEntry.ipStatus}</td>
                            <td className={"entryCell"} style={myStyle}>{ipEntry.modifiedAt.replace('T', "_").substring(0, ipEntry.modifiedAt.length-7)}</td>
                        </tr>
                    ))}
                </>
            )}
        </>
    )
}