package com.example.springboot.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Appointments")
@Data
@NoArgsConstructor
public class Appointment {
    @Id
    @Column(name = "Appointment_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "App_DateTime")
    private Date appDateTime;

    @Column(name = "Doctor_ID")
    private int doctor_ID;

    public Appointment(Date appDateTime, int doctor_ID) {
        this.appDateTime = appDateTime;
        this.doctor_ID = doctor_ID;
    }

}
