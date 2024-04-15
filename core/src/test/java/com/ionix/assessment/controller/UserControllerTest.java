package com.ionix.assessment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ionix.assessment.dto.request.UserRequest;
import com.ionix.assessment.dto.response.ResponseType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String path = "/api/v0";

    private static final String userEmail = "no@mail.com";
    @Test
    void test1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(path + "/test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectToJson(ResponseType.OK)));
    }

    @Test
    void test2() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("Test1");
        request.setUserName("test1");
        request.setEmail(userEmail);
        request.setPhone("2342242");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(path + "/secure/user")
                        .header("Authorization", "Basic YXNzZXNzbWVudDppb25peA==")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(request))).andReturn();

        Assert.isTrue(HttpStatus.CREATED.value() == result.getResponse().getStatus() || HttpStatus.BAD_REQUEST.value() == result.getResponse().getStatus(), "User is created or is already exist");

    }

    @Test
    void test3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(path + "/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void test4() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(path + "/user/" + userEmail)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.name").value("Test1"));
    }

    @Test
    void test5() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(path + "/secure/user/" + userEmail)
                .header("Authorization", "Basic YXNzZXNzbWVudDppb25peA==")
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assert.isTrue(HttpStatus.OK.value() == result.getResponse().getStatus() || HttpStatus.NOT_FOUND.value() == result.getResponse().getStatus(), "User is deleted or not exist");

    }

    @Test
    void test6() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(path + "/external/search/1-9")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.responseCode").value(0))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.registerCount").value(3));
    }

    private String objectToJson(Object object) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(object);

        return jsonString;
    }

}