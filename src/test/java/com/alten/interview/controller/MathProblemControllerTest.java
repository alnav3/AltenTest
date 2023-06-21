package com.alten.interview.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.alten.interview.data.MathProblem;
import com.alten.interview.data.MathProblemResponse;
import com.alten.interview.service.MathProblemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.http.MediaType;

@ExtendWith(MockitoExtension.class)
public class MathProblemControllerTest {
    @Mock
    private MathProblemService mathProblemService;

    @InjectMocks
    private MathProblemController mathProblemController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(mathProblemController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testBadRequestWhenNullParams() throws Exception {
        mockMvc.perform(post("/interview/mathProblem")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new MathProblem(null, null, null))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBadRequestWhenXisZero() throws Exception {
        mockMvc.perform(post("/interview/mathProblem")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MathProblem(0, 1, 2))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBadRequestWhenYgreaterThanX() throws Exception {
        mockMvc.perform(post("/interview/mathProblem")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MathProblem(3, 4, 5))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBadRequestWhenYgreaterThanN() throws Exception {
        mockMvc.perform(post("/interview/mathProblem")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MathProblem(2, 3, 2))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCorrectResponse() throws Exception {
        MathProblemResponse mockResponse = new MathProblemResponse(5);
        when(mathProblemService.solveProblem(2, 1, 3)).thenReturn(mockResponse);

        MvcResult result = mockMvc.perform(post("/interview/mathProblem")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MathProblem(2, 1, 3))))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        MathProblemResponse response = objectMapper.readValue(content, MathProblemResponse.class);
        assertEquals(mockResponse.getK(), response.getK());
    }

    @Test
    public void testCorrectGetResponse() throws Exception {
        MathProblemResponse mockResponse = new MathProblemResponse(5);
        when(mathProblemService.solveProblem(3, 2, 3)).thenReturn(mockResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/interview/mathProblem")
                .param("x", "3")
                .param("y", "2")
                .param("n", "3"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        MathProblemResponse response = objectMapper.readValue(content, MathProblemResponse.class);
        assertEquals(mockResponse.getK(), response.getK());
    }

    @Test
    public void testBadRequestGetWhenYgreaterThanN() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/interview/mathProblem")
                .param("x", "5")
                .param("y", "3")
                .param("n", "2"))
                .andExpect(status().isBadRequest());
    }

}

