package uz.mahorat.management.intern_task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.mahorat.management.intern_task.dto.CreateEventDto;
import uz.mahorat.management.intern_task.dto.EventDto;
import uz.mahorat.management.intern_task.model.User;
import uz.mahorat.management.intern_task.service.EventService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "02. Events", description = "APIs for creating, viewing, updating, and deleting events")
public class EventController {

    private final EventService eventService;

    @Operation(summary = "Create a new event", description = "Creates a new event. Only authenticated users can create events.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Event created successfully", content = @Content(schema = @Schema(implementation = EventDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid event data provided", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "401", description = "User is not authenticated", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is authenticated but not authorized", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody CreateEventDto createEventDto, @AuthenticationPrincipal User currentUser) {
        EventDto createdEvent = eventService.create(createEventDto, currentUser);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all events", description = "Retrieves a list of all available events.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of events")
    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @Operation(summary = "Get an event by its ID", description = "Retrieves a single event's details by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the event"),
            @ApiResponse(responseCode = "404", description = "Event not found with the given ID", content = @Content)
    })
    @Parameter(name = "id", description = "The unique identifier of the event", required = true, example = "1")
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getById(id));
    }

    @Operation(
            summary = "Get my organized events",
            description = "Retrieves a list of all events organized by the current authenticated user."
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of user's events")
    @GetMapping("/my-events")
    public ResponseEntity<List<EventDto>> getMyOrganizedEvents(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(eventService.getMyEvents(currentUser));
    }

    @Operation(
            summary = "Update an existing event",
            description = "Updates the details of an existing event. Only the organizer of the event can perform this action."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid event data provided"),
            @ApiResponse(responseCode = "403", description = "User is not the organizer of this event"),
            @ApiResponse(responseCode = "404", description = "Event not found with the given ID")
    })
    @Parameter(name = "id", description = "The ID of the event to update", required = true, example = "1")
    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @Valid @RequestBody CreateEventDto updateDto, @AuthenticationPrincipal User currentUser) {
        EventDto updatedEvent = eventService.update(id, updateDto, currentUser);
        return ResponseEntity.ok(updatedEvent);
    }

    @Operation(
            summary = "Delete an event",
            description = "Deletes an event. Only the organizer of the event can perform this action."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "403", description = "User is not the organizer of this event"),
            @ApiResponse(responseCode = "404", description = "Event not found with the given ID")
    })
    @Parameter(name = "id", description = "The ID of the event to delete", required = true, example = "1")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        eventService.delete(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}