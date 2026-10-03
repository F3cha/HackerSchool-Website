package hackerschool.website.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("ADMIN")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends User {

	public Admin(String name, String email) {
		super(name, email);
	}

	@Override
	public Role getRole() {
		return Role.ADMIN;
	}

}
