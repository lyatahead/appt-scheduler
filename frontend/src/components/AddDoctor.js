import axios from "axios";
import React, { useEffect, useState, Fragment } from "react";

export default function AddDoctor() {

  const [postDoctor, setPostDoctor] = useState({
    firstName: '',
    lastName: ''
}) 
  const [error, setErrors] = useState("")

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(postDoctor);
    
    axios.post('http://localhost:8080/api/createDoctor', postDoctor)
        .then(res => {
          if (res.status !== 200) {
            console.log('Bad Status', res.status)
            setErrors(res.status)
          }
          else {
            const doctor = res.data;
            console.log(doctor);
            setPostDoctor(doctor);
          }
        })
  }

  const handleChange = (event) => {
    setPostDoctor({ ...postDoctor, [event.target.name]: event.target.value  });
  };

    return (
      <Fragment>
      <h1>Add Doctor</h1>
      <form onSubmit={handleSubmit}>
        <label>
            First Name:
            <input type='text' name='firstName' onChange={handleChange} value={postDoctor.firstName}/>
        </label>
        <label>
            Last Name:
            <input type='text' name='lastName' onChange={handleChange} value={postDoctor.lastName}/>
        </label>
        <button type="submit">Add</button>
      </form>
      </Fragment>
    );
}



