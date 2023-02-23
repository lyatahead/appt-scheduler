import axios from "axios";
import React, { useEffect, useState, Fragment } from "react";

export default function SpecificDoctor() {
    const [doctID, setPost] = useState("")
    const [error, setErrors] = useState("")

    const handleChange = (event) => {
        setPost(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        axios.get(`http://localhost:8080/api/doctor/${doctID}`, {})
        .then(res => {
            if (res.status !== 200) {
              console.log('Bad Status', res.status)
              setErrors(res.status)
            }
            else {
              //const apptID = res.data;
              console.log(res.data);
              //setPost(apptID);
            }
          })
    };

   
    
    

        return (
            <Fragment>
                <h1>Get Specific Doctor</h1>
                <form onSubmit={handleSubmit}>
                    <label>
                        Specific Doctor ID:
                        <input type="text" name="id" value={doctID} onChange={handleChange} />
                    </label>
                    <button type="submit">Find</button>
                </form>
            </Fragment>
        );

}

