package com.oopsiedaisy.auth.mapper;

import com.oopsiedaisy.auth.controller.resource.AuthenticationRequestResource;
import com.oopsiedaisy.auth.controller.resource.AuthenticationResultResource;
import com.oopsiedaisy.auth.domain.AuthenticationRequest;
import com.oopsiedaisy.auth.domain.AuthenticationResult;
import com.oopsiedaisy.config.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface AuthenticationMapper {
    AuthenticationRequest toDomain(AuthenticationRequestResource resource);

    AuthenticationResultResource toResource(AuthenticationResult  domain);
}
