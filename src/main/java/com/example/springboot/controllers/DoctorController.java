package com.example.springboot.controllers;

import com.example.springboot.model.Doctor;
import com.example.springboot.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class DoctorController {
    @Autowired
    DoctorRepository doctorRepository;

    public DoctorController(@Autowired DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * Create the doctor object
     * @param doctor
     * @return save the doctor into the repository
     */
    @PostMapping("/createDoctor")
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    /**
     * Get a list of all the doctors in the repository
     * @return the doctor objects
     */
    @GetMapping("/doctorsList")
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    /**
     * Get specific doctor by their doctor ID
     * @param id
     * @return the doctor object
     */
    @GetMapping("/doctor/{id}")
    public Optional<Doctor> getSpecificDoctor(@PathVariable(name = "id") Long id) {
        if (!this.doctorRepository.findById(id).isPresent()) {
            throw new NoSuchElementException();
        }
        return doctorRepository.findById(id);
    }

    /**
     * Delete the doctor by their doctor ID
     * @param id
     */
    @DeleteMapping("/deleteDoctor/{id}")
    public void deleteDoctor(@PathVariable("id") Long id) {
        doctorRepository.deleteById(id);
    }

    /**
     * Update the doctor in the repository by their doctor ID and body of API request
     * @param id
     * @param updateDoctor
     * @return save the updated doctor in the repository
     */
    @PutMapping("/updateDoctor/{id}")
    public Doctor updateDoctor(@PathVariable("id") Long id, @RequestBody Doctor updateDoctor) {
        Doctor tempDoctor = doctorRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        tempDoctor.setFirstName(updateDoctor.getFirstName());
        tempDoctor.setLastName(updateDoctor.getLastName());
        return doctorRepository.save(tempDoctor);
    }

}
