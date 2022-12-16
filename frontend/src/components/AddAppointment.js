import axios from "axios";
import React, { useEffect, useState, Fragment } from "react";

export default function AddAppointment() {

  const [postAppointment, setPostAppointment] = useState({
    appDate: '',
    doctorId: ''
}) 
  const [error, setErrors] = useState("")

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(postAppointment);
    
    axios.post('http://localhost:8080/api/createAppointment', postAppointment)
        .then(res => {
          if (res.status !== 200) {
            console.log('Bad Status', res.status)
            setErrors(res.status)
          }
          else {
            const appointment = res.data;
            console.log(appointment);
            setPostAppointment(appointment);
          }
        })
  }

  const handleChange = (event) => {
    setPostAppointment({ ...postAppointment, [event.target.name]: event.target.value  });
  };

    return (
      <Fragment>
      <h1>Add Appointment</h1>
      <form onSubmit={handleSubmit}>
        <label>
            Appointment Date:
            <input type='date' name='appDate' onChange={handleChange} value={postAppointment.appDate}/>
        </label>
        <label>
            Doctor ID:
            <input type='text' name='doctorId' onChange={handleChange} value={postAppointment.doctorId}/>
        </label>
        <button type="submit">Add</button>
      </form>
      </Fragment>
    );
}



