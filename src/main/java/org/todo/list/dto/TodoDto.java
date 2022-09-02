package org.todo.list.dto;

import org.hibernate.validator.constraints.Length;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

public class TodoDto  implements Serializable {

    private Long id;

    @NotNull(message="Nome é Obrigatorio")
    @NotBlank(message="Não e permitido vazio")
    @Length(min=3,max=250,message="Não e permitido nomes menores que 3 e maiores que 250")
    private String nome;
    @JsonbDateFormat("dd/MM/yyyy HH:mm")
    private LocalDateTime dataCriacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
