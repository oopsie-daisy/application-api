package com.oopsiedaisy.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation on http request method to check if user is authorised to requested resource.
 * Http url must have "uuid" path variable that indicates user uuid.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JwtValidated {
}
