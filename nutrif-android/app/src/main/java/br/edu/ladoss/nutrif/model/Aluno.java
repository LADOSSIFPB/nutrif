package br.edu.ladoss.nutrif.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

import br.edu.ladoss.nutrif.exceptions.DadoInvalidoException;
import br.edu.ladoss.nutrif.validation.Validate;

/**
 * Created by juan on 15/03/16.
 */

/**
 * No modelo do problema, Aluno representa qualquer estudante da instituição que possui um permissão
 * para utilizar o restaurante do IFPB.
 */
public class Aluno extends Pessoa implements Serializable{

    @XmlTransient
    private String matricula;

    private String keyConfirmation;

    private int idMigracao;

    private boolean acesso;

    private boolean suap;

    @XmlTransient
    private byte[] photo;

    public Aluno(String matricula, String email, String senha) throws DadoInvalidoException {
        super(email, senha);
        if (!Validate.validaIdentificador(matricula)){
            throw new DadoInvalidoException(DadoInvalidoException.MATRICULA_INVALIDA);
        }
        this.matricula = matricula;
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

    public String getKeyconfirmation() {
        return keyConfirmation;
    }

    public void setKeyconfirmation(String keyconfirmation) {
        this.keyConfirmation = keyconfirmation;
    }

    public int getIdMigracao() {
        return idMigracao;
    }

    public void setIdMigracao(int idMigracao) {
        this.idMigracao = idMigracao;
    }

    public boolean isAcesso() {
        return acesso;
    }

    public void setAcesso(boolean acesso) {
        this.acesso = acesso;
    }

    public boolean isSuap() {
        return suap;
    }

    public void setSuap(boolean suap) {
        this.suap = suap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aluno)) return false;

        Aluno aluno = (Aluno) o;

        return matricula != null ? matricula.equals(aluno.matricula) : aluno.matricula == null;

    }

    @Override
    public int hashCode() {
        return matricula != null ? matricula.hashCode() : 0;
    }
}
