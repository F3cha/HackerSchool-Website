package hackerschool.website.activity.dto;

import jakarta.validation.constraints.NotNull;

public record ParticipationRequest(
		@NotNull Long clientId) {
}
