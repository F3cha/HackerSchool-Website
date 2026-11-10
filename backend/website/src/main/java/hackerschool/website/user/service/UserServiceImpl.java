package hackerschool.website.user.service;


import hackerschool.website.user.domain.Client;
import hackerschool.website.user.domain.Member;
import hackerschool.website.user.dto.UserResponse;
import hackerschool.website.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Member registerMember(String name, String email, String rawPassword, String istId, String bio) {
        return null;
    }

    @Override
    @Transactional
    public Client registerClient(String name, String email, String rawPassword, String istId, String bio) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        Client client = new Client(name, email, istId, bio);
        client.setPassword(encodedPassword);
        return userRepository.save(client);
    }

    @Override
    @Transactional
    public Client registerClient(String name, String email, String rawPassword, String phoneNumber) {
        return null;
    }

    @Override
    public UserResponse getUserById(Long userId) {
        return null;
    }
}
