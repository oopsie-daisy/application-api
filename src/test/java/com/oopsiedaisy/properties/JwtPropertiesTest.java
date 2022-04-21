package com.oopsiedaisy.properties;

import com.oopsiedaisy.common.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JwtPropertiesTest extends IntegrationTest {

    @Autowired
    private JwtProperties properties;

    @Test
    void shouldGetSecretFromJwtProperties() {
        String jwtSecret = properties.getSecret();
        assertThat(jwtSecret).isEqualTo("lfbNiwulbvB4PLIdYOR5v9KaScZodLQ2hfd9wgRwcEeIfdCQx1vmWHRP77J30IOR");
    }

    @Test
    void shouldFailWhenExpectingBadKey() {
        String jwtSecret = properties.getSecret();
        assertThat(jwtSecret).isNotEqualTo("bad secret");
    }
}
