package com.example.springboot.controllers;

import com.example.springboot.model.Appointment;
import com.example.springboot.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * These are the generic functionality tests for the controller
 */

@SpringBootTest
public class AppointmentControllerTest {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    AppointmentController appointmentController;
    LocalDate dateTime1 = LocalDate.of(2022, Month.DECEMBER, 12);
    LocalDate dateTime2 = LocalDate.of(2022, Month.DECEMBER, 20);

    Appointment testAppt = new Appointment(dateTime2, 1);
    Appointment testAppt1 = new Appointment(dateTime1,1);
    Appointment testAppt2 = new Appointment(dateTime2,2);

    /**
     * Delete all in repository before test
     */
    @BeforeEach
    void setUp() {
        appointmentRepository.deleteAll();
    }

    /**
     * Test for create appointment POST method. Test if appointment is present after creating.
     */
    @Test
    void createAppointment() {
        LocalDate dateTime = LocalDate.of(2022, Month.DECEMBER, 15);
        Appointment testAppt = appointmentController.createAppointment(new Appointment(dateTime2,1));
        assertTrue(appointmentRepository.findById(testAppt.getId()).isPresent());
    }

    /**
     * Test for get appointments GET method. Test if appointments are returned when controller is called
     */
    @Test
    void getAllAppointments() {
        assertTrue(appointmentController.getAllAppointments().isEmpty());
        appointmentRepository.save(testAppt1);
        assertFalse(appointmentController.getAllAppointments().isEmpty());
        appointmentRepository.save(testAppt2);
        assertEquals(2, appointmentController.getAllAppointments().size());

    }

    /**
     * Test for get specific appointment GET method. Test if appointment with specific ID is present
     */
    @Test
    void getSpecificAppointment() {
        appointmentRepository.save(testAppt2);
        Optional<Appointment> tempAppt = appointmentController.getSpecificAppointment(testAppt2.getId());
        assertTrue(tempAppt.isPresent());
        assertEquals(testAppt2, tempAppt.get());

    }

    /**
     * Test for delete appointment DELETE method. Test if appointment deleted is not present when called
     */
    @Test
    void deleteAppointment() {
        appointmentRepository.save(testAppt1);
        appointmentController.deleteAppointment(testAppt1.getId());
        assertTrue(appointmentRepository.findById(testAppt1.getId()).isEmpty());
    }

    /**
     * Test for update appointment PUT method. Test if appointment is updated when controller is called
     */
    @Test
    void updateAppointment() {
        appointmentRepository.save(testAppt2);
        Appointment tempApt = new Appointment(dateTime1, 2);
        appointmentController.updateAppointment(testAppt2.getId(), tempApt);
        assertEquals(dateTime1, appointmentRepository.findById(testAppt2.getId()).get().getAppDate());
    }

    /**
     * Test for specific appointment by doctor ID GET method. Test if appointments are returned when doctor ID is given
     */
    @Test
    void getAppointmentsByDoctor() {
        appointmentRepository.save(testAppt1);
        appointmentRepository.save(testAppt);
        appointmentController.getAppointmentsByDoctor(testAppt.getDoctorId());
        assertEquals(2, appointmentController.getAppointmentsByDoctor(testAppt.getDoctorId()).size());
    }

    /**
     * Test for delete appointment by doctor ID DELETE method. Test if appointment is deleted when doctor ID is given
     */
    @Test
    void deleteAppointmentByDoctor() {
        appointmentRepository.save(testAppt);
        assertFalse(appointmentRepository.findById(testAppt.getId()).isEmpty());
        appointmentController.deleteAppointmentByDoctor(testAppt.getDoctorId());
        assertTrue(appointmentRepository.findById(testAppt.getId()).isEmpty());
    }
}
