package com.example.springboot.controllers;

import com.example.springboot.model.Patient;
import com.example.springboot.repository.PatientRepository;
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
public class PatientControllerTest {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    PatientController patientController;

    LocalDate dateTime1 = LocalDate.of(1975, Month.APRIL, 12);
    LocalDate dateTime2 = LocalDate.of(1988, Month.JULY, 22);
    Patient testPatient = new Patient("Amy", "Do", dateTime1, "1234 Creek Atlanta, GA 30033", "6786226014");
    Patient testPatient2 = new Patient("Louie", "P", dateTime2, "6551 Martin Atlanta, GA 30033", "6786226014");

    /**
     * Delete all in repository before test
     */
    @BeforeEach
    void setUp() {
        patientRepository.deleteAll();
    }

    /**
     * Test for create patient POST method. Test if patient is present after creating.
     */
    @Test
    void createPatient() {
        Patient tempPatient = patientController.createPatient(testPatient);
        assertTrue(patientRepository.findById(tempPatient.getId()).isPresent());
    }

    /**
     * Test for get patients GET method. Test if patient is returned when controller is called
     */
    @Test
    void getAllPatients() {
        assertTrue(patientController.getAllPatients().isEmpty());
        patientRepository.save(testPatient);
        assertFalse(patientController.getAllPatients().isEmpty());
        patientRepository.save(testPatient2);
        assertEquals(2, patientController.getAllPatients().size());
    }

    /**
     * Test for get specific patient GET method. Test if patient with specific ID is present
     */
    @Test
    void getSpecificPatient() {
        patientRepository.save(testPatient);
        Optional<Patient> tempPatient = patientController.getSpecificPatient(testPatient.getId());
        assertTrue(tempPatient.isPresent());
        assertEquals(testPatient, tempPatient.get());
    }

    /**
     * Test for delete patient DELETE method. Test if patient deleted is not present when called
     */
    @Test
    void deletePatient() {
        patientRepository.save(testPatient);
        patientController.deletePatient(testPatient.getId());
        assertTrue(patientRepository.findById(testPatient.getId()).isEmpty());
    }

    /**
     * Test for update patient PUT method. Test if patient is updated when controller is called
     */
    @Test
    void updatePatient() {
        patientRepository.save(testPatient);
        Patient tempPatient = new Patient("Amy", "Do", dateTime1, "1234 Creek Atlanta, GA 30033", "6786226015");
        patientController.updatePatient(testPatient.getId(), tempPatient);
        assertEquals("6786226015", patientRepository.findById(testPatient.getId()).get().getPhoneNumber());
    }
}
