import axios from "axios";
import React, { useEffect, useState, Fragment } from "react";

export default function AddPatient() {

  const [postPatient, setPostPatient] = useState({
    firstName: '',
    lastName: '',
    dateOfBirth:'',
    address:'',
    phoneNumber:''
}) 
  const [error, setErrors] = useState("")

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(postPatient);
    
    axios.post('http://localhost:8080/api/createPatient', postPatient)
        .then(res => {
          if (res.status !== 200) {
            console.log('Bad Status', res.status)
            setErrors(res.status)
          }
          else {
            const patient = res.data;
            console.log(patient);
            setPostPatient(patient);
          }
        })
  }

  const handleChange = (event) => {
    setPostPatient({ ...postPatient, [event.target.name]: event.target.value  });
  };

    return (
      <Fragment>
      <h1>Add Patient</h1>
      <form onSubmit={handleSubmit}>
        <label>
            First Name:
            <input type='text' name='firstName' onChange={handleChange} value={postPatient.firstName}/>
        </label>
        <label>
            Last Name:
            <input type='text' name='lastName' onChange={handleChange} value={postPatient.lastName}/>
        </label>
        <label>
            Date of Birth:
            <input type='date' name='dateOfBirth' onChange={handleChange} value={postPatient.dateOfBirth}/>
        </label>
        <label>
            Address:
            <input type='text' name='address' onChange={handleChange} value={postPatient.address}/>
        </label>
        <label>
            Phone Number:
            <input type='text' name='phoneNumber' onChange={handleChange} value={postPatient.phoneNumber}/>
        </label>
        <button type="submit">Add</button>
      </form>
      </Fragment>
    );
}



