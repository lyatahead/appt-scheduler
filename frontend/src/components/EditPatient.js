import axios from "axios";
import React, { useEffect, useState, Fragment } from "react";

export default function EditPatient() {
    const [patiID, setPat] = useState("")
    const [putPati, setPatient] = useState({
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        address: '',
        phoneNumber: ''
    })
    const [error, setErrors] = useState("")


    const handleChange = (event) => {
        setPatient({...putPati, [event.target.name]: event.target.value});                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
    };

    const handleChangepatiID = (e) => {
        setPat(e.target.value);
    }

    const handleSubmit = (event) => {
        axios.put(`http://localhost:8080/api/updatePatient/${patiID}`, putPati)
            .then(res => {
                if (res.status !== 200) {
                  console.log('Bad Status', res.status)
                  setErrors(res.status)
                }
                else {
                  const pati = res.data;
                  setPatient(pati);
                }
              })

    };

        return (
            <Fragment>
                <h1>Edit Patient</h1>
                <form onSubmit={handleSubmit} >
                    <label>
                        Patient ID:
                        <input  type="text" name="patiID" value={patiID} onChange={handleChangepatiID} />
                        First Name: 
                        <input type="text" name="firstName" value={putPati.firstName} onChange={handleChange} />
                        Last Name:
                        <input type="text" name="lastName" value={putPati.lastName} onChange={handleChange} />
                        Date of Birth:
                        <input type="date" name="dateOfBirth" value={putPati.dateOfBirth} onChange={handleChange} />
                        Address:
                        <input type="text" name="address" value={putPati.address} onChange={handleChange} />
                        Phone Number:
                        <input type="text" name="phoneNumber" value={putPati.phoneNumber} onChange={handleChange} />
                    </label>
                    <button type="submit">Edit</button>
                </form>
            </Fragment>
        );

}