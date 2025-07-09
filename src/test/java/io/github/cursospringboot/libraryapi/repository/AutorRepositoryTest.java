package io.github.cursospringboot.libraryapi.repository;
import io.github.cursospringboot.libraryapi.model.Autor;
import io.github.cursospringboot.libraryapi.model.GeneroLivro;
import io.github.cursospringboot.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Gabriel");
        autor.setNacionalidade("Indiano");
        autor.setDataNascimento(LocalDate.of(2008, 5, 26));
        autorRepository.save(autor);
        System.out.println("Autor salvo com sucesso: " + autor.getNome() + " - ID: " + autor.getId());
    }

    @Test
    public void atualizarTest() {
        Optional<Autor> autorOptional =  autorRepository.findById(UUID.fromString("285bf555-248c-48fd-aa89-f836aa38d18e"));
        if (autorOptional.isPresent()) {
            Autor foundAutor = autorOptional.get();
            System.out.println("Dados do autor: ");
            System.out.println(foundAutor);

            foundAutor.setDataNascimento(LocalDate.of(2006, 5, 12));
            autorRepository.save(foundAutor);
        } else {
            System.out.println("Autor não encontrado.");
        }
    }
    @Test
    public void listarTodos() {
        List<Autor> lista  = autorRepository.findAll();
        lista.forEach(System.out::println);
    }
    @Test
    public void CountTest() {
        System.out.println("Contagem de autors: " + autorRepository.count());
    }
    @Test
    public void deleteByIdTest() {
        Optional<Autor> optionalAutor = autorRepository.findById(UUID.fromString("9b0f29dc-6b46-4a84-90cb-3193a60366e8"));
        autorRepository.deleteById(optionalAutor.get().getId());
    }
    @Test
    public void deleteObjectTest() {
        Optional<Autor> autorOptional = autorRepository.findById(UUID.fromString("50ded7db-1d5f-4a7f-aa5e-08c99cf0e4e7"));
        var joao = autorOptional.get();
        autorRepository.delete(joao);
    }
    @Test
    public void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Matheus");
        autor.setNacionalidade("Madagascar");
        autor.setDataNascimento(LocalDate.of(2008, 9, 29));

        Livro livro = new Livro();
        livro.setTitulo("Crepusculo");
        livro.setIsbn("47364732-76342781");
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setPreco(BigDecimal.valueOf(242));
        livro.setDataPublicacao(LocalDate.of(2022, 10, 10));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setTitulo("A evolucao da vida");
        livro2.setIsbn("47364732-76342781");
        livro2.setGenero(GeneroLivro.CIENCIA);
        livro2.setPreco(BigDecimal.valueOf(444));
        livro2.setDataPublicacao(LocalDate.of(2025, 3, 10));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);
        livroRepository.saveAll(autor.getLivros());
    }
    @Test
    @Transactional
    public void mostrarLivrosAutor(){
         var autor = autorRepository.findById(UUID.fromString("0e80e346-3ca7-4379-b258-cedfd509bc37")).get();

         //Buscar os livros do autor
        List<Livro> livros = livroRepository.findByAutor(autor);
        autor.setLivros(livros);

         autor.getLivros().forEach(System.out::println);
    }
}
