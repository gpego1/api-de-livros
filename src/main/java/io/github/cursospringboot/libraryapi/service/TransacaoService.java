package io.github.cursospringboot.libraryapi.service;
import io.github.cursospringboot.libraryapi.model.Autor;
import io.github.cursospringboot.libraryapi.model.GeneroLivro;
import io.github.cursospringboot.libraryapi.model.Livro;
import io.github.cursospringboot.libraryapi.repository.AutorRepository;
import io.github.cursospringboot.libraryapi.repository.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Transactional
    public void atualizacaoSemAtualizar(){
        Livro livro = livroRepository.findById(UUID.fromString("ad9c4a2b-34ed-48ef-94ec-fb332d8ed84f")).orElse(null);
        livro.setDataPublicacao(LocalDate.of(2020, 10, 15));
    }

    @Transactional
    public void executar(){
        Autor autor = new Autor();
        autor.setNome("Julie");
        autor.setNacionalidade("Estadunidense");
        autor.setDataNascimento(LocalDate.of(1990, 5, 30));
        autorRepository.save(autor);

        if(autor.getNome().equals("Julie")){
            throw new RuntimeException("Rollback");
        }

        Livro livro = new Livro();
        livro.setTitulo("As fumacas falarao por mim");
        livro.setIsbn("47364732-76342781");
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setPreco(BigDecimal.valueOf(242));
        livro.setDataPublicacao(LocalDate.of(2022, 10, 10));
        livro.setAutor(autor);
        livroRepository.save(livro);
    }
}
