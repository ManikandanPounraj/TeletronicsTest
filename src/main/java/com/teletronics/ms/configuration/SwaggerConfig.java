package com.teletronics.ms.configuration;


import com.fasterxml.classmate.TypeResolver;
import com.teletronics.ms.BootstrapApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apis(ConfigurableEnvironment environment, TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(environment.getProperty("config.host"))
                .groupName(environment.getProperty("spring.application.name"))
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BootstrapApplication.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfo(
                        "Teletronics Test Microservice APIs Documentation",
                        "List of Teletronics Test Microservice APIs Documentation",
                        "1.0.0", "https://developer.test.com/api-catalogue",
                        new Contact("test", "http://www.teletronics.ae",
                                "test@teletronics.com.ae"),
                        "Teletronics API license 1.0",
                        "https://developer.teletronics.com/api-catalogue", Collections.emptyList()));
                //.alternateTypeRules(
                  //      new AlternateTypeRule(
                    //            typeResolver.resolve(
                      //                  DeferredResult.class,
                        //                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                          //      typeResolver.resolve(WildcardType.class)));
    }

	/*
	 * public UiConfiguration uiConfig() { return UiConfigurationBuilder .builder()
	 * .operationsSorter(OperationsSorter.METHOD) .build(); }
	 */
}

