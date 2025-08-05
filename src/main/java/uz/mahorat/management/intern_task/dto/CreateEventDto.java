package uz.mahorat.management.intern_task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "Data Transfer Object for creating or updating an event")
public class CreateEventDto {

    @Schema(description = "The title of the event", example = "Spring Boot 3 Workshop")
    @NotBlank
    @Size(min = 3, max = 100)
    private String title;

    @Schema(description = "A detailed description of the event", example = "An intensive workshop covering the new features of Spring Boot 3.")
    @NotBlank
    private String description;

    @Schema(description = "The date and time of the event", example = "2026-10-15T14:30")
    @NotNull
    @Future(message = "Event date must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime date;

    @Schema(description = "The physical or virtual location of the event", example = "Online via Zoom")
    @NotBlank
    private String location;
}