package com.example.springboot.controllers;

import com.example.springboot.model.Doctor;
import com.example.springboot.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DoctorControllerTest {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    DoctorController doctorController;

    Doctor testDoc = new Doctor("P", "Lee");
    Doctor testDoc2 = new Doctor("A", "Park");

    @BeforeEach
    void setUp() {
        doctorRepository.deleteAll();
    }

    @Test
    void createDoctor() {
        Doctor tempDoc = doctorController.createDoctor(testDoc);
        assertTrue(doctorRepository.findById(tempDoc.getId()).isPresent());
    }

    @Test
    void getAllDoctors() {
        assertTrue(doctorController.getAllDoctors().isEmpty());
        doctorRepository.save(testDoc);
        assertFalse(doctorController.getAllDoctors().isEmpty());
        doctorRepository.save(testDoc2);
        assertEquals(2, doctorController.getAllDoctors().size());
    }

    @Test
    void getSpecificDoctor() {
        doctorRepository.save(testDoc);
        Optional<Doctor> tempDoc = doctorController.getSpecificDoctor(testDoc.getId());
        assertTrue(tempDoc.isPresent());
        assertEquals(testDoc, tempDoc.get());
    }

    @Test
    void deleteDoctor() {
        doctorRepository.save(testDoc);
        doctorController.deleteDoctor(testDoc.getId());
        assertTrue(doctorRepository.findById(testDoc.getId()).isEmpty());
    }

    @Test
    void updateDoctor() {
        doctorRepository.save(testDoc2);
        Doctor tempDoct = new Doctor("A", "Song");
        doctorController.updateDoctor(testDoc2.getId(), tempDoct);
        assertEquals("Song", doctorRepository.findById(testDoc2.getId()).get().getLastName());
    }

}