package hackerschool.website.user.domain;

import hackerschool.website.auth.domain.AuthUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role; // Uses the external Role.java

    @Column(length = 20)
    private String phoneNumber;

    @Column(unique = true, length = 32)
    private String istId;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String photoPath;

    //@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "user")
    //private AuthUser authUser;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Builder
    public User(String name, Role role, AuthUser authUser, String email, String istId, String photoPath, String phoneNumber, String bio) {
        this.name = name;
        this.role = (role != null) ? role : Role.CLIENT;
        this.email = email;
        this.istId = istId;
        this.photoPath = photoPath;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
      //  setAuthUser(authUser);
    }

    //public void setAuthUser(AuthUser authUser) {
      //  this.authUser = authUser;
        //if (authUser != null) {
         //   authUser.setUser(this);
        //}
    //}

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }
}