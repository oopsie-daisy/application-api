package com.oopsiedaisy.auth;

import com.oopsiedaisy.auth.controller.resource.AddressCreationResource;
import com.oopsiedaisy.auth.controller.resource.CustomerCreationRequestResource;
import com.oopsiedaisy.common.IntegrationTest;
import com.oopsiedaisy.customers.repository.CustomerJpaRepository;
import com.oopsiedaisy.customers.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerCreationIntTest extends IntegrationTest {

    private static final String SIGN_UP_URL = "/auth/sign-up";

    @Autowired
    private CustomerRepository repository;

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

    private CustomerCreationRequestResource buildBadCustomerCreationResource() {
        return CustomerCreationRequestResource.builder()
                .firstName(null)
                .lastName("Doe")
                .fullName("John M. Doe")
                .phoneNumber("+370 6123 4567")
                .email("example@mail.com")
                .password("password")
                .addresses(List.of())
                .build();
    }

    private CustomerCreationRequestResource buildCustomerCreationResource() {
        return CustomerCreationRequestResource.builder()
                .firstName("John")
                .lastName("Doe")
                .fullName("John M. Doe")
                .phoneNumber("+370 6123 4567")
                .email("example@mail.com")
                .password("password")
                .addresses(List.of(buildAddress()))
                .build();
    }

    private AddressCreationResource buildAddress() {
        return AddressCreationResource.builder()
                .city("City")
                .country("Country")
                .houseNumber("1")
                .streetName("Groove street")
                .zipCode("12346")
                .build();
    }
}
