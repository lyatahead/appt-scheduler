package com.example.springboot.controllers;

import com.example.springboot.model.Patient;
import com.example.springboot.repository.PatientRepository;
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
public class PatientControllerIntegrationTest {
    @Autowired
    PatientController patientController;
    @Autowired
    PatientRepository patientRepository;

    MockMvc mockMvc;

    LocalDate dateTime1 = LocalDate.of(1975, Month.APRIL, 12);
    LocalDate dateTime2 = LocalDate.of(1988, Month.JULY, 22);
    Patient testPatient = new Patient("Amy", "Do", dateTime1, "1234 Creek Atlanta, GA 30033", "6786226014");
    Patient testPatient2 = new Patient("Louie", "P", dateTime2, "6551 Martin Atlanta, GA 30033", "6786226014");

    @BeforeEach
    void setUp() {
        patientController = new PatientController(patientRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
        patientRepository.save(testPatient);
        patientRepository.save(testPatient2);
    }

    @Test
    void contextLoads() {
        assertThat(patientController).isNotNull();
    }

    @Test
    @SneakyThrows
    void createPatient()  {
        patientRepository.save(testPatient2);
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        MvcResult result = mockMvc.perform(post("/api/createPatient")
                        .content(mapper.writeValueAsString(testPatient2))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(result);
    }

    @Test
    @SneakyThrows
    void getPatients()  {
        mockMvc.perform(get("/api/patientsList"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> jsonPath("$[0].id", is(testPatient.getId()
                )))
                .andExpect(mvcResult -> jsonPath("$[1].id", is(testPatient2.getId())));
    }

    @Test
    @SneakyThrows
    void getSpecificPatient() {
        mockMvc.perform(get("/api/patient/{id}", testPatient.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void deletePatient() {
        mockMvc.perform(delete("/api/deletePatient/{id}", testPatient.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void createPatientFail() {
        Patient tempPatient = new Patient(null, null, null, null, null);
        patientRepository.save(testPatient);
        mockMvc.perform(post("/api/createPatient")
                        .content(new ObjectMapper().writeValueAsString(tempPatient))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dateOfBirth", isEmptyOrNullString()))
        ;
    }

    @Test
    @SneakyThrows
    void getSpecificPatientFail() {
        mockMvc.perform(get("/api/patient/" + null))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/api/patient/"))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void getPatientsFail() {
        mockMvc.perform(get("/api/patientsList"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> jsonPath("$", hasSize(0)))
                .andExpect(mvcResult -> jsonPath("$[0].id").isEmpty())
                .andExpect(mvcResult -> jsonPath("$[1].id").isEmpty());
    }

    @Test
    @SneakyThrows
    void deletePatientFail() {
        mockMvc.perform(delete("/api/deletePatient/"))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/api/deletePatient/" + null))
                .andExpect(status().isBadRequest());
    }
}
