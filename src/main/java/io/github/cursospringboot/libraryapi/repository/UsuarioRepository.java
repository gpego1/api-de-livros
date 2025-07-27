package io.github.cursospringboot.libraryapi.repository;
import io.github.cursospringboot.libraryapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<User, UUID> {
    User findByLogin(String login);

    User findByEmail(String email);
}
