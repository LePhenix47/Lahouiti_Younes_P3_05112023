package com.openclassrooms.p3.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(contact = @Contact(name = "Younes LAHOUITI", email = "youneslahouiti@duck.com", url = "https://younes-portfolio-dev.vercel.app/"), description = "OpenApi Swagger Documentation for the Chatop back-end server", title = "Swagger doc for Chatop", version = "1.0", license = @License(name = "MIT License")

), servers = {
                @Server(description = "Local ENV", url = "http://localhost:3001/"),
                @Server(description = "Prod ENV (Work in progress)", url = "http://chatop.server/"),
})
@SecurityScheme(name = "bearerAuth", description = "JWT auth description",

                scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {

}
