package com.example.springboot.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Appointments")
@Data
@NoArgsConstructor
public class Appointment {
    @Id
    @Column(name = "Appointment_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "App_Date")
    private String appDate;

    @Column(name = "App_Time")
    private String appTime;

    public Appointment(String appDate, String appTime) {
        this.appDate = appDate;
        this.appTime = appTime;
    }
}
