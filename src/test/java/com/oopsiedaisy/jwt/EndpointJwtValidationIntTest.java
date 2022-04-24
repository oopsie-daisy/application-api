package com.oopsiedaisy.jwt;

import com.oopsiedaisy.auth.controller.resource.AuthenticationRequestResource;
import com.oopsiedaisy.auth.domain.AuthenticationResult;
import com.oopsiedaisy.common.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({"/testdata/customers/create-user.sql"})
class EndpointJwtValidationIntTest extends IntegrationTest {

    @Test
    void shouldAllowCallWithValidJwt() throws Exception {
        AuthenticationResult authenticationResult = authenticateUser();

        mockMvc.perform(get("/test/" + authenticationResult.getUserUuid())
                        .accept(APPLICATION_JSON)
                        .header("x-application-context", authenticationResult.getJwt()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotAllowCallWhenNoJwtIsInHeader() throws Exception {
        AuthenticationResult authenticationResult = authenticateUser();

        mockMvc.perform(get("/test/" + authenticationResult.getUserUuid())
                        .accept(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is("User is not authorised to access this resource: Jwt is not provided")));
    }

    @Test
    void shouldNotAllowCallWhenNoUserIdIsNotInJwt() throws Exception {
        AuthenticationResult authenticationResult = authenticateUser();

        mockMvc.perform(get("/test/f8c3de3d-1fea-4d7c-a8b0-29f63c4c3887")
                        .accept(APPLICATION_JSON)
                .header("x-application-context", authenticationResult.getJwt()))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is("User is not authorised to access this resource: Jwt is not valid")));
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
}
