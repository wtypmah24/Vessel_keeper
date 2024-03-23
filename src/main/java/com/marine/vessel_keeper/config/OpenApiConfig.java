package com.marine.vessel_keeper.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Vessel keeper",
                description = "API for control and manage your fleet.", version = "1.0.0",
                contact = @Contact(
                        name = "Taras Shevchenko",
                        email = "wtypmah48@gmail.com"
                )))

public class OpenApiConfig {
}