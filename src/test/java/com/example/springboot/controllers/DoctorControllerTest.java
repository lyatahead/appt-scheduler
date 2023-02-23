package com.example.springboot.controllers;

import com.example.springboot.model.Doctor;
import com.example.springboot.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * These are the generic functionality tests for the controller
 */

@SpringBootTest
class DoctorControllerTest {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    DoctorController doctorController;

    Doctor testDoc = new Doctor("P", "Lee");
    Doctor testDoc2 = new Doctor("A", "Park");

    /**
     * Delete all in repository before test
     */
    @BeforeEach
    void setUp() {
        doctorRepository.deleteAll();
    }

    /**
     * Test for create doctor POST method. Test if doctor is present after creating.
     */
    @Test
    void createDoctor() {
        Doctor tempDoc = doctorController.createDoctor(testDoc);
        assertTrue(doctorRepository.findById(tempDoc.getId()).isPresent());
    }

    /**
     * Test for get doctors GET method. Test if doctors are returned when controller is called
     */
    @Test
    void getAllDoctors() {
        assertTrue(doctorController.getAllDoctors().isEmpty());
        doctorRepository.save(testDoc);
        assertFalse(doctorController.getAllDoctors().isEmpty());
        doctorRepository.save(testDoc2);
        assertEquals(2, doctorController.getAllDoctors().size());
    }

    /**
     * Test for get specific doctor GET method. Test if doctor with specific ID is present
     */
    @Test
    void getSpecificDoctor() {
        doctorRepository.save(testDoc);
        Optional<Doctor> tempDoc = doctorController.getSpecificDoctor(testDoc.getId());
        assertTrue(tempDoc.isPresent());
        assertEquals(testDoc, tempDoc.get());
    }

    /**
     * Test for delete doctor DELETE method. Test if doctor deleted is not present when called
     */
    @Test
    void deleteDoctor() {
        doctorRepository.save(testDoc);
        doctorController.deleteDoctor(testDoc.getId());
        assertTrue(doctorRepository.findById(testDoc.getId()).isEmpty());
    }

    /**
     * Test for update doctor PUT method. Test if doctor is updated when controller is called
     */
    @Test
    void updateDoctor() {
        doctorRepository.save(testDoc2);
        Doctor tempDoct = new Doctor("A", "Song");
        doctorController.updateDoctor(testDoc2.getId(), tempDoct);
        assertEquals("Song", doctorRepository.findById(testDoc2.getId()).get().getLastName());
    }

}