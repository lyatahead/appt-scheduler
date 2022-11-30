package com.example.springboot.controllers;

import com.example.springboot.model.Appointment;
import com.example.springboot.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AppointmentControllerTest {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    AppointmentController appointmentController;

    Appointment testAppt1 = new Appointment(new Date(2022,12,15,9,30),1);
    Appointment testAppt2 = new Appointment(new Date(2022, 12, 5, 10, 45),2);

    @BeforeEach
    void setUp() {
        appointmentRepository.deleteAll();
    }

    @Test
    void createAppointment() {
        Appointment testAppt = appointmentController.createAppointment(new Appointment(new Date(2022, 12, 5, 10, 45),2));
        assertTrue(appointmentRepository.findById(testAppt.getId()).isPresent());
    }

    @Test
    void getAllAppointments() {
        assertTrue(appointmentController.getAllAppointments().isEmpty());
        appointmentRepository.save(testAppt1);
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
        appointmentRepository.save(testAppt1);
        appointmentController.deleteAppointment(testAppt1.getId());
        assertTrue(appointmentRepository.findById(testAppt1.getId()).isEmpty());
    }
}
