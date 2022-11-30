package com.example.springboot.repository;

import com.example.springboot.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "appointments")
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAll();
    Optional<Appointment> findById(Long id);


}
