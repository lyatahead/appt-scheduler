package com.example.springboot.repository;

import com.example.springboot.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "patients")
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findAll();
    Optional<Patient> findById(Long id);
}
