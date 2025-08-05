package uz.mahorat.management.intern_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mahorat.management.intern_task.model.Event;
import uz.mahorat.management.intern_task.model.User;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrganizer(User organizer);
}