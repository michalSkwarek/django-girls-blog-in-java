package com.skwarek.blog.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Michal on 05/01/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestLoginController {

    private LoginController loginController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.loginController = new LoginController();

        this.mockMvc = MockMvcBuilders.standaloneSetup(this.loginController)
                .build();
    }

    @Test
    public void loginController_ShouldSendUserData() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }
}
