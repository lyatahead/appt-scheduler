package com.example.springboot.controllers;

import com.example.springboot.model.Appointment;
import com.example.springboot.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AppointmentControllerTest {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    AppointmentController appointmentController;
    LocalDate dateTime1 = LocalDate.of(2022, Month.DECEMBER, 12);
    LocalDate dateTime2 = LocalDate.of(2022, Month.DECEMBER, 20);

    Appointment testAppt1 = new Appointment(dateTime1,1);
    Appointment testAppt2 = new Appointment(dateTime2,2);

    @BeforeEach
    void setUp() {
        appointmentRepository.deleteAll();
    }

    @Test
    void createAppointment() {
        LocalDate dateTime = LocalDate.of(2022, Month.DECEMBER, 15);
        Appointment testAppt = appointmentController.createAppointment(new Appointment(dateTime2,1));
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

    @Test
    void updateAppointment() {
        appointmentRepository.save(testAppt2);
        Appointment tempApt = new Appointment(dateTime1, 2);
        appointmentController.updateAppointment(testAppt2.getId(), tempApt);
        assertEquals(dateTime1, appointmentRepository.findById(testAppt2.getId()).get().getAppDate());
    }
}
