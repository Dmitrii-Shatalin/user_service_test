//package org.example.controller;
//
//import org.example.dto.UserDTO;
//import org.example.dto.UserRequestDTO;
//import org.example.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(UserController.class)
//class UserControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    @Test
//    void shouldCreateUser() throws Exception {
//        UserDTO mockResponse = new UserDTO();
//        mockResponse.setId(1L);
//        mockResponse.setName("Test");
//        mockResponse.setEmail("test@test.com");
//        mockResponse.setAge(25);
//
//        given(userService.createUser(any(UserRequestDTO.class))).willReturn(mockResponse);
//
//        mockMvc.perform(post("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"Test\",\"email\":\"test@test.com\",\"age\":25}"))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name").value("Test"));
//    }
//
//    @Test
//    void shouldGetAllUsers() throws Exception {
//        UserDTO mockUser = new UserDTO();
//        mockUser.setId(1L);
//        mockUser.setName("Alice");
//        mockUser.setEmail("alice@test.com");
//        mockUser.setAge(30);
//
//        given(userService.getAllUsers()).willReturn(List.of(mockUser));
//
//        mockMvc.perform(get("/api/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].email").value("alice@test.com"));
//    }
//}

