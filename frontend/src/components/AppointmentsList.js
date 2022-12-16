import axios from 'axios';
import React, { useEffect, useState, Fragment } from "react";

export default function AppointmentsList() {
    
    const [appointments, setAppointments] = useState([]) 
    const [error, setErrors] = useState("")
    useEffect(() => {
        axios.get('http://localhost:8080/api/appointmentsList')
        .then(res => {
          if (res.status !== 200) {
            console.log('Bad Status', res.status)
            setErrors(res.status)
          }
          else {
            const appointments = res.data;
            console.log(appointments);
            setAppointments(appointments);
          }
        })
  }, []);

    return (
        <Fragment>
      <h1>Appointments</h1>
        <ul>
          {appointments?.map((a) => (
            <li key={a.id}>{a.appDate}, {a.doctorId}</li>
          ))}
        </ul>
      </Fragment>
    );
}

