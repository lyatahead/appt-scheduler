package com.example.springboot.repository;

import com.example.springboot.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "doctors")
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findAll();
    Optional<Doctor> findById(Long id);
    List<Doctor> findByLastName(String lastName);
}
