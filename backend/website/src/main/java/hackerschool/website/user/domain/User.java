package hackerschool.website.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING, length = 20)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Column(nullable = false)
	private String name;

	@Setter
	@Column(nullable = false, unique = true)
	private String email;

	/** Storage key of the profile photo. */
	@Setter
	private String photoPath;

	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	protected User(String name, String email) {
		this.name = name;
		this.email = email;
	}

	@PrePersist
	void onCreate() {
		createdAt = Instant.now();
	}

	public abstract Role getRole();

}
