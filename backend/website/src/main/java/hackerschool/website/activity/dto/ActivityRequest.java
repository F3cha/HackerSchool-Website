package hackerschool.website.activity.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ActivityRequest(
		@NotBlank @Size(max = 255) String name,
		String description,
		String location,
		@NotNull @Future LocalDateTime startingTime,
		@NotNull @Future LocalDateTime endingTime,
		@NotNull @Future LocalDateTime applicationDeadline,
		@NotNull @Positive Integer capacity,
		@NotNull @PositiveOrZero Long priceCents) {
}
