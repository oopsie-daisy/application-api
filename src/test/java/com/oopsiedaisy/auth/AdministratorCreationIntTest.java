package com.oopsiedaisy.auth;

import com.oopsiedaisy.auth.controller.resource.AdministratorCreationRequestResource;
import com.oopsiedaisy.common.IntegrationTest;
import com.oopsiedaisy.customers.repository.AdministratorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdministratorCreationIntTest extends IntegrationTest {

    private static final String SIGN_UP_URL = "/auth/sign-up";

    @Autowired
    private AdministratorRepository repository;

    @Test
    void shouldNotCreateCustomerWhenPayloadIsBad() throws Exception {
        mockMvc.perform(post(SIGN_UP_URL)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(buildBadCustomerCreationResource())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql({"/testdata/customers/create-user.sql"})
    void shouldNotCreateCustomerWhenEmailIsNotUnique() throws Exception {
        mockMvc.perform(post(SIGN_UP_URL)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(buildCustomerCreationResource())))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message", is("User with that email already exists")));
    }

    @Test
    void shouldCreateCustomer() throws Exception {
        mockMvc.perform(post(SIGN_UP_URL)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(buildCustomerCreationResource())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("OK")));

        assertThat(repository.getByEmail("example@mail.com").getFullName()).isEqualTo("John M. Doe");
    }

    private AdministratorCreationRequestResource buildBadCustomerCreationResource() {
        return AdministratorCreationRequestResource.builder()
                .firstName(null)
                .lastName("Doe")
                .fullName("John M. Doe")
                .phoneNumber("+370 6123 4567")
                .email("example@mail.com")
                .password("password")
                .build();
    }

    private AdministratorCreationRequestResource buildCustomerCreationResource() {
        return AdministratorCreationRequestResource.builder()
                .firstName("John")
                .lastName("Doe")
                .fullName("John M. Doe")
                .phoneNumber("+370 6123 4567")
                .email("example@mail.com")
                .password("password")
                .build();
    }
}
