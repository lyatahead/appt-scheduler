import './App.css';
import React from 'react';
import {Routes, Route, BrowserRouter, Link} from 'react-router-dom';
import DoctorsList from './components/DoctorsList';
import AppointmentsList from './components/AppointmentsList';
import PatientsList from './components/PatientsList';
import AddDoctor from './components/AddDoctor';
import AddAppointment from './components/AddAppointment';
import AddPatient from './components/AddPatient';
import DeleteDoctor from './components/DeleteDoctor';
import SpecificAppointment from './components/SpecificAppointment';
import DeletePatient from './components/DeletePatient';
import DeleteAppointment from './components/DeleteAppointment';
import EditAppointment from './components/EditAppointment';
import EditPatient from './components/EditPatient';
import EditDoctor from './components/EditDoctor';
import SpecificDoctor from './components/SpecificDoctor';
import SpecificPatient from './components/SpecificPatient';

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
              <Link to='/deleteDoctor'>Delete Doctor</Link>
            </li>
            <li>
              <Link to='/specificDoctor'>Get Doctor</Link>
            </li>
            <li>
              <Link to='/editDoctor'>Edit Doctor</Link>
            </li>
            <li>
              <Link to='/addPatient'>Add Patient</Link>
            </li>
            <li>
              <Link to='/patients'>Patients</Link>
            </li>
            <li>
              <Link to='/deletePatient'>Delete Patient</Link>
            </li>
            <li>
              <Link to='/specificPatient'>Get Patient</Link>
            </li>
            <li>
              <Link to='/editPatient'>Edit Patient</Link>
            </li>
            <li>
              <Link to='/addAppointment'>Add Appointment</Link>
            </li>
            <li>
              <Link to='/appointments'>Appointments</Link>
            </li>
            <li>
              <Link to='/deleteAppointment'>Delete Appointment</Link>
            </li>
            <li>
              <Link to='/specificAppointment'>Get Appointment</Link>
            </li>
            <li>
              <Link to='/editAppointment'>Edit Appointment</Link>
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
        <Route path='/deleteDoctor' element={<DeleteDoctor />} />
        <Route path='/deletePatient' element={<DeletePatient />} />
        <Route path='/deleteAppointment' element={<DeleteAppointment />} />
        <Route path='/specificAppointment' element={<SpecificAppointment />} />
        <Route path='/specificPatient' element={<SpecificPatient />} />
        <Route path='/specificDoctor' element={<SpecificDoctor />} />
        <Route path='/editDoctor' element={<EditDoctor />} />
        <Route path='/editPatient' element={<EditPatient />} />
        <Route path='/editAppointment' element={<EditAppointment />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;

