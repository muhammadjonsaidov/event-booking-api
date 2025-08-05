package uz.mahorat.management.intern_task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.mahorat.management.intern_task.dto.CreateEventDto;
import uz.mahorat.management.intern_task.dto.EventDto;
import uz.mahorat.management.intern_task.exception.ResourceNotFoundException;
import uz.mahorat.management.intern_task.exception.UnauthorizedOperationException;
import uz.mahorat.management.intern_task.model.Event;
import uz.mahorat.management.intern_task.model.User;
import uz.mahorat.management.intern_task.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    /**
     * Yangi tadbir yaratadi.
     *
     * @param createEventDto Yangi tadbir ma'lumotlari.
     * @param currentUser    Tadbir tashkilotchisi.
     * @return Yaratilgan tadbirning DTO'si.
     */
    @Transactional
    public EventDto create(CreateEventDto createEventDto, User currentUser) {
        Event event = Event.builder()
                .title(createEventDto.getTitle())
                .description(createEventDto.getDescription())
                .date(createEventDto.getDate())
                .location(createEventDto.getLocation())
                .organizer(currentUser)
                .build();

        Event savedEvent = eventRepository.save(event);
        return toDto(savedEvent);
    }

    /**
     * Barcha mavjud tadbirlar ro'yxatini qaytaradi.
     *
     * @return Tadbirlar ro'yxati (List<EventDto>).
     */
    @Transactional(readOnly = true)
    public List<EventDto> getAll() {
        return eventRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * ID bo'yicha bitta tadbirni topadi.
     *
     * @param id Tadbir IDsi.
     * @return Topilgan tadbirning DTO'si.
     * @throws ResourceNotFoundException Agar tadbir topilmasa.
     */
    @Transactional(readOnly = true)
    public EventDto getById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        return toDto(event);
    }

    /**
     * Joriy foydalanuvchi tomonidan tashkil etilgan tadbirlar ro'yxatini qaytaradi.
     *
     * @param currentUser Joriy foydalanuvchi.
     * @return Foydalanuvchi tadbirlari ro'yxati.
     */
    @Transactional(readOnly = true)
    public List<EventDto> getMyEvents(User currentUser) {
        return eventRepository.findByOrganizer(currentUser).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Mavjud tadbirni yangilaydi. Faqat tadbir egasi uni yangilay oladi.
     *
     * @param eventId      Yangilanayotgan tadbir IDsi.
     * @param updateDto    Yangi ma'lumotlar.
     * @param currentUser  Operatsiyani bajarayotgan foydalanuvchi.
     * @return Yangilangan tadbirning DTO'si.
     * @throws ResourceNotFoundException Agar tadbir topilmasa.
     * @throws UnauthorizedOperationException Agar foydalanuvchi tadbir egasi bo'lmasa.
     */
    @Transactional
    public EventDto update(Long eventId, CreateEventDto updateDto, User currentUser) {
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        if (!existingEvent.getOrganizer().getId().equals(currentUser.getId())) {
            throw new UnauthorizedOperationException("You are not authorized to update this event.");
        }

        existingEvent.setTitle(updateDto.getTitle());
        existingEvent.setDescription(updateDto.getDescription());
        existingEvent.setDate(updateDto.getDate());
        existingEvent.setLocation(updateDto.getLocation());

        Event updatedEvent = eventRepository.save(existingEvent);
        return toDto(updatedEvent);
    }

    /**
     * Tadbirni o'chiradi. Faqat tadbir egasi uni o'chira oladi.
     *
     * @param eventId     O'chiriladigan tadbir IDsi.
     * @param currentUser Operatsiyani bajarayotgan foydalanuvchi.
     * @throws ResourceNotFoundException Agar tadbir topilmasa.
     * @throws UnauthorizedOperationException Agar foydalanuvchi tadbir egasi bo'lmasa.
     */
    @Transactional
    public void delete(Long eventId, User currentUser) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        if (!event.getOrganizer().getId().equals(currentUser.getId())) {
            throw new UnauthorizedOperationException("You are not authorized to delete this event.");
        }

        eventRepository.delete(event);
    }

    /**
     * Event entity'sini EventDto'ga o'giruvchi yordamchi metod.
     */
    private EventDto toDto(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .date(event.getDate())
                .location(event.getLocation())
                .organizerUsername(event.getOrganizer().getUsername())
                .build();
    }
}