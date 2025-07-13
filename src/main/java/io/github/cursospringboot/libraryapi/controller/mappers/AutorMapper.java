package io.github.cursospringboot.libraryapi.controller.mappers;
import io.github.cursospringboot.libraryapi.controller.dto.AutorDTO;
import io.github.cursospringboot.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);
    AutorDTO toDTO(Autor autor);
}
