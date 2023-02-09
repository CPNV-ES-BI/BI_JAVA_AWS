package com.cpnv.bijavaaws.config;

import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class ConfigMock {

    @Bean
    public WebEndpointsSupplier webEndpointServletHandlerMapping() {
        return mock(WebEndpointsSupplier.class);
    }
}
