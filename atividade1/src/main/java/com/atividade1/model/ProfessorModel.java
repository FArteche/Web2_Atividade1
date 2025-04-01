package com.atividade1.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="professor")
public class ProfessorModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    @OneToMany(mappedBy = "professor")
    private List<CursoModel> cursos;
    //CONSTUTORES
    public ProfessorModel(@NotBlank String nome, @NotBlank String email) {
        this.nome = nome;
        this.email = email;
    }
    public ProfessorModel() {
    }
    //GETTERS E SETTERS
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<CursoModel> getCursos() {
        return cursos;
    }
    public void setCursos(List<CursoModel> cursos) {
        this.cursos = cursos;
    }
}
