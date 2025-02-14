package br.dev.arturmiranda.mvpifood.service;

import br.dev.arturmiranda.mvpifood.dto.user.UserDTO;
import br.dev.arturmiranda.mvpifood.dto.user.UserRegisterDTO;
import br.dev.arturmiranda.mvpifood.entity.user.User;
import br.dev.arturmiranda.mvpifood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO save(UserRegisterDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = new User(user);
        return new UserDTO(userRepository.save(newUser));
    }

    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserDTO::new);
    }

}
