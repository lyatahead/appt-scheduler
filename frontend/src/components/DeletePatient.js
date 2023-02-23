import axios from "axios";
import React, { useEffect, useState, Fragment } from "react";

export default function DeletePatient() {
    const [patientId, setPatientId] = useState("")
    
    const handleSubmit = (event) => {
        event.preventDefault();
        console.log({'ID is': patientId});

        axios.delete(`http://localhost:8080/api/deletePatient/${patientId}`, {})
        
    };

    const handleChange = (event) => {
        setPatientId(
            event.target.value,
        );
    };
    
        return (
            <Fragment>
                <h1>Delete Patient</h1>
                <form onSubmit={handleSubmit} >
                    <label>
                        Patient ID:
                        <input  type="text" name="patientId" value={patientId} onChange={handleChange} />
                    </label>
                    <button type="submit">Delete</button>
                </form>
            </Fragment>
        );

}

