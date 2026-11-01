package hackerschool.website.content.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.BooleanFlag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "blog_posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogPost{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String title;

    @Setter
    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Setter
    @NotBlank
    @Column(updatable = false)
    private Long createdByAdminId; // Admin lives in the user module, so only the id is stored

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Setter
    @BooleanFlag
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean approved = false;

    @Setter
    private String imageUrl; // Optional field for the image URL or path, depending on how images are handled in the application.

    //TODO : Add a field for the image URL or path if needed, depending on how images are handled in the application. (still to decide for the image handling)

    public BlogPost(String title, String content, Long createdByAdminId) {
        this.title = title;
        this.content = content;
        this.createdByAdminId = createdByAdminId;
        this.imageUrl = null; // Initialize imageUrl to null by default
    }

    @PrePersist
    public void onCreat() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;

    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void approve() {
        this.approved = true;
    }


}

