package com.marine.vessel_keeper.config;

import org.mapstruct.MapperConfig;
import org.springframework.context.annotation.ComponentScan;
import org.mapstruct.BeanMapping;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.marine.vessel_keeper"})
public class AppConfiguration {
}
