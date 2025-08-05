package uz.mahorat.management.intern_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mahorat.management.intern_task.model.Registration;
import uz.mahorat.management.intern_task.model.RegistrationId;
import uz.mahorat.management.intern_task.model.User;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, RegistrationId> {
    List<Registration> findByUser(User user);
    boolean existsByUserIdAndEventId(Long userId, Long eventId);
}