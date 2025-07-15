package io.github.cursospringboot.libraryapi.repository;
import io.github.cursospringboot.libraryapi.model.Autor;
import io.github.cursospringboot.libraryapi.model.GeneroLivro;
import io.github.cursospringboot.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
/**
 * @see livroRepositoryTest
 */
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    boolean existsByAutor(Autor autor);

    //Query Method
    List<Livro> findByAutor(Autor autor);
    List<Livro> findByTitulo(String titulo);
    Optional<Livro> findByIsbn(String isbn);
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    //select * from livro where titulo = ? or isbn = ?
//    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    //select * from livro where data_publicacao between ? and ?
//    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    @Query( "select l from Livro as l order by l.titulo" )
    List<Livro> listarTodos();

    @Query( "select a from Livro l join l.autor a " )
    List<Autor> listarAutoresDosLivros();

    //select distinct l.nome
    @Query("select distinct l.titulo from Livro l")
    List<String> listarDiferentesLivros();

    @Query("""
            select l.genero
            from Livro l 
            join l.autor a
            where a.nacionalidade = 'Indiano'
            order by l.genero    
""")
    List<String> listarGeneroAutoresIndianos();

    //named parameters
    @Query( "select l from Livro l where l.genero = :genero order by :paramOrder" )
    List<Livro> findByGenero(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrder") String nomePropriedade
    );

    //positional parameters
    @Query( "select l from Livro l where l.genero = ?1 order by ?2" )
    List<Livro> findByGeneroPositionalParameters(GeneroLivro generoLivro,String nomePropriedade);

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate dataPublicacao);


}
