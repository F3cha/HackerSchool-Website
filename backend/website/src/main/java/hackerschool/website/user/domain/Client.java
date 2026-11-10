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


	public Client(String name, String email, String phoneNumber, String istId) {
		super(name, email);

	}

	public Client(String name, String email, String phoneNumber){
		super(name, email);
	}


	@Override
	public Role getRole() {
		return Role.CLIENT;
	}

}
