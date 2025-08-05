package uz.mahorat.management.intern_task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object for new user registration")
public class RegisterRequest {

    @Schema(description = "A unique username for the new user.", requiredMode = Schema.RequiredMode.REQUIRED, example = "vali")
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @Schema(description = "A unique email address for the new user.", requiredMode = Schema.RequiredMode.REQUIRED, example = "vali@example.com")
    @NotBlank
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "Password for the new user. Must be at least 6 characters long.", requiredMode = Schema.RequiredMode.REQUIRED, example = "password456")
    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
}