package io.github.cursospringboot.libraryapi.controller.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public record ErroReposta(int status, String mensagem, List<ErroCampo> erros) {

    public static ErroReposta respostaPadrao(String mensagem){
        return new ErroReposta(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }
    public static ErroReposta conflito(String mensagem){
        return new ErroReposta(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }
}
