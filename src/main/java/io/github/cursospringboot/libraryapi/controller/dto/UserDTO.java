package io.github.cursospringboot.libraryapi.controller.dto;
import io.github.cursospringboot.libraryapi.model.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UserDTO(
        @NotBlank(message = "Campo obrigatorio") String login,
        @Email(message = "email invalido") String email,
        @NotBlank(message = "Campo obrigatorio") String senha,
        List<String> roles
) {
}
