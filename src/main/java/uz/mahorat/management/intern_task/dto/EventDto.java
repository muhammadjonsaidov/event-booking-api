package uz.mahorat.management.intern_task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object representing a single event")
public class EventDto {

    @Schema(description = "The unique identifier of the event.", example = "1")
    private Long id;

    @Schema(description = "The title of the event.", example = "Mega Java Konferensiyasi 2026")
    private String title;

    @Schema(description = "A detailed description of the event.", example = "Java, Spring, Microservices va Cloud texnologiyalariga bag'ishlangan eng katta konferensiya.")
    private String description;

    @Schema(description = "The date and time when the event will take place.", example = "2026-05-21T09:30")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;

    @Schema(description = "The physical or virtual location of the event.", example = "Toshkent, International Hotel")
    private String location;

    @Schema(description = "The username of the user who organized the event.", example = "ali")
    private String organizerUsername;
}