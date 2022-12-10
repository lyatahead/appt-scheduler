package com.example.springboot.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;
import java.time.*;

@Entity
@Table(name = "Appointments")
@Data
@NoArgsConstructor
public class Appointment {
    @Id
    @Column(name = "Appointment_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "App_Date")
    private LocalDate appDate;

    @Column(name = "Doctor_ID")
    private int doctorId;

    public Appointment(LocalDate appDate, int doctorId) {
        this.appDate = appDate;
        this.doctorId = doctorId;
    }

}
