package com.oopsiedaisy.flowers;

import com.oopsiedaisy.common.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FlowersControllerIntTest extends IntegrationTest {

    private static final String ALL_FLOWERS_URL = "/flowers";

    @Value("classpath:testdata/flowers/expected_all_flowers.json")
    private Resource expectedAllFlowers;

    @Value("classpath:testdata/flowers/expected_by_title.json")
    private Resource expectedFlowersByTitle;

    @Value("classpath:testdata/flowers/expected_by_color.json")
    private Resource expectedFlowersByColor;

    @Value("classpath:testdata/flowers/expected_by_many_filters.json")
    private Resource expectedFlowersByManyFilters;

    @Value("classpath:testdata/flowers/expected_by_bounded_price.json")
    private Resource expectedFlowersByBoundedPrice;

    @Value("classpath:testdata/common/empty_list_response.json")
    private Resource emptyListResponse;

    @Test
    @Sql({"/testdata/flowers/add-flowers.sql"})
    void shouldReturnFlowersByBoundedPrice() throws Exception {
        testGetFlowersByFilter(ALL_FLOWERS_URL + "?priceFrom=0.5&priceTo=15.00", expectedFlowersByBoundedPrice, status().isOk());
    }

    @Test
    @Sql({"/testdata/flowers/add-flowers.sql"})
    void shouldReturnFlowersByManyFilters() throws Exception {
        testGetFlowersByFilter(ALL_FLOWERS_URL + "?priceFrom=0.5&bouquet=true", expectedFlowersByManyFilters, status().isOk());
    }

    @Test
    @Sql({"/testdata/flowers/add-flowers.sql"})
    void shouldReturnFlowersByColor() throws Exception {
        testGetFlowersByFilter(ALL_FLOWERS_URL + "?color=red", expectedFlowersByColor, status().isOk());
    }

    @Test
    @Sql({"/testdata/flowers/add-flowers.sql"})
    void shouldReturnFlowersByTitle() throws Exception {
        testGetFlowersByFilter(ALL_FLOWERS_URL + "?title=tulip", expectedFlowersByTitle, status().isOk());
    }

    @Test
    @Sql({"/testdata/flowers/add-flowers.sql"})
    void shouldGetAllFlowers() throws Exception {
        testGetFlowersByFilter(ALL_FLOWERS_URL, expectedAllFlowers, status().isOk());
    }

    @Test
    void shouldReturnEmptyListWhenNoFlowersArePresent() throws Exception {
        testGetFlowersByFilter(ALL_FLOWERS_URL, emptyListResponse, status().isOk());
    }

    private void testGetFlowersByFilter(String url, Resource expectedContent, ResultMatcher expectedStatus) throws Exception {
        mockMvc.perform(get(url)
                        .accept(APPLICATION_JSON))
                .andExpect(expectedStatus)
                .andExpect(content().json(readResourceAsString(expectedContent)));
    }
}
