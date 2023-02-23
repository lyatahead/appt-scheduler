import axios from "axios";
import React, { useEffect, useState, Fragment } from "react";

export default function DeleteAppointment() {
    const [appointmentId, setAppointmentId] = useState("")
    
    const handleSubmit = (event) => {
        event.preventDefault();
        console.log({'ID is': appointmentId});

        axios.delete(`http://localhost:8080/api/deleteAppointment/${appointmentId}`, {})
        
    };

    const handleChange = (event) => {
        setAppointmentId(
            event.target.value,
        );
    };
    
        return (
            <Fragment>
                <h1>Delete Appointment</h1>
                <form onSubmit={handleSubmit} >
                    <label>
                        Appointment ID:
                        <input  type="text" name="appointmentId" value={appointmentId} onChange={handleChange} />
                    </label>
                    <button type="submit">Delete</button>
                </form>
            </Fragment>
        );

}

