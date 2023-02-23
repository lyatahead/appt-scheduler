import axios from "axios";
import React, { useEffect, useState, Fragment } from "react";

export default function EditDoctor() {
    const [doctID, setDoct] = useState("")
    const [putDoct, setDoctor] = useState({
        firstName: '',
        lastName: ''
    })
    const [error, setErrors] = useState("")


    const handleChange = (event) => {
        setDoctor({...putDoct, [event.target.name]: event.target.value});                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
    };

    const handleChangeID = (e) => {
        setDoct(e.target.value);
    }

    const handleSubmit = (event) => {
        axios.put(`http://localhost:8080/api/updateDoctor/${doctID}`, putDoct)
            .then(res => {
                if (res.status !== 200) {
                  console.log('Bad Status', res.status)
                  setErrors(res.status)
                }
                else {
                  const doct = res.data;
                  console.log(doct);
                  setDoctor(doct);
                }
              })

    };

        return (
            <Fragment>
                <h1>Edit Doctor</h1>
                <form onSubmit={handleSubmit} >
                    <label>
                        Doctor ID:
                        <input  type="text" name="doctID" value={doctID} onChange={handleChangeID} />
                        First Name: 
                        <input type="text" name="firstName" value={putDoct.firstName} onChange={handleChange} />
                        Last Name:
                        <input type="text" name="lastName" value={putDoct.lastName} onChange={handleChange} />
                    </label>
                    <button type="submit">Edit</button>
                </form>
            </Fragment>
        );

}