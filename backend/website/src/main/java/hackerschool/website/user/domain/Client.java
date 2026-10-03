package hackerschool.website.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("CLIENT")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client extends User {

	private String organization;

	public Client(String name, String email, String organization) {
		super(name, email);
		this.organization = organization;
	}

	@Override
	public Role getRole() {
		return Role.CLIENT;
	}

}
