import axios from "axios";
import React, { useEffect, useState, Fragment } from "react";

export default function SpecificPatient() {
    const [patiID, setPost] = useState("")
    const [error, setErrors] = useState("")
    const [patients, setPatients] = useState([])

    const handleChange = (event) => {
        setPost(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        axios.get(`http://localhost:8080/api/patient/${patiID}`, {})
        .then(res => {
            if (res.status !== 200) {
              console.log('Bad Status', res.status)
              setErrors(res.status)
            }
            else {
              const patients = res.data;
              console.log(patients);
              setPatients(patients)
            }
          })
    };

   
    
    

        return (
            <Fragment>
                <h1>Get Specific Patient</h1>
                <form onSubmit={handleSubmit}>
                    <label>
                        Specific Patient ID:
                        <input type="text" name="id" value={patiID} onChange={handleChange} />
                    </label>
                    <button type="submit">Find</button>
                </form>
            </Fragment>
        );

}

