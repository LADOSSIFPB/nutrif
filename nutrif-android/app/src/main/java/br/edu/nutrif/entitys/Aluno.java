package br.edu.nutrif.entitys;

/**
 * Created by juan on 15/03/16.
 */
public class Aluno extends Pessoa {
    private String matricula;
    private Id curso;
    private String keyconfirmation;

    public Aluno(String matricula, String email, String senha) {
        this.matricula = matricula;
        this.senha = senha;
        this.email = email;
    }

    public String getKeyconfirmation() {
        return keyconfirmation;
    }

    public Id getCurso() {
        return curso;
    }

    public void setCurso(Id curso) {
        this.curso = curso;
    }

    public void setKeyconfirmation(String keyconfirmation) {
        this.keyconfirmation = keyconfirmation;
    }

    public Aluno() {
        super();
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }


}
