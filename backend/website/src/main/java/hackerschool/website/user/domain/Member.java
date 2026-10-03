package hackerschool.website.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("MEMBER")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends User {

	@Column(unique = true, length = 32)
	private String istId;

	private String bio;

	public Member(String name, String email, String istId, String bio) {
		super(name, email);
		this.istId = istId;
		this.bio = bio;
	}

	@Override
	public Role getRole() {
		return Role.MEMBER;
	}

}
