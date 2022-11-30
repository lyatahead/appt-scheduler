package com.example.springboot.controllers;

import com.example.springboot.model.Appointment;
import com.example.springboot.repository.AppointmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AppointmentControllerIntegrationTest {
    @Autowired
    AppointmentController appointmentController;
    @Autowired
    AppointmentRepository appointmentRepository;

    MockMvc mockMvc;

    LocalDate dateTime1 = LocalDate.of(2022, Month.DECEMBER, 12);
    LocalDate dateTime2 = LocalDate.of(2022, Month.DECEMBER, 20);

    Appointment testAppt1 = new Appointment(dateTime1, 1);
    Appointment testAppt2 = new Appointment(dateTime2, 2);

    @BeforeEach
    void setUp() {
        appointmentController = new AppointmentController(appointmentRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();
        appointmentRepository.save(testAppt1);
        appointmentRepository.save(testAppt2);
    }

    @Test
    void contextLoads() {
        assertThat(appointmentController).isNotNull();
    }

    @Test
    @SneakyThrows
    void createAppointment()  {
        appointmentRepository.save(testAppt1);
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        MvcResult result = mockMvc.perform(post("/api/createAppointment")
                        .content(mapper.writeValueAsString(testAppt1))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result);
    }

    @Test
    @SneakyThrows
    void getAppointments()  {
        mockMvc.perform(get("/api/appointmentsList"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> jsonPath("$[0].id", is(testAppt1.getId()
                )))
                .andExpect(mvcResult -> jsonPath("$[1].id", is(testAppt2.getId())));
    }

    @Test
    @SneakyThrows
    void getSpecificAppointment() {
        mockMvc.perform(get("/api/appointment/{id}", testAppt1.getId()))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> jsonPath("$[0].id", is(testAppt1.getId())));
    }

    @Test
    @SneakyThrows
    void deleteAppointment() {
        mockMvc.perform(delete("/api/deleteAppointment/{id}", testAppt1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void updateAppointment() {
        appointmentRepository.save(testAppt1);
        Appointment tempApt = new Appointment(dateTime2, 1);
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        MvcResult result = mockMvc.perform(put("/api/updateAppointment/{id}", testAppt1.getId())
                        .content(mapper.writeValueAsString(tempApt))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> jsonPath("$[0].appDate", is(tempApt.getAppDate())))
                .andReturn();
        assertNotNull(result);
    }

    @Test
    @SneakyThrows
    void createAppointmentFail() {
        Appointment testAppt = new Appointment(null, 0);
        appointmentRepository.save(testAppt);
        mockMvc.perform(post("/api/createAppointment")
                        .content(new ObjectMapper().writeValueAsString(testAppt))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appDate", isEmptyOrNullString()));
    }

    @Test
    @SneakyThrows
    void getSpecificAppointmentFail() {
        mockMvc.perform(get("/api/appointment/" + null))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/api/appointment/"))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void getAppointmentsFail() {
        mockMvc.perform(get("/api/appointmentsList"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> jsonPath("$", hasSize(0)))
                .andExpect(mvcResult -> jsonPath("$[0].id").isEmpty())
                .andExpect(mvcResult -> jsonPath("$[1].id").isEmpty());
    }

    @Test
    @SneakyThrows
    void deleteAppointmentFail() {
        mockMvc.perform(delete("/api/deleteAppointment/"))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/api/deleteAppointment/" + null))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void updateAppointmentFail() {
        appointmentRepository.save(testAppt1);
        Appointment tempApt = new Appointment(null, 1);
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        MvcResult result = mockMvc.perform(put("/api/updateAppointment/{id}", testAppt1.getId())
                        .content(mapper.writeValueAsString(tempApt))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appDate", isEmptyOrNullString()))
                .andReturn();
    }
}
