package com.skwarek.blog.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Michal on 30/01/2017.
 */
public class TestIndexController {

    private static final String VIEWS_INDEX = "index";

    private IndexController indexController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.indexController = new IndexController();

        this.mockMvc = MockMvcBuilders.standaloneSetup(this.indexController)
                .build();
    }

    @Test
    public void indexController_ShouldRenderWelcomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(VIEWS_INDEX))
                .andExpect(view().name(VIEWS_INDEX));
    }
}
