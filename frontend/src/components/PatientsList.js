import axios from 'axios';
import React, { useEffect, useState, Fragment } from "react";

export default function PatientsList() {
    
    const [patients, setPatients] = useState([]) 
    const [error, setErrors] = useState("")
    useEffect(() => {
        axios.get('http://localhost:8080/api/patientsList')
        .then(res => {
          if (res.status !== 200) {
            console.log('Bad Status', res.status)
            setErrors(res.status)
          }
          else {
            const patients = res.data;
            console.log(patients);
            setPatients(patients);
          }
        })
  }, []);

    return (
        <Fragment>
      <h1>Patients</h1>
        <ul>
          {patients?.map((p) => (
            <li key={p.id}>{p.firstName}, {p.lastName}, {p.dateOfBirth}, {p.address}, {p.phoneNumber}</li>
          ))}
        </ul>
      </Fragment>
    );
}

