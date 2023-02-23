package com.example.springboot.controllers;

import com.example.springboot.model.Appointment;
import com.example.springboot.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * These are mockito tests for the controller
 */
@ExtendWith(MockitoExtension.class)
public class AppointmentControllerMockTest {
    @Mock
    AppointmentRepository appointmentRepository;
    @InjectMocks
    AppointmentController appointmentController;

    LocalDate dateTime1 = LocalDate.of(2022, Month.DECEMBER, 12);
    LocalDate dateTime2 = LocalDate.of(2022, Month.DECEMBER, 20);

    Appointment testAppt = new Appointment(dateTime2, 1);
    Appointment testAppt1 = new Appointment(dateTime1,1);
    Appointment testAppt2 = new Appointment(dateTime2,2);
    List<Appointment> appointments = new ArrayList<>();

    /**
     * Adding test appointments before each test
     */
    @BeforeEach
    void setUp() {
        appointments.add(testAppt);
        appointments.add(testAppt1);
        appointments.add(testAppt2);
    }

    /**
     * When the repository is called to find all appointments, then the controller's get all appointments method should be called
     */
    @Test
    void getAllAppointments() {
        when(appointmentRepository.findAll()).thenReturn(appointments);
        appointmentController.getAllAppointments();
        verify(appointmentRepository).findAll();
        assertEquals(3, appointmentController.getAllAppointments().size());
    }

    /**
     * When the repository is called to find an appointment by ID, then the controller's get specific appointment method should be called
     */
    @Test
    void getAppointmentById() {
        when(appointmentRepository.findById(testAppt.getId())).thenReturn(Optional.of(testAppt));
        Optional <Appointment> tempApt = appointmentController.getSpecificAppointment(testAppt.getId());
        assertTrue(tempApt.isPresent());
        assertEquals(testAppt, tempApt.get());
    }

    /**
     * When the repository is called to save an appointment, then the controller's create appointment method should be called
     */
    @Test
    void createAppointment() {
        when(appointmentRepository.save(testAppt1)).thenReturn(testAppt1);
        Appointment tempApt = appointmentController.createAppointment(testAppt1);
        assertNotNull(tempApt);
        assertEquals(testAppt1, tempApt);
    }

    /**
     * When the repository is called to save an updated appointment, then the controller's update apppointment method should be called
     */
    @Test
    void updateAppointment() {
        Appointment updateApt = new Appointment(dateTime1,2);
        when(appointmentRepository.findById(testAppt2.getId())).thenReturn(Optional.of(testAppt2));
        when(appointmentRepository.save(updateApt)).thenReturn(updateApt);
        Appointment tempApt = appointmentController.updateAppointment(testAppt2.getId(), updateApt);
        assertNotNull(tempApt);
        assertEquals(updateApt.getAppDate(), testAppt2.getAppDate());
    }

    /**
     * If the controller's delete appointment method is called, then verify if repository is called to delete an appointment
     */
    @Test
    void deleteAppointment() {
        appointmentController.deleteAppointment(testAppt2.getId());
        verify(appointmentRepository).deleteById(testAppt2.getId());
        assertTrue(appointmentRepository.findById(testAppt2.getId()).isEmpty());
    }

    /**
     * When repository is called to find appointments by doctor ID, then controller's get appointments by doctor method should be called
     */
    @Test
    void getAppointmentsByDoctor() {
        when(appointmentRepository.findByDoctorId(testAppt.getDoctorId())).thenReturn(appointments);
        appointmentController.getAppointmentsByDoctor(testAppt.getDoctorId());
        verify(appointmentRepository).findByDoctorId(testAppt.getDoctorId());
        assertFalse(appointmentController.getAppointmentsByDoctor(testAppt.getDoctorId()).isEmpty());
    }

    /**
     * When controller's delete appointment by doctor ID is called, then verify if repository is called to delete an appointment by doctor ID
     */
    @Test
    void deleteAppointmentByDoctor() {
        appointmentController.deleteAppointmentByDoctor(testAppt.getDoctorId());
        verify(appointmentRepository).deleteByDoctorId(testAppt.getDoctorId());
        assertTrue(appointmentRepository.findByDoctorId(testAppt.getDoctorId()).isEmpty());
    }
}
