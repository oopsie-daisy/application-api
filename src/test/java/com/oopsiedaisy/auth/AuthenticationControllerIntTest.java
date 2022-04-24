package com.oopsiedaisy.auth;

import com.oopsiedaisy.auth.controller.resource.AuthenticationRequestResource;
import com.oopsiedaisy.common.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({"/testdata/customers/create-user.sql"})
class AuthenticationControllerIntTest extends IntegrationTest {

    private static final String AUTH_URL = "/auth";

    @Test
    void shouldAuthenticateUserWhenGoodCredentialsArePassed() throws Exception {
        testUserAuthentication(buildAuthRequest("example@mail.com", "password"),
                status().isOk(), jsonPath("$.status", is("OK")));
    }

    @Test
    void shouldAuthenticateUserAndHaveJwtWhenGoodCredentialsArePassed() throws Exception {
        testUserAuthentication(buildAuthRequest("example@mail.com", "password"),
                status().isOk(), jsonPath("$.jwt").value(not(nullValue())));
    }

    @Test
    void shouldReturn401WhenUserDoesNotExist() throws Exception {
        testUserAuthentication(buildAuthRequest("random@mail.com", "password"),
                status().isUnauthorized(), jsonPath("$.message", is("Customer with this email not found")));
    }

    @Test
    void shouldReturnBadRequestWhenRequiredFieldIsEmpty() throws Exception {
        mockMvc.perform(post(AUTH_URL)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(buildAuthRequest(null, "password"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn401WhenPasswordIsIncorrect() throws Exception {
        testUserAuthentication(buildAuthRequest("example@mail.com", "password123"),
                status().isUnauthorized(), jsonPath("$.message", is("Bad credentials")));
    }

    private void testUserAuthentication(AuthenticationRequestResource requestResource, ResultMatcher expectedStatus,
                                        ResultMatcher expectedJsonPath) throws Exception {
        mockMvc.perform(post(AUTH_URL)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(requestResource)))
                .andExpect(expectedStatus)
                .andExpect(expectedJsonPath);
    }

    private AuthenticationRequestResource buildAuthRequest(String email, String password) {
        return new AuthenticationRequestResource(email, password);
    }

}
