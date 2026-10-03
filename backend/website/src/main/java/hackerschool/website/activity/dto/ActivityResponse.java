package hackerschool.website.activity.dto;

import hackerschool.website.activity.domain.Activity;
import hackerschool.website.activity.domain.ActivityStatus;

import java.time.LocalDateTime;

public record ActivityResponse(
		Long id,
		String name,
		String description,
		String location,
		LocalDateTime startingTime,
		LocalDateTime endingTime,
		LocalDateTime applicationDeadline,
		Integer capacity,
		Long freeSeats,
		Long priceCents,
		ActivityStatus status) {

	public static ActivityResponse from(Activity activity, Long freeSeats) {
		return new ActivityResponse(
				activity.getId(),
				activity.getName(),
				activity.getDescription(),
				activity.getLocation(),
				activity.getStartingTime(),
				activity.getEndingTime(),
				activity.getApplicationDeadline(),
				activity.getCapacity(),
				freeSeats,
				activity.getPriceCents(),
				activity.getStatus());
	}
}
