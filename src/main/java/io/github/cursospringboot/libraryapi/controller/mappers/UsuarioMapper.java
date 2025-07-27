package io.github.cursospringboot.libraryapi.controller.mappers;
import io.github.cursospringboot.libraryapi.controller.dto.UserDTO;
import io.github.cursospringboot.libraryapi.model.User;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.RequestBody;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    User toEntity(@RequestBody UserDTO dto);
}
