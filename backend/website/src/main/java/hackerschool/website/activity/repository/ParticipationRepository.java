package hackerschool.website.activity.repository;

import hackerschool.website.activity.domain.Participation;
import hackerschool.website.activity.domain.ParticipationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findAllByActivityId(Long activityId);
    List<Participation> findAllByClientId(Long clientId);

    // True if the client has a participation in this activity whose status is not the given one (pass CANCELLED)
    boolean existsByActivityIdAndClientIdAndStatusNot(Long activityId, Long clientId, ParticipationStatus status);

    // Cancels every unpaid participation created before the cutoff, freeing its seat. Returns how many were cancelled.
    @Modifying
    @Query("""
           update Participation p
           set p.status = hackerschool.website.activity.domain.ParticipationStatus.CANCELLED
           where p.status = hackerschool.website.activity.domain.ParticipationStatus.PENDING_PAYMENT
             and p.createdAt < :cutoff
           """)
    int cancelUnpaidCreatedBefore(@Param("cutoff") LocalDateTime cutoff);
}