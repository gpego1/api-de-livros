package io.github.cursospringboot.libraryapi.repository;
import io.github.cursospringboot.libraryapi.model.Autor;
import io.github.cursospringboot.libraryapi.model.GeneroLivro;
import io.github.cursospringboot.libraryapi.model.Livro;
import io.github.cursospringboot.libraryapi.service.TransacaoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class TransacoesTest {
    /**
     * Commit --> confirmar as alteracoes
     * Rollback -> Desfazer as alteracoes
     */
    @Autowired
    TransacaoService service;

    @Test
    public void executarAplicacao(){
        service.executar();
    }
    @Test
    public void update(){
        service.atualizacaoSemAtualizar();
    }

}
