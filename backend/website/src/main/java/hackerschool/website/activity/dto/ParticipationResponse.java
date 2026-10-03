package hackerschool.website.activity.dto;

import hackerschool.website.activity.domain.Participation;
import hackerschool.website.activity.domain.ParticipationStatus;

import java.time.LocalDateTime;

public record ParticipationResponse(
		Long id,
		Long activityId,
		Long clientId,
		ParticipationStatus status,
		LocalDateTime createdAt,
		LocalDateTime confirmedAt) {

	public static ParticipationResponse from(Participation participation) {
		return new ParticipationResponse(
				participation.getId(),
				participation.getActivity().getId(),
				participation.getClientId(),
				participation.getStatus(),
				participation.getCreatedAt(),
				participation.getConfirmedAt());
	}
}
