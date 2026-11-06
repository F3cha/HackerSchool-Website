package hackerschool.website.content.dto;

import hackerschool.website.content.domain.BlogPost;

import java.time.LocalDateTime;

public record BlogPostResponse(
        String title,
        String content,
        String imageUrl, // Optional field for the image URL or path
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static BlogPostResponse from(BlogPost blogPost) {
        return new BlogPostResponse(
                blogPost.getTitle(),
                blogPost.getContent(),
                blogPost.getImageUrl(),
                blogPost.getCreatedAt(),
                blogPost.getUpdatedAt()
        );
    }


}
