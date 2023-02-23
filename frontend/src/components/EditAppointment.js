import axios from "axios";
import React, { useEffect, useState, Fragment } from "react";

export default function EditAppointment() {
    const [apptID, setAppt] = useState("")
    const [putAppt, setAppointment] = useState({
        appDate: '',
        doctorId: ''
    })
    const [error, setErrors] = useState("")


    const handleChange = (event) => {
        setAppointment({...putAppt, [event.target.name]: event.target.value});                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
    };

    const handleChangeapptID = (e) => {
        setAppt(e.target.value);
    }

    const handleSubmit = (event) => {
        axios.put(`http://localhost:8080/api/updateAppointment/${apptID}`, putAppt)
            .then(res => {
                if (res.status !== 200) {
                  console.log('Bad Status', res.status)
                  setErrors(res.status)
                }
                else {
                  const appt = res.data;
                  setAppointment(appt);
                }
              })

    };

        return (
            <Fragment>
                <h1>Edit Appointment</h1>
                <form onSubmit={handleSubmit} >
                    <label>
                        Appointment ID:
                        <input  type="text" name="apptID" value={apptID} onChange={handleChangeapptID} />
                        Appointment Date: 
                        <input type="date" name="appDate" value={putAppt.appDate} onChange={handleChange} />
                        Doctor ID:
                        <input type="text" name="doctorId" value={putAppt.doctorId} onChange={handleChange} />
                    </label>
                    <button type="submit">Edit</button>
                </form>
            </Fragment>
        );

}