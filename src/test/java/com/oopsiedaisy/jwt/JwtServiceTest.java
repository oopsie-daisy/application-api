package com.oopsiedaisy.jwt;

import com.oopsiedaisy.common.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static java.util.UUID.fromString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JwtServiceTest extends IntegrationTest {

    @Autowired
    private JwtService service;

    @Test
    void shouldGenerateValidJwt() {
        UUID userUuid = fromString("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454");
        String jwt = service.generateToken(userUuid);
        assertThat(service.validateToken(jwt)).isTrue();
        assertThat(service.getUserUuidFromJwt(jwt)).isEqualTo(userUuid.toString());
    }
}
