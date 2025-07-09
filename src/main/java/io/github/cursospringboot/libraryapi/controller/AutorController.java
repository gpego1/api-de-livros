package io.github.cursospringboot.libraryapi.controller;
import io.github.cursospringboot.libraryapi.controller.dto.AutorDTO;
import io.github.cursospringboot.libraryapi.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autorDTO){
        var autorObj = autorDTO.mapearParaAutor();
        autorService.salvar(autorObj);

        //Retornar o id do usuario recem-criado na header da requisicao
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorObj.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
