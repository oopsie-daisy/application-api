package com.oopsiedaisy.flowers;

import com.oopsiedaisy.auth.controller.resource.AuthenticationRequestResource;
import com.oopsiedaisy.auth.domain.AuthenticationResult;
import com.oopsiedaisy.common.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Value("classpath:testdata/flowers/add_flowers.json")
    private Resource addFlowersResource;

    @Value("classpath:testdata/common/empty_list_response.json")
    private Resource emptyListResponse;

    @Test
    @Sql({"/testdata/customers/create-user.sql"})
    void shouldAddFlowersWhenUserIsAuthenticated() throws Exception {
        AuthenticationResult result = authenticateUser();

        mockMvc.perform(post("/flowers")
                    .accept(APPLICATION_JSON)
                    .header("x-application-context", result.getJwt())
                    .contentType(APPLICATION_JSON)
                    .content(readResourceAsString(addFlowersResource)))
                .andExpect(status().isOk());
    }

    @Test
    @Sql({"/testdata/customers/create-user.sql"})
    void shouldNotAddFlowersWhenJwtIsNotProvided() throws Exception {
        AuthenticationResult result = authenticateUser();

        mockMvc.perform(post("/flowers")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(readResourceAsString(addFlowersResource)))
                .andExpect(status().isUnauthorized());
    }

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
        testGetFlowersByFilter(ALL_FLOWERS_URL + "?color=RED", expectedFlowersByColor, status().isOk());
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

    private AuthenticationResult authenticateUser() throws Exception {
        String response = mockMvc.perform(post("/auth")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(buildAuthRequest("example@mail.com", "password"))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return fromJson(response, AuthenticationResult.class);
    }

    private AuthenticationRequestResource buildAuthRequest(String email, String password) {
        return new AuthenticationRequestResource(email, password);
    }

    private void testGetFlowersByFilter(String url, Resource expectedContent, ResultMatcher expectedStatus) throws Exception {
        mockMvc.perform(get(url)
                        .accept(APPLICATION_JSON))
                .andExpect(expectedStatus)
                .andExpect(content().json(readResourceAsString(expectedContent)));
    }
}
