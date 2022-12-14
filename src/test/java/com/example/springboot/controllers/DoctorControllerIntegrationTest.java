package com.example.springboot.controllers;

import com.example.springboot.model.Doctor;
import com.example.springboot.repository.DoctorRepository;
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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
class DoctorControllerIntegrationTest {
    @Autowired
    DoctorController doctorController;
    @Autowired
    DoctorRepository doctorRepository;

    MockMvc mockMvc;

    Doctor testDoc1 = new Doctor("Jo", "E");
    Doctor testDoc2 = new Doctor("El", "P");

    @BeforeEach
    public void setup() {
        doctorController = new DoctorController(doctorRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
        doctorRepository.deleteAll();
    }

    @Test
    public void contextLoads() {
        assertThat(doctorController).isNotNull();
    }

    @Test
    @SneakyThrows
    void createDoctor()  {
        doctorRepository.save(testDoc1);
        MvcResult result = mockMvc.perform(post("/api/createDoctor")
                        .content(new ObjectMapper().writeValueAsString(testDoc1))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result);
    }

    @Test
    @SneakyThrows
    void getDoctors()  {
        mockMvc.perform(get("/api/doctorsList"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> jsonPath("$[0].id", is(testDoc1.getId()
                )))
                .andExpect(mvcResult -> jsonPath("$[1].id", is(testDoc2.getId())));
    }

    @Test
    @SneakyThrows
    void getSpecificDoctor() {
        mockMvc.perform(get("/api/doctor/{id}", testDoc1.getId() ))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void getDoctorsFail() {
        mockMvc.perform(get("/api/doctorsList"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> jsonPath("$", hasSize(0)))
                .andExpect(mvcResult -> jsonPath("$[0].id").isEmpty())
                .andExpect(mvcResult -> jsonPath("$[1].id").isEmpty());
    }

    @Test
    @SneakyThrows
    void getSpecificDoctorFail() {
        mockMvc.perform(get("/api/doctor/" + null))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/api/doctor/"))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void createDoctorFail() {
        Doctor testDoc = new Doctor(null, null);
        doctorRepository.save(testDoc);
        mockMvc.perform(post("/api/createDoctor")
                        .content(new ObjectMapper().writeValueAsString(testDoc))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", isEmptyOrNullString()))
                .andExpect(jsonPath("$.lastName", isEmptyOrNullString()));
    }

    @Test
    @SneakyThrows
    void deleteDoctorFail() {
        mockMvc.perform(delete("/api/deleteDoctor/"))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/api/deleteDoctor/" + null))
                .andExpect(status().isBadRequest());
    }
}