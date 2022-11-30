package com.example.springboot.controllers;

import com.example.springboot.model.Patient;
import com.example.springboot.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class PatientController {
    @Autowired
    PatientRepository patientRepository;

    public PatientController(@Autowired PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @PostMapping("/createPatient")
    public Patient createPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }

    @GetMapping("/patientsList")
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/patient/{id}")
    public Optional<Patient> getSpecificPatient(@PathVariable(name = "id") Long id) {
        return patientRepository.findById(id);
    }
    @DeleteMapping("/deletePatient/{id}")
    public void deletePatient(@PathVariable("id") Long id) {
        patientRepository.deleteById(id);
    }

    @PutMapping("/updatePatient/{id}")
    public Patient updatePatient(@PathVariable("id") Long id, @RequestBody Patient updatePatient) {
        Patient tempPatient = patientRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        tempPatient.setAddress(updatePatient.getAddress());
        tempPatient.setPhoneNumber(updatePatient.getPhoneNumber());
        return patientRepository.save(tempPatient);
    }
}
