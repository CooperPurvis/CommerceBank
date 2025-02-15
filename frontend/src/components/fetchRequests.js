import React, { useEffect, useState } from "react";

export default function FetchRequest() {
    const [name, setName] = useState("");

    useEffect(() => {
        const url = "http://localhost:8080/api/user";
        // const body = { name };

        fetch(url, {
            method: "GET",
            // body: JSON.stringify(body),
        })
            .then((response) => response.json())
            .then((data) => console.log(data));
     }) //, [name]);

    return (
        <div>
            <input type="text" value={name} onChange={(e) => setName(e.target.value)} />
            <button onClick={() => setName("")}>Clear</button>
        </div>
    );
};