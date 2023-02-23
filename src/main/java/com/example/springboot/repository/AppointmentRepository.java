package com.example.springboot.repository;

import com.example.springboot.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository(value = "appointments")
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAll();
    Optional<Appointment> findById(Long id);
    List<Appointment> findByDoctorId(int doctorId);
    @Transactional
    @Modifying
    int deleteByDoctorId(int doctorId);
}
