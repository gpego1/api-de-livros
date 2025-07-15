package io.github.cursospringboot.libraryapi.repository;
import io.github.cursospringboot.libraryapi.model.Autor;
import io.github.cursospringboot.libraryapi.model.GeneroLivro;
import io.github.cursospringboot.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void createLivro(){
        Livro livro = new Livro();
        livro.setTitulo("Senhor dos Aneis");
        livro.setIsbn("47364732-76342781");
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setPreco(BigDecimal.valueOf(124));
        livro.setDataPublicacao(LocalDate.of(1991, 10, 10));

        livro.setAutor(autorRepository.findById(
                UUID.fromString("c92c8fff-336e-49e0-9956-a5205b8f6f07"))
                .orElse(null));

//        Autor autor = new Autor();
//        autor.setNome("J.K Rowling");
//        autor.setNacionalidade("Britanica");
//        autor.setDataNascimento(LocalDate.of(1956, 3, 19));
//
//
//       livro.setAutor(autor);

        livroRepository.save(livro);
        System.out.println("Livro: " + livro.getTitulo() + " escrito por: "+ livro.getAutor() +" Salvo com sucesso");
    }
    @Test
    public void atualizarAutorByLivro(){
        UUID id = UUID.fromString("d359b40c-e1b7-4e7c-9add-f487e7329747");
        var updateLivro = livroRepository.findById(id).orElse(null);

        Autor autor = autorRepository.findById(UUID.fromString("d6456184-17c4-48eb-9ee0-edb423eee76d")).orElse(null);
        updateLivro.setAutor(autor);
        livroRepository.save(updateLivro);
    }
    @Test
    public void deletar(){
        UUID id = UUID.fromString("b183e534-0e7f-4b6c-b2a0-95ca7c7f0e27");
        livroRepository.deleteById(id);
    }
    @Test
    @Transactional
    public void buscarLivroTest(){
        UUID id = UUID.fromString("fd07a14f-5bfb-4756-9dbf-709e5ec1f923");
        Livro livro = livroRepository.findById(id).orElse(null);
        System.out.println("Dados do Livro: ");
        System.out.println(livro.getTitulo());
        System.out.println(livro.getAutor().getNome());
        System.out.println(livro.getDataPublicacao());
    }

    @Test
    public void findAllTest() {
        List<Livro> lista = livroRepository.findAll();
    }
    @Test
    public void encontrarPorTitulo(){
        List<Livro> lista = livroRepository.findByTitulo("Senhor dos Aneis");
        lista.forEach(System.out::println);
    }
    @Test
    public void encontrarPorIsbn(){
        Optional<Livro> livro = livroRepository.findByIsbn("47364732-76342781");
        livro.ifPresent(System.out::println);
    }
    @Test
    public void encontrarPorTituloAndPreco(){
        var titulo = "Senhor dos Aneis";
        var preco = BigDecimal.valueOf(124);

        List<Livro> lista = livroRepository.findByTituloAndPreco(titulo, preco);
        lista.forEach(System.out::println);
    }
    @Test
    public void encontrarPorTituloOuIsbn(){
        var titulo = "Senhor dos Aneis";
        var preco = BigDecimal.valueOf(124);

        List<Livro> lista = livroRepository.findByTituloAndPreco(titulo, preco);
        lista.forEach(System.out::println);
    }
    @Test
    public void listarTodosComQuery(){
        List<Livro> todosLivros = livroRepository.listarTodos();
        todosLivros.forEach(System.out::println);
    }
    @Test
    public void ListarAutoresComQuery(){
        List<Autor> autores = livroRepository.listarAutoresDosLivros();
        autores.forEach(System.out::println);
    }
    @Test
    public void ListarDiferentesTitulos(){
        List<String> titulos = livroRepository.listarDiferentesLivros();
        titulos.forEach(System.out::println);
    }
    @Test
    public void ListarDiferentesAutores(){
        List<String> generos = livroRepository.listarGeneroAutoresIndianos();
        generos.forEach(System.out::println);
    }
    @Test
    public void getByGenero(){
        var generosLivros = livroRepository.findByGenero(GeneroLivro.FICCAO, "preco");
        generosLivros.forEach(System.out::println);
    }
    @Test
    public void getByGeneroPositionalParameters(){
        var generosLivros = livroRepository.findByGeneroPositionalParameters(GeneroLivro.FICCAO, "preco");
        generosLivros.forEach(System.out::println);
    }
    @Test
    public void deleteBygenero(){
        livroRepository.deleteByGenero(GeneroLivro.CIENCIA);
    }
    @Test
    public void mudarGenero(){
        Optional<Livro> livroOptional = livroRepository.findById(UUID.fromString("a4b48e84-b5e5-499a-88cd-237db53b115e"));
        if(livroOptional.isPresent()){
            Livro livro = livroOptional.get();
            livro.setGenero(GeneroLivro.CIENCIA);
            livroRepository.save(livro);
        } else {
            System.out.println("Nenhum livro foi encontado com o ID: " + livroOptional.get().getId());
        }
    }
    @Test
    public void updateDataPublicacao(){
        livroRepository.updateDataPublicacao(LocalDate.of(2000,10,10));
    }
}
