package com.oopsiedaisy.config;

import org.mapstruct.MapperConfig;

import static org.mapstruct.ReportingPolicy.IGNORE;

@MapperConfig(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface MapStructConfig {

}