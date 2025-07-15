package io.github.cursospringboot.libraryapi.controller;
import io.github.cursospringboot.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.cursospringboot.libraryapi.controller.dto.ErroReposta;
import io.github.cursospringboot.libraryapi.controller.dto.PesquisaLivroDTO;
import io.github.cursospringboot.libraryapi.controller.mappers.LivroMapper;
import io.github.cursospringboot.libraryapi.exception.RegistroDuplicadoException;
import io.github.cursospringboot.libraryapi.model.Autor;
import io.github.cursospringboot.libraryapi.model.GeneroLivro;
import io.github.cursospringboot.libraryapi.model.Livro;
import io.github.cursospringboot.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroMapper mapper;
    private final LivroService service;

    @GetMapping
    public ResponseEntity<List<PesquisaLivroDTO>> pesquisar(
            @RequestParam(value="isbn", required = false) String isbn,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "dataPublicacao", required = false) LocalDate dataPublicacao,
            @RequestParam(value = "genero", required = false) GeneroLivro genero,
            @RequestParam(value = "autor", required = false) Autor autor) {

        List<Livro> res = service.pesquisa(isbn, titulo, dataPublicacao, genero, autor);
        List<PesquisaLivroDTO> lista = res.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }


    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid CadastroLivroDTO dto){
        try {
            Livro livro = mapper.toEntity(dto);
            service.salvar(livro);
            var url = gerarHeaderLocation(livro.getId());
            return ResponseEntity.created(url).build();
        } catch(RegistroDuplicadoException e){
            var erroDTO = ErroReposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") String id,@RequestBody @Valid CadastroLivroDTO dto){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro livroObj = mapper.toEntity(dto);
                    livro.setDataPublicacao(livroObj.getDataPublicacao());
                    livro.setIsbn(livroObj.getIsbn());
                    livro.setPreco(livroObj.getPreco());
                    livro.setGenero(livroObj.getGenero());
                    livro.setTitulo(livroObj.getTitulo());
                    livro.setAutor(livroObj.getAutor());

                    service.atualizar(livro);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    service.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
