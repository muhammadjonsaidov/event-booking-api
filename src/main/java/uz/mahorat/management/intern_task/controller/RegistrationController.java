package uz.mahorat.management.intern_task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.mahorat.management.intern_task.dto.RegistrationDto;
import uz.mahorat.management.intern_task.model.User;
import uz.mahorat.management.intern_task.service.RegistrationService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "03. Registrations", description = "APIs for managing event registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    /**
     * Foydalanuvchini tadbirga ro'yxatdan o'tkazadi.
     * @param eventId Tadbir IDsi.
     * @param currentUser Joriy foydalanuvchi (token orqali olinadi).
     * @return HTTP 201 Created statusi.
     */

    @Operation(
            summary = "Register for an event",
            description = "Allows an authenticated user to register for a specific event. A user cannot register for their own event."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully registered for the event"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "409", description = "Conflict (e.g., already registered)")
    })
    @Parameter(name = "eventId", description = "The ID of the event to register for", required = true, example = "1")
    @PostMapping("/events/{eventId}/register")
    public ResponseEntity<Void> registerForEvent(@PathVariable Long eventId, @AuthenticationPrincipal User currentUser) {
        registrationService.registerForEvent(eventId, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Joriy foydalanuvchining barcha registratsiyalarini qaytaradi.
     * @param currentUser Joriy foydalanuvchi.
     * @return Registratsiyalar ro'yxati.
     */
    @Operation(
            summary = "Get my registrations",
            description = "Retrieves a list of all events the current authenticated user has registered for."
    )
    @GetMapping("/registrations/my")
    public ResponseEntity<List<RegistrationDto>> getMyRegistrations(@AuthenticationPrincipal User currentUser) {
        List<RegistrationDto> registrations = registrationService.getMyRegistrations(currentUser);
        return ResponseEntity.ok(registrations);
    }
}