package com.oopsiedaisy.properties;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static lombok.AccessLevel.PRIVATE;

@Data
@Configuration
@FieldDefaults(level = PRIVATE)
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    String secret;
}
