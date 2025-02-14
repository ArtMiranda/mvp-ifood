package br.dev.arturmiranda.mvpifood.repository;

import br.dev.arturmiranda.mvpifood.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
}
