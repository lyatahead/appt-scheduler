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

    @PostMapping("/createDoctor")
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @GetMapping("/doctorsList")
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @GetMapping("/doctor/{id}")
    public Optional<Doctor> getSpecificDoctor(@PathVariable(name = "id") Long id) {
        return doctorRepository.findById(id);
    }

    @DeleteMapping("/deleteDoctor/{id}")
    public void deleteDoctor(@PathVariable("id") Long id) {
        doctorRepository.deleteById(id);
    }

    @PutMapping("/updateDoctor/{id}")
    public Doctor updateDoctor(@PathVariable("id") Long id, @RequestBody Doctor updateDoctor) {
        Doctor tempDoctor = doctorRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        tempDoctor.setFirstName(updateDoctor.getFirstName());
        tempDoctor.setLastName(updateDoctor.getLastName());
        return doctorRepository.save(tempDoctor);
    }

}
