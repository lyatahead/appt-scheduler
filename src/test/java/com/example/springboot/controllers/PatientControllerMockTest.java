package com.example.springboot.controllers;

import com.example.springboot.model.Patient;
import com.example.springboot.repository.PatientRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientControllerMockTest {
    @Mock
    PatientRepository patientRepository;
    @InjectMocks
    PatientController patientController;

    LocalDate dateTime1 = LocalDate.of(1975, Month.APRIL, 12);
    LocalDate dateTime2 = LocalDate.of(1988, Month.JULY, 22);
    Patient testPatient = new Patient("Amy", "Do", dateTime1, "1234 Creek Atlanta, GA 30033", "6786226014");
    Patient testPatient2 = new Patient("Louie", "P", dateTime2, "6551 Martin Atlanta, GA 30033", "6786226014");
    List<Patient> patients = new ArrayList<>();

    @BeforeEach
    void setUp() {
        patients.add(testPatient);
        patients.add(testPatient2);
    }

    @Test
    void getPatientById() {
        when(patientRepository.findById(testPatient.getId())).thenReturn(Optional.of(testPatient));
        Optional<Patient> tempPatient = patientController.getSpecificPatient(testPatient.getId());
        assertTrue(tempPatient.isPresent());
        assertEquals(testPatient, tempPatient.get());
    }

    @Test
    void getAllPatients() {
        when(patientRepository.findAll()).thenReturn(patients);
        patientController.getAllPatients();
        verify(patientRepository).findAll();
        assertEquals(2, patientController.getAllPatients().size());
    }

    @Test
    void createPatient() {
        when(patientRepository.save(testPatient2)).thenReturn(testPatient2);
        Patient tempPatient = patientController.createPatient(testPatient2);
        assertNotNull(tempPatient);
        assertEquals(testPatient2, tempPatient);
    }

    @Test
    void updatePatient() {
        Patient updatePatient = new Patient("Louie", "P", dateTime2, "6551 Martin Atlanta, GA 30033", "6786226015");
        when(patientRepository.findById(testPatient.getId())).thenReturn(Optional.of(testPatient));
        when(patientRepository.save(updatePatient)).thenReturn(updatePatient);
        Patient tempPatient = patientController.updatePatient(testPatient.getId(), updatePatient);
        assertNotNull(tempPatient);
        assertEquals(updatePatient.getPhoneNumber(), testPatient.getPhoneNumber());
    }

    @Test
    void deletePatient() {
        patientController.deletePatient(testPatient.getId());
        verify(patientRepository).deleteById(testPatient.getId());
        assertTrue(patientRepository.findById(testPatient.getId()).isEmpty());
    }
}
