package br.edu.ladoss.nutrif.model;

import java.io.Serializable;

/**
 * Created by juan on 15/03/16.
 */
public class Aluno extends Pessoa implements Serializable{
    private String matricula;
    private Curso curso;
    private String keyConfirmation;
    private byte[] photo;


    public Aluno(String matricula, String email, String senha) {
        this.matricula = matricula;
        this.senha = senha;
        this.email = email;
    }

    public String getKeyconfirmation() {
        return keyConfirmation;
    }

    public void setKeyconfirmation(String keyconfirmation) {
        this.keyConfirmation = keyconfirmation;
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

    public void setPhoto(byte[] photo){
        this.photo = photo;
    }

    public byte[] getPhoto(){
        return photo;
    }
}
