package com.example.springboot.controllers;

import com.example.springboot.model.Appointment;
import com.example.springboot.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping("/createAppointment")
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @GetMapping("/appointmentsList")
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @GetMapping("/appointment/{id}")
    public Optional<Appointment> getSpecificAppointment(@PathVariable(name = "id") Long id) {
        return appointmentRepository.findById(id);
    }

    @DeleteMapping("/deleteAppointment/{id}")
    public void deleteAppointment(@PathVariable("id") Long id) {
        appointmentRepository.deleteById(id);
    }




}
