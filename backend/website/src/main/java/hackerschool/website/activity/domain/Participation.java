package hackerschool.website.activity.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participation{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "activity_id", nullable = false, updatable = false)
    private Activity activity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long clientId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ParticipationStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime confirmedAt;


    public Participation(Activity activity, Long clientId){
        this.activity = activity;
        this.clientId = clientId;
        if (activity.isFree()){
            this.status = ParticipationStatus.CONFIRMED;
            this.confirmedAt = LocalDateTime.now();
        } else {
            this.status = ParticipationStatus.PENDING_PAYMENT;
        }
    }

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public void cancelParticipation() { // Not sure if we should include this
        this.status = ParticipationStatus.CANCELLED;
    }

    public void confirm() {
        if (status != ParticipationStatus.PENDING_PAYMENT) {
            throw new IllegalStateException("Only pending participations can be confirmed");
        }
        status = ParticipationStatus.CONFIRMED;
        confirmedAt = LocalDateTime.now();
    }

    public boolean takesSeat() {
        return status == ParticipationStatus.CONFIRMED;
    }






}