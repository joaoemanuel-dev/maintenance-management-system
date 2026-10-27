package com.joao.empresa.handler;

import com.joao.empresa.dto.error.ErrorResponse;
import com.joao.empresa.exceptions.EmpresaJaCadastradaException;
import com.joao.empresa.exceptions.EmpresaNaoEncontradaException;
import com.joao.empresa.exceptions.EntidadeEmUsoException;
import com.joao.empresa.exceptions.EquipamentoJaCadastradoException;
import com.joao.empresa.exceptions.EquipamentoNaoEncontradoException;
import com.joao.empresa.exceptions.ManutencaoNaoEncontradaException;
import com.joao.empresa.exceptions.UsuarioJaCadastradoException;
import com.joao.empresa.exceptions.UsuarioNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice // essa classe vai observar as controllers e tratar exceptions lançadas nas requisições
public class GlobalExceptionHandler {

    // se qualquer uma dessas exceptions acontecer durante uma requisição, execute este método
    @ExceptionHandler({
            EmpresaNaoEncontradaException.class,
            EquipamentoNaoEncontradoException.class,
            ManutencaoNaoEncontradaException.class,
            UsuarioNaoEncontradoException.class
    })
    public ResponseEntity<ErrorResponse> tratarNaoEncontrado(
            RuntimeException exception,
            HttpServletRequest request
    ) {

        return criarResposta(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler({
            EmpresaJaCadastradaException.class,
            EquipamentoJaCadastradoException.class,
            UsuarioJaCadastradoException.class,
            EntidadeEmUsoException.class,
            IllegalStateException.class
    })
    public ResponseEntity<ErrorResponse> tratarConflito(
            RuntimeException exception,
            HttpServletRequest request
    ) {

        return criarResposta(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> tratarArgumentoInvalido(
            IllegalArgumentException exception,
            HttpServletRequest request
    ) {

        return criarResposta(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> tratarValidacao(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {

        String mensagem = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(erro -> erro.getField()
                        + ": "
                        + erro.getDefaultMessage())
                .orElse("Dados inválidos.");

        return criarResposta(
                HttpStatus.BAD_REQUEST,
                mensagem,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> tratarErroInesperado(
            Exception exception,
            HttpServletRequest request
    ) {

        return criarResposta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno no servidor.",
                request.getRequestURI()
        );
    }

    private ResponseEntity<ErrorResponse> criarResposta(
            HttpStatus status,
            String mensagem,
            String path
    ) {

        ErrorResponse erro = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                mensagem,
                path
        );

        return ResponseEntity
                .status(status)
                .body(erro);
    }
}