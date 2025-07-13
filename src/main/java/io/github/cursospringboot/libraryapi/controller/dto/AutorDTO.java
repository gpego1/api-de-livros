package io.github.cursospringboot.libraryapi.controller.dto;
import io.github.cursospringboot.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
       @NotBlank(message = "Campo de nome eh obrigatorio")
       @Size(min = 2,max=100, message = "Campo fora do tamanho padrao") String nome,

       @NotNull
        @Past(message = "Nao pode ser uma data futura")
        LocalDate dataNascimento,

       @NotBlank(message = "Campo de nacionalidade eh obrigatorio")
        @Size(min = 2,max=50, message = "Campo fora do tamanho padrao") String nacionalidade) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }

}
