package com.marine.vessel_keeper.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marine.vessel_keeper.dto.request.UserRequestDto;
import com.marine.vessel_keeper.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private UserService userService;
    private UserRequestDto userCandidate;

    @BeforeEach
    void setUp(){
        userCandidate = new UserRequestDto(
                "testUser",
                "login",
                "password",
                "OWNER");
    }
    @Test
    void createUserValidInput() throws Exception {
        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userCandidate)))
                .andExpect(status().isCreated());
    }
    @Test
    void createInvalidInput(){

    }
    @Test
    void deleteUser() {
    }
}