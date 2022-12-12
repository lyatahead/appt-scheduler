package com.example.springboot.controllers;

import com.example.springboot.model.Doctor;
import com.example.springboot.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoctorControllerMockTest {
    @Mock
    DoctorRepository doctorRepository;
    @InjectMocks
    DoctorController doctorController;

    Doctor testDoc = new Doctor("P", "Lee");
    Doctor testDoc2 = new Doctor("A", "Park");
    List<Doctor> doctors = new ArrayList<>();

    @BeforeEach
    void setUp() {
        doctors.add(testDoc);
        doctors.add(testDoc2);
    }

    @Test
    void getDoctorById() {
        when(doctorRepository.findById(testDoc.getId())).thenReturn(Optional.of(testDoc));
        Optional<Doctor> tempDoc = doctorController.getSpecificDoctor(testDoc.getId());
        assertTrue(tempDoc.isPresent());
        assertEquals(testDoc, tempDoc.get());
    }

    @Test
    void getAllDoctors() {
        when(doctorRepository.findAll()).thenReturn(doctors);
        doctorController.getAllDoctors();
        verify(doctorRepository).findAll();
        assertEquals(2, doctorController.getAllDoctors().size());
    }

    @Test
    void createDoctor() {
        when(doctorRepository.save(testDoc)).thenReturn(testDoc);
        Doctor tempDoc = doctorController.createDoctor(testDoc);
        assertNotNull(tempDoc);
        assertEquals(testDoc, tempDoc);
    }

    @Test
    void updateDoctor() {
        Doctor updateDoc = new Doctor("A", "Song");
        when(doctorRepository.findById(testDoc2.getId())).thenReturn(Optional.of(testDoc2));
        when(doctorRepository.save(updateDoc)).thenReturn(updateDoc);
        Doctor tempDoc = doctorController.updateDoctor(testDoc2.getId(), updateDoc);
        assertNotNull(tempDoc);
        assertEquals(updateDoc.getLastName(), testDoc2.getLastName());
    }

    @Test
    void deleteDoctor() {
        doctorController.deleteDoctor(testDoc2.getId());
        verify(doctorRepository).deleteById(testDoc2.getId());
        assertTrue(doctorRepository.findById(testDoc2.getId()).isEmpty());
    }

}
