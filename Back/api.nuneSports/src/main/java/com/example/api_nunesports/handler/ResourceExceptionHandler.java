package com.example.api_nunesports.handler;

import com.example.api_nunesports.domain.DetalhesErro;
import com.example.api_nunesports.handler.exception.BadRequestException;
import com.example.api_nunesports.handler.exception.ConflitoException;
import com.example.api_nunesports.handler.exception.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ClassUtils.getDescriptiveType;

@ControllerAdvice // permite que essa classe intercepte todas as exceções que aconteçam no código
public class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DetalhesErro>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<DetalhesErro> erros = ex.getBindingResult().
                getAllErrors()
                .stream()
                .map(erro -> conveterEmDetalhesErro(erro.getDefaultMessage()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    private DetalhesErro conveterEmDetalhesErro(String mensagemErro) {

        DetalhesErro erro = new DetalhesErro();
        erro.setTitulo("Argumentos Inválidos");
        erro.setStatus(400L);
        erro.setTimestamp(System.currentTimeMillis());
        erro.setMenssagemUsuario(mensagemErro);
        erro.setMenssagemDesenvolvedor(mensagemErro);
        return erro;
    }

    // ------------------------------- EXCEPTIONS CUSTOMIZADAS----------------------------------------

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<DetalhesErro> handleNullPointerException(NullPointerException ex) {

        DetalhesErro erro = new DetalhesErro();
        erro.setTitulo(ex.getLocalizedMessage() + " não informado!");
        erro.setMenssagemDesenvolvedor(getDevMessageFromStackTrace(ex));
        erro.setStatus(500L);
        erro.setTimestamp(System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<DetalhesErro> argumentoException(IllegalArgumentException ex) {
        DetalhesErro erro = new DetalhesErro();
        erro.setTitulo(ex.getLocalizedMessage() + " não informado!");
        erro.setMenssagemDesenvolvedor(ex.getMessage());
        erro.setStatus(500L);
        erro.setTimestamp(System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<DetalhesErro> handleNotFoundException(NotFound exception) {
        DetalhesErro erro = new DetalhesErro();
        erro.setTitulo("O objeto não encontrado!");
        erro.setStatus(404L);
        erro.setMenssagemDesenvolvedor(exception.getMessage());
        erro.setMenssagemUsuario(exception.getMessage());
        erro.setTimestamp(System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(ConflitoException.class)
    public ResponseEntity<DetalhesErro> handleConflitoException(ConflitoException ex) {

        DetalhesErro erro = new DetalhesErro();
        erro.setTitulo("Ocorreu um conflito!");
        erro.setMenssagemDesenvolvedor(ex.getMessage());
        erro.setMenssagemUsuario(ex.getMessage());
        erro.setStatus(409L);
        erro.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<DetalhesErro> handleBadRequestException(BadRequestException ex) {

        DetalhesErro erro = new DetalhesErro();
        erro.setTitulo("Houve um erro na requisição!");
        erro.setMenssagemDesenvolvedor(ex.getMessage());
        erro.setMenssagemUsuario(ex.getMessage());
        erro.setStatus(400L);
        erro.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }


    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<DetalhesErro> handlerNumberFormatException(NumberFormatException ex) {
        String titulo;
        String msgUser;

        titulo = "Parametro invalido";
        msgUser = "Esperava um numero, mas a entrada foi uma String";

        ex.printStackTrace();

        DetalhesErro erro = new DetalhesErro();
        erro.setMenssagemUsuario(msgUser);
        erro.setMenssagemDesenvolvedor(ex.getMessage());
        erro.setStatus(400L);
        erro.setTitulo(titulo);
        erro.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<DetalhesErro> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        String titulo;
        String msgUser;

        titulo = "Erro de desserialização";
        msgUser = "Não foi possivel converter o Json em um Object";

        ex.printStackTrace();

        DetalhesErro erro = new DetalhesErro();
        erro.setTimestamp(System.currentTimeMillis());
        erro.setTitulo(titulo);
        erro.setStatus(400L);
        erro.setMenssagemUsuario(msgUser);
        erro.setMenssagemDesenvolvedor(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<DetalhesErro> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {

        String titulo;
        String msgUser;
        String msgDev;
        String parametrosAusentes = ex.getParameterName();

        titulo = "Erro de preenchimento";
        msgUser = "Os seguintes parâmetros estão ausentes: " + parametrosAusentes;
        msgDev = getDevMessageFromStackTrace(ex);
        ex.printStackTrace();

        DetalhesErro erro = new DetalhesErro();
        erro.setTitulo(titulo);
        erro.setStatus(400L);
        erro.setTimestamp(System.currentTimeMillis());
        erro.setMenssagemUsuario(msgUser);
        erro.setMenssagemDesenvolvedor(msgDev);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<DetalhesErro> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        String titulo;
        String msgUser;
        String msgDev;
        String parametro = ex.getName();
        String tipoEsperado = ex.getRequiredType().getSimpleName();
        String tipoRecebido = ClassUtils.getShortName(getDescriptiveType(ex.getValue()));

        titulo = "Parâmetro inválido";
        msgUser = "Parâmetro '" + parametro + "' inválido. O tipo recebido foi " + tipoRecebido +
                ", mas era esperado receber o tipo " + tipoEsperado;
        msgDev = getDevMessageFromStackTrace(ex);

        ex.printStackTrace();

        DetalhesErro erro = new DetalhesErro();
        erro.setTitulo(titulo);
        erro.setMenssagemUsuario(msgUser);
        erro.setMenssagemDesenvolvedor(msgDev);
        erro.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // ---------------------------------------------- REGRAS DE NEGÓCIO ------------------------------------------------

    public String getDevMessageFromStackTrace(Exception ex) {

        String msgDev = ex.toString();

        for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
            if (stackTraceElement.getClassName().startsWith("com.decasa.produtos")) {

                String classe;
                String metodo;
                int linha;

                classe = stackTraceElement.getClassName();
                metodo = stackTraceElement.getMethodName();
                linha = stackTraceElement.getLineNumber();

                msgDev += ": " + classe + "(método " + metodo + ": linha " + linha + ")";

                break;
            }
        }

        return msgDev;
    }
}