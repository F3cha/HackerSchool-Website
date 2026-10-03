package hackerschool.website.activity.repository;

import hackerschool.website.activity.domain.Participation;
import hackerschool.website.activity.domain.ParticipationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findAllByActivityId(Long activityId);
    List<Participation> findAllByClientId(Long clientId);

    // True if the client has a participation in this activity whose status is not the given one (pass CANCELLED)
    boolean existsByActivityIdAndClientIdAndStatusNot(Long activityId, Long clientId, ParticipationStatus status);
}