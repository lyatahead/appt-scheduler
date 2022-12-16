import './App.css';
import React from 'react';
import {Routes, Route, BrowserRouter, Link} from 'react-router-dom';
import DoctorsList from './components/DoctorsList';
import AppointmentsList from './components/AppointmentsList';
import PatientsList from './components/PatientsList';
import AddDoctor from './components/AddDoctor';
import AddAppointment from './components/AddAppointment';
import AddPatient from './components/AddPatient';

function App() {
  return (
    <BrowserRouter>
      <div>
        <nav>
          <ul>
            <li>
              <Link to="/">Home</Link>
            </li>
            <li>
              <Link to='/addDoctor'>Add Doctor</Link>
            </li>
            <li>
              <Link to='/doctors'>Doctors</Link>
            </li>
            <li>
              <Link to='/addPatient'>Add Patient</Link>
            </li>
            <li>
              <Link to='/patients'>Patients</Link>
            </li>
            <li>
              <Link to='/addAppointment'>Add Appointment</Link>
            </li>
            <li>
              <Link to='/appointments'>Appointments</Link>
            </li>
          </ul>
        </nav>
      </div>
      <Routes>
        <Route path='/doctors' element={<DoctorsList />} />
        <Route path='/patients' element={<PatientsList />} />
        <Route path='/appointments' element={<AppointmentsList />} />
        <Route path='/addDoctor' element={<AddDoctor />} />
        <Route path='/addAppointment' element={<AddAppointment />} />
        <Route path='/addPatient' element={<AddPatient />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;

