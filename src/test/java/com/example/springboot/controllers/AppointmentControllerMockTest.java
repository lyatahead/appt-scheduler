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

    @BeforeEach
    void setUp() {
        appointments.add(testAppt);
        appointments.add(testAppt1);
        appointments.add(testAppt2);
    }

    @Test
    void getAllAppointments() {
        when(appointmentRepository.findAll()).thenReturn(appointments);
        appointmentController.getAllAppointments();
        verify(appointmentRepository).findAll();
        assertEquals(3, appointmentController.getAllAppointments().size());
    }

    @Test
    void getAppointmentById() {
        when(appointmentRepository.findById(testAppt.getId())).thenReturn(Optional.of(testAppt));
        Optional <Appointment> tempApt = appointmentController.getSpecificAppointment(testAppt.getId());
        assertTrue(tempApt.isPresent());
        assertEquals(testAppt, tempApt.get());
    }

    @Test
    void createAppointment() {
        when(appointmentRepository.save(testAppt1)).thenReturn(testAppt1);
        Appointment tempApt = appointmentController.createAppointment(testAppt1);
        assertNotNull(tempApt);
        assertEquals(testAppt1, tempApt);
    }

    @Test
    void updateAppointment() {
        Appointment updateApt = new Appointment(dateTime1,2);
        when(appointmentRepository.findById(testAppt2.getId())).thenReturn(Optional.of(testAppt2));
        when(appointmentRepository.save(updateApt)).thenReturn(updateApt);
        Appointment tempApt = appointmentController.updateAppointment(testAppt2.getId(), updateApt);
        assertNotNull(tempApt);
        assertEquals(updateApt.getAppDate(), testAppt2.getAppDate());
    }

    @Test
    void deleteAppointment() {
        appointmentController.deleteAppointment(testAppt2.getId());
        verify(appointmentRepository).deleteById(testAppt2.getId());
        assertTrue(appointmentRepository.findById(testAppt2.getId()).isEmpty());
    }

    @Test
    void getAppointmentsByDoctor() {
        when(appointmentRepository.findByDoctorId(testAppt.getDoctorId())).thenReturn(appointments);
        appointmentController.getAppointmentsByDoctor(testAppt.getDoctorId());
        verify(appointmentRepository).findByDoctorId(testAppt.getDoctorId());
        assertFalse(appointmentController.getAppointmentsByDoctor(testAppt.getDoctorId()).isEmpty());
    }

    @Test
    void deleteAppointmentByDoctor() {
        appointmentController.deleteAppointmentByDoctor(testAppt.getDoctorId());
        verify(appointmentRepository).deleteByDoctorId(testAppt.getDoctorId());
        assertTrue(appointmentRepository.findByDoctorId(testAppt.getDoctorId()).isEmpty());
    }
}
