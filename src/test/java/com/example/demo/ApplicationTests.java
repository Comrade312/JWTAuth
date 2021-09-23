package com.example.demo;

import com.example.demo.dto.response.JwtResponse;
import com.example.demo.entity.User;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void unAuthorizeTest_Unauthorized() throws Exception {
        this.mockMvc.perform(get("/api/hello"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("E01")
    public void authorizeTest_Ok() throws Exception {
        this.mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk());
    }

    @Test
    public void singUpTest_Ok() throws Exception {
        User user = new User();
        user.setUsername("TestName");
        user.setPassword("123");
        user.setEmail("aboba@mail");

        Gson gson = new Gson();
        String json = gson.toJson(user);

        MockHttpServletRequestBuilder requestBuilder = post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void singUpWithoutReqParamTest_BadRequest() throws Exception {
        User user = new User();
        user.setUsername("TestName");
        user.setPassword("123");

        Gson gson = new Gson();
        String json = gson.toJson(user);

        MockHttpServletRequestBuilder requestBuilder = post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void singUpWithExistUsername_BadRequest() throws Exception {
        User user = new User();
        user.setUsername("E01");
        user.setPassword("123");
        user.setEmail("aboba@mail");

        Gson gson = new Gson();
        String json = gson.toJson(user);

        MockHttpServletRequestBuilder requestBuilder = post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void singInTest_Ok() throws Exception {
        User user = new User();
        user.setUsername("E01");
        user.setPassword("123");

        Gson gson = new Gson();
        String json = gson.toJson(user);

        MockHttpServletRequestBuilder requestBuilder = post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        JwtResponse jwtResponse = gson.fromJson(mvcResult.getResponse().getContentAsString(), JwtResponse.class);

        requestBuilder = get("/api/hello")
                .header("Authorization", jwtResponse.getType() + " " + jwtResponse.getToken());

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void singInWithBadCredentialsTest_Unauthorized() throws Exception {
        User user = new User();
        user.setUsername("-1");
        user.setPassword("-1");

        Gson gson = new Gson();
        String json = gson.toJson(user);

        MockHttpServletRequestBuilder requestBuilder = post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized());
    }

}
