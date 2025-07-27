package io.github.cursospringboot.libraryapi.service;
import io.github.cursospringboot.libraryapi.exception.OperacaoNaoPermitidaException;
import io.github.cursospringboot.libraryapi.model.Autor;
import io.github.cursospringboot.libraryapi.model.User;
import io.github.cursospringboot.libraryapi.repository.AutorRepository;
import io.github.cursospringboot.libraryapi.repository.LivroRepository;
import io.github.cursospringboot.libraryapi.security.SecurityService;
import io.github.cursospringboot.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;


    public Autor salvar(Autor autor){
        validator.validar(autor);
        User usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
        return autorRepository.save(autor);
    }
    public Autor atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("O usuario nao existe no banco de dados.");
        }
        validator.validar(autor);
        return autorRepository.save(autor);
    }
    public Optional<Autor> obterPorId(UUID id){
        return autorRepository.findById(id);
    }
    public void deletar(Autor autor){
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Autor: "+ autor.getNome() + " possui livros cadastrados");
        }
         autorRepository.delete(autor);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade){
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        ExampleMatcher matcher = ExampleMatcher.
                matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
       Example<Autor> autorExample =  Example.of(autor, matcher);
       return autorRepository.findAll(autorExample);
    }


}
