package io.github.cursospringboot.libraryapi.controller.dto;
import io.github.cursospringboot.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @NotBlank(message = "campo obrigatorio") @ISBN String isbn,
        @NotBlank(message = "campo obrigatorio") String titulo,
        @NotNull(message = "campo obrigatorio") @Past(message = "nao pode ser uma data futura") LocalDate dataPublicacao,
        @NotNull(message = "campo obrigatorio") GeneroLivro genero,
        @NotNull(message = "campo obrigatorio") BigDecimal preco,
        @NotNull(message = "campo obrigatorio") UUID idAutor
) {
}
