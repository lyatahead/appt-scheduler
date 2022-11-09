package com.example.springboot.controllers;

import com.example.springboot.model.Appointment;
import com.example.springboot.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AppointmentControllerTest {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    AppointmentController appointmentController;

    Appointment testAppt = new Appointment("12/12/2022", "8:45");
    Appointment testAppt2 = new Appointment("12/05/2022", "8:30");

    @BeforeEach
    void setUp() {
        appointmentRepository.deleteAll();
    }

    @Test
    void createAppointment() {
        Appointment testAppt = appointmentController.createAppointment(new Appointment("12/02/2022", "8:40"));
        assertTrue(appointmentRepository.findById(testAppt.getId()).isPresent());
    }

    @Test
    void getAllAppointments() {
        assertTrue(appointmentController.getAllAppointments().isEmpty());
        appointmentRepository.save(testAppt);
        assertFalse(appointmentController.getAllAppointments().isEmpty());
        appointmentRepository.save(testAppt2);
        assertEquals(2, appointmentController.getAllAppointments().size());

    }

    @Test
    void getSpecificAppointment() {
        appointmentRepository.save(testAppt2);
        Optional<Appointment> tempAppt = appointmentController.getSpecificAppointment(testAppt2.getId());
        assertTrue(tempAppt.isPresent());
        assertEquals(testAppt2, tempAppt.get());

    }

    @Test
    void deleteAppointment() {
        appointmentRepository.save(testAppt);
        appointmentController.deleteAppointment(testAppt.getId());
        assertTrue(appointmentRepository.findById(testAppt.getId()).isEmpty());
    }
}
