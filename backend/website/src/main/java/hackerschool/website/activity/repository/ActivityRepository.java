package hackerschool.website.activity.repository;

import hackerschool.website.activity.domain.Activity;
import hackerschool.website.activity.domain.ActivityStatus;
import hackerschool.website.activity.domain.ParticipationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    // All activities with a given status (e.g. PUBLISHED for the public page), soonest first
    List<Activity> findAllByStatusOrderByStartingTimeAsc(ActivityStatus status);

    // Activities with a given status that haven't started yet, soonest first
    List<Activity> findAllByStatusAndStartingTimeAfterOrderByStartingTimeAsc(ActivityStatus status, LocalDateTime now);

    // Admin view: every activity, newest first
    List<Activity> findAllByOrderByStartingTimeDesc();

    // Free seats = capacity - participations with one of the given statuses.
    // Empty if the activity doesn't exist. Can be negative if capacity was lowered after people registered.
    @Query("""
           select a.capacity - count(p)
           from Activity a
           left join Participation p on p.activity = a and p.status in :seatTakingStatuses
           where a.id = :activityId
           group by a.id, a.capacity
           """)
    Optional<Long> countFreeSeats(@Param("activityId") Long activityId,
                                  @Param("seatTakingStatuses") Collection<ParticipationStatus> seatTakingStatuses);

}
