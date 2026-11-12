package hackerschool.website.user.domain;

/**
 * Kind of user. The names double as the discriminator values in {@code users.role}.
 */
public enum Role {
    MEMBER,
    ADMIN,
    CLIENT,
    EXTERNALMEMBER;

    public static final class Constants {
        public static final String MEMBER = "MEMBER";
        public static final String ADMIN = "ADMIN";
        public static final String CLIENT = "CLIENT";
        public static final String EXTERNALMEMBER = "EXTERNALMEMBER";

        private Constants() {
        }
    }
}

