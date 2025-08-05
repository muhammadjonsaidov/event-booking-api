package uz.mahorat.management.intern_task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Data Transfer Object representing a user's registration for an event")
public class RegistrationDto {

    @Schema(description = "The ID of the event the user registered for.", example = "1")
    private Long eventId;

    @Schema(description = "The title of the event.", example = "Mega Java Konferensiyasi 2026")
    private String eventTitle;

    @Schema(description = "The location of the event.", example = "Toshkent, International Hotel")
    private String eventLocation;

    @Schema(description = "The date and time of the event.", example = "2026-05-21T09:30")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eventDate;

    @Schema(description = "The timestamp when the user registered for the event.", example = "2025-08-06 14:20:15")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registeredAt;
}