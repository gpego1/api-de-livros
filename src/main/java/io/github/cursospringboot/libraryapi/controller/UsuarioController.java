package io.github.cursospringboot.libraryapi.controller;
import io.github.cursospringboot.libraryapi.controller.dto.UserDTO;
import io.github.cursospringboot.libraryapi.controller.mappers.UsuarioMapper;
import io.github.cursospringboot.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UserDTO dto){
        var usuario = mapper.toEntity(dto);
        service.salvar(usuario);
    }
}
