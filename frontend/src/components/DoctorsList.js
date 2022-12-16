import axios from "axios";
import React, { useEffect, useState, Fragment } from "react";

export default function DoctorsList() {

  const [doctors, setDoctors] = useState([]) 
  const [error, setErrors] = useState("")

  useEffect(() => {
        axios.get('http://localhost:8080/api/doctorsList')
        .then(res => {
          if (res.status !== 200) {
            console.log('Bad Status', res.status)
            setErrors(res.status)
          }
          else {
            const doctors = res.data;
            console.log(doctors);
            setDoctors(doctors);
          }
        })
  }, []);

    return (
      <Fragment>
      <h1>Doctors</h1>
        <ul>
          {doctors?.map((d) => (
            <li key={d.id}>{d.firstName}, {d.lastName}</li>
          ))}
        </ul>
      </Fragment>
    );
}



