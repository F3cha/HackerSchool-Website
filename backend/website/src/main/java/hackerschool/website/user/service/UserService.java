package hackerschool.website.user.service;

import hackerschool.website.user.domain.Client;
import hackerschool.website.user.domain.Member;
import hackerschool.website.user.dto.UserResponse;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    Member registerMember(String name, String email, String rawPassword, String istId, String bio);


    Client registerClient(String name, String email, String rawPassword, String istId, String bio);

    Client registerClient(String name, String email, String rawPassword, String phoneNumber);
    UserResponse getUserById(Long userId);
}