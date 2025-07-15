package io.github.cursospringboot.libraryapi.controller.commom;
import io.github.cursospringboot.libraryapi.controller.dto.ErroCampo;
import io.github.cursospringboot.libraryapi.controller.dto.ErroReposta;
import io.github.cursospringboot.libraryapi.exception.CampoInvalidoException;
import io.github.cursospringboot.libraryapi.exception.OperacaoNaoPermitidaException;
import io.github.cursospringboot.libraryapi.exception.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroReposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErroReposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validacao",
                listaErros);
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroReposta handleRegistroDuplicadoException(RegistroDuplicadoException e){
        return ErroReposta.conflito(e.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroReposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e){
        return ErroReposta.conflito(e.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroReposta handleErrosNaoTratados(RuntimeException e){
        return new ErroReposta(HttpStatus.INTERNAL_SERVER_ERROR.value(), "", List.of());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroReposta handleCampoInvalidoException(CampoInvalidoException e){
        return new ErroReposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validacao",
                List.of(new ErroCampo(e.getCampo(), e.getMessage()))
        );
    }
}
