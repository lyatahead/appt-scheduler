import axios from "axios";
import React, { useEffect, useState, Fragment } from "react";

export default function DeleteDoctor() {
    const [doctorId, setDoctorId] = useState("")
    
    const handleSubmit = (event) => {
        event.preventDefault();
        console.log({'ID is': doctorId});

        axios.delete(`http://localhost:8080/api/deleteDoctor/${doctorId}`, {})
        
    };

    const handleChange = (event) => {
        setDoctorId(
            event.target.value,
        );
    };
    
        return (
            <Fragment>
                <h1>Delete Doctor</h1>
                <form onSubmit={handleSubmit} >
                    <label>
                        Doctor ID:
                        <input  type="text" name="doctorId" value={doctorId} onChange={handleChange} />
                    </label>
                    <button type="submit">Delete</button>
                </form>
            </Fragment>
        );

}

