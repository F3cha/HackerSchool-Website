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



	private String bio;

	public Member(String name, String email, String istId, String bio) {
		super(name, email, istId);
		this.bio = bio;
	}

	public Member(String name, String email, String bio){
		super(name, email);
		this.bio = bio;
	}

	@Override
	public Role getRole() {
		if (this.getIstId()==null){
			return Role.EXTERNALMEMBER;
		}
		return Role.MEMBER;
	}

}
