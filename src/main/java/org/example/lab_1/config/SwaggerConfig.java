package org.example.lab_1.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "E-commerce Api",
                description = "E-commerce", version = "1.0.0",
                contact = @Contact(
                        name = "Terentev Nikita",
                        email = "nitro-2003@mail.ru"
                )
        )
)
public class SwaggerConfig {

}
