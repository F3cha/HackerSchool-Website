package hackerschool.website.user.domain;

/**
 * Kind of user. The names double as the discriminator values in {@code users.role}.
 */
public enum Role {
	MEMBER,
	ADMIN,
	CLIENT,
	EXTERNALMEMBER
}
