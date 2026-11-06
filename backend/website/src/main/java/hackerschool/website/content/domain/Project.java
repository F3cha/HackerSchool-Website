package hackerschool.website.content.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.BooleanFlag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Project")
@Getter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String tittle;

    @Setter
    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(updatable = false)
    @NotNull
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Setter
    @BooleanFlag
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean approved = false;

    @Column(nullable = false)
    private long createdById; //TODO check if only admin can create

    //TODO Add image URL, and approve

    public Project(String tittle, String description, long createdById) {
        this.tittle = tittle;
        this.description = description;
        this.createdById = createdById;


    }

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}