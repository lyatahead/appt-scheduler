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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * These are mock mvc tests
 */
@SpringBootTest
class DoctorControllerIntegrationTest {
    @Autowired
    DoctorController doctorController;
    @Autowired
    DoctorRepository doctorRepository;

    MockMvc mockMvc;

    Doctor testDoc1 = new Doctor("Jo", "E");
    Doctor testDoc2 = new Doctor("El", "P");

    /**
     * Build controller and save test doctors before test
     */
    @BeforeEach
    public void setup() {
        doctorController = new DoctorController(doctorRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
        doctorRepository.save(testDoc1);
        doctorRepository.save(testDoc2);
    }


    @Test
    public void contextLoads() {
        assertThat(doctorController).isNotNull();
    }

    /**
     * Test to mock POST request method of creating a doctor
     */
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

    /**
     * Test to mock GET request method of getting all doctors
     */
    @Test
    @SneakyThrows
    void getDoctors()  {
        mockMvc.perform(get("/api/doctorsList"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> jsonPath("$[0].id", is(testDoc1.getId()
                )))
                .andExpect(mvcResult -> jsonPath("$[1].id", is(testDoc2.getId())));
    }

    /**
     * Test to mock GET request method of getting a specific doctor by the doctor ID
     */
    @Test
    @SneakyThrows
    void getSpecificDoctor() {
        mockMvc.perform(get("/api/doctor/{id}", testDoc1.getId() ))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> jsonPath("$[0].id", is(testDoc1.getId())));
    }

    /**
     * Test to mock DELETE request method of deleting a doctor by the doctor ID
     */
    @Test
    @SneakyThrows
    void deleteDoctor() {
        mockMvc.perform(delete("/api/deleteDoctor/{id}", testDoc1.getId()))
                .andExpect(status().isOk());
    }

    /**
     * Test to mock PUT request method of updating a doctor
     */
    @Test
    @SneakyThrows
    void updateDoctor() {
        doctorRepository.save(testDoc2);
        Doctor tempDoct = new Doctor("El", "Song");
        MvcResult result = mockMvc.perform(put("/api/updateDoctor/{id}", testDoc2.getId())
                .content(new ObjectMapper().writeValueAsString(tempDoct))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> jsonPath("$[0].lastName", is(tempDoct.getLastName())))
                .andReturn();
        assertNotNull(result);
    }

    /**
     * Test to mock a FAILED GET request method of getting all doctors
     */
    @Test
    @SneakyThrows
    void getDoctorsFail() {
        mockMvc.perform(get("/api/doctorsList"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> jsonPath("$", hasSize(0)))
                .andExpect(mvcResult -> jsonPath("$[0].id").isEmpty())
                .andExpect(mvcResult -> jsonPath("$[1].id").isEmpty());
    }

    /**
     * Test to mock a FAILED GET request method of getting a specific doctor by the doctor ID
     */
    @Test
    @SneakyThrows
    void getSpecificDoctorFail() {
        mockMvc.perform(get("/api/doctor/" + null))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/api/doctor/"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test to mock a FAILED POST request method of creating a doctor
     */
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

    /**
     * Test to mock a FAILED DELETE request method of deleting a doctor by the doctor ID
     */
    @Test
    @SneakyThrows
    void deleteDoctorFail() {
        mockMvc.perform(delete("/api/deleteDoctor/"))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/api/deleteDoctor/" + null))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test to mock a FAILED PUT request method of updating a doctor
     */
    @Test
    @SneakyThrows
    void updateDoctorFail() {
        doctorRepository.save(testDoc2);
        Doctor tempDoct = new Doctor("El", null);
        MvcResult result = mockMvc.perform(put("/api/updateDoctor/{id}", testDoc2.getId())
                        .content(new ObjectMapper().writeValueAsString(tempDoct))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> jsonPath("$[0].lastName", isEmptyOrNullString()))
                .andReturn();
        assertNotNull(result);
    }
}