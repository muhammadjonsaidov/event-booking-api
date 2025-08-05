package uz.mahorat.management.intern_task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mahorat.management.intern_task.dto.RegistrationDto;
import uz.mahorat.management.intern_task.exception.ResourceNotFoundException;
import uz.mahorat.management.intern_task.model.Event;
import uz.mahorat.management.intern_task.model.Registration;
import uz.mahorat.management.intern_task.model.RegistrationId;
import uz.mahorat.management.intern_task.model.User;
import uz.mahorat.management.intern_task.repository.EventRepository;
import uz.mahorat.management.intern_task.repository.RegistrationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;

    /**
     * Foydalanuvchini tadbirga ro'yxatdan o'tkazadi.
     *
     * @param eventId     Tadbir IDsi.
     * @param currentUser Ro'yxatdan o'tayotgan foydalanuvchi.
     * @throws ResourceNotFoundException Agar tadbir topilmasa.
     * @throws IllegalStateException Agar foydalanuvchi bu tadbirga allaqachon ro'yxatdan o'tgan bo'lsa
     *                               yoki o'zi tashkilotchi bo'lsa.
     */
    @Transactional
    public void registerForEvent(Long eventId, User currentUser) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        RegistrationId registrationId = new RegistrationId(currentUser.getId(), eventId);
        if (registrationRepository.existsById(registrationId)) {
            throw new IllegalStateException("You are already registered for this event.");
        }

        if (event.getOrganizer().getId().equals(currentUser.getId())) {
            throw new IllegalStateException("You cannot register for an event that you are organizing.");
        }

        Registration newRegistration = Registration.builder()
                .id(registrationId)
                .user(currentUser)
                .event(event)
                .build();

        registrationRepository.save(newRegistration);
    }

    /**
     * Joriy foydalanuvchi ro'yxatdan o'tgan barcha tadbirlarni qaytaradi.
     *
     * @param currentUser Joriy foydalanuvchi.
     * @return Registratsiyalar ro'yxati (List<RegistrationDto>).
     */
    @Transactional(readOnly = true)
    public List<RegistrationDto> getMyRegistrations(User currentUser) {
        return registrationRepository.findByUser(currentUser).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Registration entity'sini RegistrationDto'ga o'giruvchi yordamchi metod.
     */
    private RegistrationDto toDto(Registration registration) {
        return RegistrationDto.builder()
                .eventId(registration.getEvent().getId())
                .eventTitle(registration.getEvent().getTitle())
                .eventLocation(registration.getEvent().getLocation())
                .eventDate(registration.getEvent().getDate())
                .registeredAt(registration.getRegisteredAt())
                .build();
    }
}