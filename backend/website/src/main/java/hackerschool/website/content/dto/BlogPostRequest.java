package hackerschool.website.content.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BlogPostRequest(
        @NotBlank @Size(max = 255) String title,
        @NotBlank String content,
        @NotNull String imageUrl // Optional field for the image URL or path
) {
}
