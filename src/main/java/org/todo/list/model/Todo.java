package org.todo.list.model;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

//ENTIDADE JPA
@Entity
//EXPECIFICAR O NOME DA TABELA
@Table(name="todo")

@NamedNativeQueries({
        @NamedNativeQuery(name="CONSULTAR_TODO",query="SELECT id,nome,dataCriacao FROM todo",resultClass=Todo.class),
        @NamedNativeQuery(name="INSERIR_TODO",query="INSERT INTO todo(nome,dataCriacao) values(:nome,:dataCriacao)"),
        @NamedNativeQuery(name="EXCLUIR_TODO",query="DELETE FROM todo WHERE id=:id")

})

public class Todo implements Serializable{

    //CHAVE PRIMARIA
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;


    @Column(name="nome",length = 250,nullable=false)
    private String nome;
    @Column(name="dataCriacao",nullable = false,updatable=false)
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
