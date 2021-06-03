package br.com.zup.orange.autor;

import com.fasterxml.jackson.annotation.JsonProperty;

class CriacaoDeNovoAutorRequest {

    @JsonProperty
    final String nome;

    @JsonProperty
    final String email;

    @JsonProperty
    final String descricao;

    CriacaoDeNovoAutorRequest(String nome, String email, String descricao) {
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
    }

    Autor toModel() {
        return new Autor(nome, email, descricao);
    }
}
