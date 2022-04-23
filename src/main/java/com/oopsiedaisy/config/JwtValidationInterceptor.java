package com.oopsiedaisy.config;

import com.oopsiedaisy.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.UUID.fromString;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
@RequiredArgsConstructor
public class JwtValidationInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            JwtValidated isAnnotatedWithJwtValidated = getJwtValidatedAnnotation((HandlerMethod) handler);
            if (isNull(isAnnotatedWithJwtValidated)) {
                return true;
            }
            validateJwtAccess(request);
        }
        return true;
    }

    private void validateJwtAccess(HttpServletRequest request) {
        try {
            validateJwt(request);
        } catch (Exception e) {
            throw new NotAuthorizedException("User is not authorised to access this resource: " + e.getMessage());
        }
    }

    private JwtValidated getJwtValidatedAnnotation(HandlerMethod handler) {
        JwtValidated jwtValidated = handler.getMethodAnnotation(JwtValidated.class);
        if (isNull(jwtValidated)) {
            jwtValidated = handler.getMethod().getDeclaringClass().getAnnotation(JwtValidated.class);
        }
        return jwtValidated;
    }

    private void validateJwt(HttpServletRequest request) {
        String jwt = request.getHeader("x-application-context");
        if (isBlank(jwt)) {
            throw new IllegalArgumentException("Jwt is not provided");
        }
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String userUuid = pathVariables.get("uuid");
        boolean isJwtExpired = jwtService.isTokenExpired(jwt);
        if (isJwtExpired) {
            throw new IllegalArgumentException("Jwt is expired");
        }
        boolean isJwtValid =  jwtService.validateToken(jwt, fromString(userUuid));
        if (!isJwtValid) {
            throw new IllegalArgumentException("Jwt is not valid");
        }
    }
}
