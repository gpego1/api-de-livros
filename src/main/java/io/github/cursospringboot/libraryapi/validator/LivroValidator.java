package io.github.cursospringboot.libraryapi.validator;
import io.github.cursospringboot.libraryapi.exception.CampoInvalidoException;
import io.github.cursospringboot.libraryapi.exception.RegistroDuplicadoException;
import io.github.cursospringboot.libraryapi.model.Livro;
import io.github.cursospringboot.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository repository;

    public void validar(Livro livro){
        if(existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("Livro ja cadastrado");
        }
        if(isPrecoObrigatorio(livro)){
            throw new CampoInvalidoException("preco", "Para livros com ano de 2020+, o preco eh obrigatorio");
        }
    }
    private boolean existeLivroComIsbn(Livro livro){
        Optional<Livro> livroOptional = repository.findByIsbn(livro.getIsbn());

        if(livro.getId() == null){
            return livroOptional.isPresent();
        }

        return livroOptional
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));
    }
    private boolean isPrecoObrigatorio(Livro livro){
        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }
}
