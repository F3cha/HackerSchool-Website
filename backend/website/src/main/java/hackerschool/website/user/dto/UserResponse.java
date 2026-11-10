package hackerschool.website.user.dto;

import hackerschool.website.user.domain.Role;

public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String istID;
    private String PhotoPath;
    private Role role;

    public UserResponse(Long id, String username, String email, String istID, String photoPath, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.istID = istID;
        this.PhotoPath = photoPath;
        this.role = role;
    }


}
