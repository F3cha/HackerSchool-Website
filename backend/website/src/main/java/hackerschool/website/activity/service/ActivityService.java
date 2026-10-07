package hackerschool.website.activity.service;

import hackerschool.website.activity.domain.Activity;
import hackerschool.website.activity.domain.ActivityStatus;
import hackerschool.website.activity.domain.Participation;
import hackerschool.website.activity.domain.ParticipationStatus;
import hackerschool.website.activity.dto.ActivityRequest;
import hackerschool.website.activity.dto.ActivityResponse;
import hackerschool.website.activity.dto.ParticipationRequest;
import hackerschool.website.activity.dto.ParticipationResponse;
import hackerschool.website.activity.repository.ActivityRepository;
import hackerschool.website.activity.repository.ParticipationRepository;
import hackerschool.website.shared.exception.BadRequestException;
import hackerschool.website.shared.exception.ConflictException;
import hackerschool.website.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class ActivityService {
    private static final List<ParticipationStatus> SEAT_TAKING_STATUSES = List.of(ParticipationStatus.PENDING_PAYMENT, ParticipationStatus.CONFIRMED);
    private static final Duration PAYMENT_WINDOW = Duration.ofMinutes(30); // time to pay before the seat is released
    private final ActivityRepository activityRepository;
    private final ParticipationRepository participationRepository;

    @Transactional
    public ActivityResponse createActivity(ActivityRequest request, Long adminId) {
        validateDates(request);
        Activity activity = activityRepository.save(new Activity(request.name(), request.description(), request.location(), request.startingTime(), request.endingTime(), request.applicationDeadline(), request.capacity(), request.priceCents(), adminId)
        );
        return ActivityResponse.from(activity, activity.getCapacity().longValue());
    }

    @Transactional
    public ActivityResponse updateActivity(Long id, ActivityRequest request) {
        validateDates(request);
        Activity activity = findActivity(id);
        activity.setName(request.name());
        activity.setDescription(request.description());
        activity.setLocation(request.location());
        activity.setStartingTime(request.startingTime());
        activity.setEndingTime(request.endingTime());
        activity.setApplicationDeadline(request.applicationDeadline());
        activity.setCapacity(request.capacity());
        activity.setPriceCents(request.priceCents());
        return toResponse(activity);
    }

    @Transactional
    public ActivityResponse publishActivity(Long id) {
        Activity activity = findActivity(id);
        if (activity.getStatus() != ActivityStatus.DRAFT) {
            throw new ConflictException("Only draft activities can be published");
        }
        activity.setStatus(ActivityStatus.PUBLISHED);
        return toResponse(activity);
    }

    @Transactional
    public ActivityResponse cancelActivity(Long id) {
        Activity activity = findActivity(id);
        if (activity.getStatus() == ActivityStatus.CANCELLED) {
            throw new ConflictException("Activity is already cancelled");
        }
        activity.setStatus(ActivityStatus.CANCELLED);
        // TODO: refund confirmed participations of paid activities once payments exist
        participationRepository.findAllByActivityId(id).forEach(Participation::cancelParticipation);
        return toResponse(activity);
    }

    @Transactional(readOnly = true)
    public ActivityResponse getActivity(Long id) {
        return toResponse(findActivity(id));
    }

    // Public page: published activities only, soonest first
    @Transactional(readOnly = true)
    public List<ActivityResponse> listPublishedActivities() {
        return activityRepository.findAllByStatusOrderByStartingTimeAsc(ActivityStatus.PUBLISHED).stream()
                .map(this::toResponse)
                .toList();
    }

    // Admin view: every activity, newest first
    @Transactional(readOnly = true)
    public List<ActivityResponse> listAllActivities() {
        return activityRepository.findAllByOrderByStartingTimeDesc().stream()
                .map(this::toResponse)
                .toList();
    }

    // Unpaid participations lose their seat once the payment window has passed.
    // Runs every minute, and at the start of each registration so the seat count is never stale there.
    @Scheduled(fixedDelay = 1, initialDelay = 1, timeUnit = TimeUnit.MINUTES)
    @Transactional
    public void releaseUnpaidSeats() {
        participationRepository.cancelUnpaidCreatedBefore(LocalDateTime.now().minus(PAYMENT_WINDOW));
    }

    @Transactional
    public ParticipationResponse register(Long activityId, ParticipationRequest request) {
        releaseUnpaidSeats();
        Activity activity = activityRepository.findByIdForUpdate(activityId)
                .orElseThrow(() -> new NotFoundException("Activity", activityId));
        if (!activity.isRegistrationOpen()) {
            throw new BadRequestException("Registration is not open for this activity");
        }
        if (participationRepository.existsByActivityIdAndClientIdAndStatusNot(activityId, request.clientId(), ParticipationStatus.CANCELLED)) {
            throw new ConflictException("Client is already registered for this activity");
        }
        if (countFreeSeats(activity) <= 0) {
            throw new ConflictException("Activity is full");
        }
        Participation participation = participationRepository.save(new Participation(activity, request.clientId()));
        return ParticipationResponse.from(participation);
    }

    // Marks a pending participation as paid. Rejected if the payment window has already passed.
    @Transactional
    public ParticipationResponse confirmParticipation(Long participationId) {
        releaseUnpaidSeats();
        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> new NotFoundException("Participation", participationId));
        if (participation.getStatus() != ParticipationStatus.PENDING_PAYMENT) {
            throw new ConflictException("Only pending participations can be confirmed");
        }
        participation.confirm();
        return ParticipationResponse.from(participation);
    }

    private Activity findActivity(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Activity", id));
    }

    private long countFreeSeats(Activity activity) {
        return activityRepository.countFreeSeats(activity.getId(), SEAT_TAKING_STATUSES)
                .orElse(activity.getCapacity().longValue());
    }

    private ActivityResponse toResponse(Activity activity) {
        return ActivityResponse.from(activity, countFreeSeats(activity));
    }

    private void validateDates(ActivityRequest request) {
        if (request.startingTime().isAfter(request.endingTime())) {
            throw new BadRequestException("Starting time must be before ending time");
        }
        if (request.applicationDeadline().isAfter(request.startingTime())) {
            throw new BadRequestException("Application deadline must be before starting time");
        }
    }


}
