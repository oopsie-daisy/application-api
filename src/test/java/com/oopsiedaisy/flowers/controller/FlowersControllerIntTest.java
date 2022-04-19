package com.oopsiedaisy.flowers.controller;

import com.oopsiedaisy.common.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FlowersControllerIntTest extends IntegrationTest {

    private static final String ALL_FLOWERS_URL = "/flowers";

    @Value("classpath:testdata/flowers/expected_all_flowers.json")
    private Resource expectedAllFlowers;

    @Value("classpath:testdata/common/empty_list_response.json")
    private Resource emptyListResponse;

    @Test
    @Sql({"/testdata/flowers/add-flowers.sql"})
    void shouldGetAllFlowers() throws Exception {
        mockMvc.perform(get(ALL_FLOWERS_URL)
                    .accept(APPLICATION_JSON))
                .andExpect(content().json(readResourceAsString(expectedAllFlowers)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnEmptyListWhenNoFlowersArePresent() throws Exception {
        mockMvc.perform(get(ALL_FLOWERS_URL)
                    .accept(APPLICATION_JSON))
                .andExpect(content().json(readResourceAsString(emptyListResponse)))
                .andExpect(status().isOk());
    }
}
