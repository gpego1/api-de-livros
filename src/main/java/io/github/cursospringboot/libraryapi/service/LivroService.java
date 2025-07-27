package io.github.cursospringboot.libraryapi.service;
import io.github.cursospringboot.libraryapi.model.Autor;
import io.github.cursospringboot.libraryapi.model.GeneroLivro;
import io.github.cursospringboot.libraryapi.model.Livro;
import io.github.cursospringboot.libraryapi.model.User;
import io.github.cursospringboot.libraryapi.repository.LivroRepository;
import io.github.cursospringboot.libraryapi.security.SecurityService;
import io.github.cursospringboot.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final LivroValidator validator;
    private final SecurityService securityService;

    public List<Livro> pesquisa(String isbn, String titulo,  LocalDate dataPublicacao,GeneroLivro genero, Autor autor){
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setIsbn(isbn);
        livro.setGenero(genero);
        livro.setDataPublicacao(dataPublicacao);
        livro.setAutor(autor);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Livro> livroExample = Example.of(livro, matcher);
        return repository.findAll(livroExample);
    }

    public Livro salvar(Livro livro) {
        validator.validar(livro);
        User usuario = securityService.obterUsuarioLogado();
        livro.setUsuario(usuario);
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){return repository.findById(id);}

    public void deletar(Livro livro){repository.delete(livro);}

    public void atualizar(Livro livro){
       if(livro.getId() == null){
           throw new IllegalArgumentException("Para atualizar, eh necessario que o livro ja esteja cadastrado no bacno.");
       }
       validator.validar(livro);
       repository.save(livro);
    }
}
