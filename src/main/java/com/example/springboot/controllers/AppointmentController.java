package com.example.springboot.controllers;

import com.example.springboot.model.Appointment;
import com.example.springboot.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class AppointmentController {
    @Autowired
    AppointmentRepository appointmentRepository;

    public AppointmentController(@Autowired AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * Create the appointment object
     * @param appointment
     * @return save the appointment into the repository
     */
    @PostMapping("/createAppointment")
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    /**
     * Get a list of all appointments from the repository
     * @return the appointment objects
     */
    @GetMapping("/appointmentsList")
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    /**
     * Get a specific appointment by their appointment ID
     * @param id
     * @return the appointment object
     */
    @GetMapping("/appointment/{id}")
    public Optional<Appointment> getSpecificAppointment(@PathVariable(name = "id") Long id) {
        if (!this.appointmentRepository.findById(id).isPresent()) {
            throw new NoSuchElementException();
        }
        return appointmentRepository.findById(id);
    }

    /**
     * Delete appointment by their appointment ID
     * @param id
     */
    @DeleteMapping("/deleteAppointment/{id}")
    public void deleteAppointment(@PathVariable("id") Long id) {
        appointmentRepository.deleteById(id);
    }

    /**
     * Update appointment in the reposiroty by their appointment ID and body of the API request
     * @param id
     * @param updateAppointment
     * @return save the updated appointment in the repository
     */
    @PutMapping("/updateAppointment/{id}")
    public Appointment updateAppointment(@PathVariable("id") Long id, @RequestBody Appointment updateAppointment) {
        Appointment tempAppointment = appointmentRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        tempAppointment.setAppDate(updateAppointment.getAppDate());
        tempAppointment.setDoctorId(updateAppointment.getDoctorId());
        return appointmentRepository.save(tempAppointment);
    }

    /**
     * Get list of appointments based on the doctor ID
     * @param doctorId
     * @return appointment objects based on doctor ID
     */
    @GetMapping("/appointmentsByDoctor/{doctorId}")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable(name = "doctorId") int doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    /**
     * Delete appointment based on the doctor ID
     * @param doctorId
     */
    @DeleteMapping("/deleteAppointmentByDoctor/{doctorId}")
    public void deleteAppointmentByDoctor(@PathVariable("doctorId") int doctorId) {
        appointmentRepository.deleteByDoctorId(doctorId);
    }
}
