package io.github.cursospringboot.libraryapi.validator;
import io.github.cursospringboot.libraryapi.exception.RegistroDuplicadoException;
import io.github.cursospringboot.libraryapi.model.Autor;
import io.github.cursospringboot.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    @Autowired
    private AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor){
        if(existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Autor ja cadastrado!");
        }
    }
    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> optionalAutor = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
        );
        if(autor.getId() == null){
            return optionalAutor.isPresent();
        }
        return !autor.getId().equals(optionalAutor.get().getId()) && optionalAutor.isPresent();
    }
}
