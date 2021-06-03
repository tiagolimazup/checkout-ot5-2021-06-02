package br.com.zup.orange.autor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Autor {

    @Id
    @GeneratedValue
    Long id;

    String nome;

    String email;

    String descricao;

    @Deprecated
    Autor() {
    }

    Autor(String nome, String email, String descricao) {
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
    }
}
