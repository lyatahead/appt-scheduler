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

    @BeforeEach
    void setUp() {
        doctorRepository.deleteAll();
    }

    @Test
    void createDoctor() {
        Doctor tempDoc = doctorController.createDoctor(new Doctor("P", "Lee"));
        assertTrue(doctorRepository.findById(tempDoc.getId()).isPresent());
    }

    @Test
    void getAllDoctors() {
        assertTrue(doctorController.getAllDoctors().isEmpty());
        Doctor testDoc = new Doctor("P", "Lee");
        doctorRepository.save(testDoc);
        assertFalse(doctorController.getAllDoctors().isEmpty());
        Doctor testDoc2 = new Doctor("g", "da");
        doctorRepository.save(testDoc2);
        assertEquals(2, doctorController.getAllDoctors().size());
    }

    @Test
    void getSpecificDoctor() {
        Doctor testDoc = new Doctor("P", "Lee");
        doctorRepository.save(testDoc);
        Optional<Doctor> tempDoc = doctorController.getSpecificDoctor(testDoc.getId());
        assertTrue(tempDoc.isPresent());
        assertEquals(testDoc, tempDoc.get());
    }
}