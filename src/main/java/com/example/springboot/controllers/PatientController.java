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

    /**
     * Create the patient object
     * @param patient information is sent in via API request body
     * @return save the patient into the repository
     */
    @PostMapping("/createPatient")
    public Patient createPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }

    /**
     * Get a list of all the patients from the repository
     * @return the patient objects
     */
    @GetMapping("/patientsList")
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    /**
     * Get a specific patient by their patient ID
     * @param id
     * @return the patient object
     */
    @GetMapping("/patient/{id}")
    public Optional<Patient> getSpecificPatient(@PathVariable(name = "id") Long id) {
        return patientRepository.findById(id);
    }

    /**
     * Delete a patient from the repository by their patient ID
     * @param id
     */
    @DeleteMapping("/deletePatient/{id}")
    public void deletePatient(@PathVariable("id") Long id) {
        patientRepository.deleteById(id);
    }

    // builder pattern and refactor code. String calls

    /**
     * Update patient in the reposiroty by their patient ID and body of the API request
     * @param id
     * @param updatePatient
     * @return save the updated patient into the repository
     */
    @PutMapping("/updatePatient/{id}")
    public Patient updatePatient(@PathVariable("id") Long id, @RequestBody Patient updatePatient) {
        Patient tempPatient = patientRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        tempPatient.setFirstName(updatePatient.getFirstName());
        tempPatient.setLastName(updatePatient.getLastName());
        tempPatient.setDateOfBirth(updatePatient.getDateOfBirth());
        tempPatient.setAddress(updatePatient.getAddress());
        tempPatient.setPhoneNumber(updatePatient.getPhoneNumber());
        return patientRepository.save(tempPatient);
    }
}
