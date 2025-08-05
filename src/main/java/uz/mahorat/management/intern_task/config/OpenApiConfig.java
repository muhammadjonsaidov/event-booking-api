package uz.mahorat.management.intern_task.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.Servers;


@OpenAPIDefinition(
        info = @Info(
                title = "Event Booking API",
                version = "1.0.0",
                description = "This API allows users to create and register for events. It's a comprehensive solution for event management.",
                contact = @Contact(
                        name = "Muhammadjon Saidov", // O'zingizning ismingiz
                        email = "saidovmuhammadjon76@gmail.com",
                        url = "https://github.com/muhammadjonsaidov" // Portfolio yoki GitHub profilingiz
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @Server(
                        description = "Local Development Server",
                        url = "http://localhost:8080"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT authentication token. To get a token, use the login endpoint. Then, paste the token here.",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}