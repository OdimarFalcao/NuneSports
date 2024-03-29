package com.example.api_nunesports.domain;

// CLASSE USADA APENAS NAS EXECEPTIONS

import java.util.List;

/**
 * created by Emanoel Adolpho on 17/03/2021
 */
public class DetalhesErro {

    private String titulo;
    private Long status;
    private Long timestamp;
    private String menssagemDesenvolvedor;
    private String menssagemUsuario;

    private List<String> mensagenUsuariosLista;

    public List<String> getMensagenUsuariosLista() {
        return mensagenUsuariosLista;
    }

    public void setMensagenUsuariosLista(List<String> mensagenUsuariosLista) {
        this.mensagenUsuariosLista = mensagenUsuariosLista;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMenssagemDesenvolvedor() {
        return menssagemDesenvolvedor;
    }

    public void setMenssagemDesenvolvedor(String menssagemDesenvolvedor) {
        this.menssagemDesenvolvedor = menssagemDesenvolvedor;
    }

    public String getMenssagemUsuario() {
        return menssagemUsuario;
    }

    public void setMenssagemUsuario(String menssagemUsuario) {
        this.menssagemUsuario = menssagemUsuario;
    }
}