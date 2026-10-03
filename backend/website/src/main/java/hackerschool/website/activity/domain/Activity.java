package hackerschool.website.activity.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String name;

    @Setter
    private String description;

    @Setter
    private String location;

    @Setter
    @NotNull
    @Column(nullable = false)
    private LocalDateTime startingTime;

    @Setter
    @NotNull
    @Column(nullable = false)
    private LocalDateTime endingTime;

    @Setter
    @NotNull
    @Column(nullable = false)
    private LocalDateTime applicationDeadline;

    @Setter
    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer capacity; // Maximum number of participants, not the remaining number of participants you monkey :D

    @Setter
    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Long priceCents; // 0 = free

    @Setter
    private String photoPath; // storage key, not the image itself

    @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ActivityStatus status = ActivityStatus.DRAFT;

    @Column(updatable = false)
    private Long createdByAdminId; // Admin lives in the user module, so only the id is stored

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Activity(String name, String description, String location,
                    LocalDateTime startingTime, LocalDateTime endingTime, LocalDateTime applicationDeadline,
                    Integer capacity, Long priceCents, Long createdByAdminId) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.applicationDeadline = applicationDeadline;
        this.capacity = capacity;
        this.priceCents = priceCents;
        this.createdByAdminId = createdByAdminId;
    }

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public boolean isFree() {
        return priceCents == 0;
    }

    public boolean isRegistrationOpen() {
        return status == ActivityStatus.PUBLISHED && LocalDateTime.now().isBefore(applicationDeadline);
    }

    public boolean hasEnded() {
        return LocalDateTime.now().isAfter(endingTime);
    }

}
