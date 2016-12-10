package br.edu.ladoss.nutrif.model;

import java.util.List;

import br.edu.ladoss.nutrif.exceptions.DadoInvalidoException;
import br.edu.ladoss.nutrif.validation.Validate;

public class Pessoa {

    private Integer id;

    private String nome;

    protected String senha;

    private String keyAuth;

    protected String email;

    private String tipo;

    private boolean ativo;

    private List<Role> roles;

    public static final int LENGHT_MATRICULA = 11;
    public static final int LENGHT_MAX_EMAIL = 30;
    public static final int LENGHT_MIN_EMAIL = 4;
    public static final int LENGHT_MAX_SENHA = 40;
    public static final int LENGHT_MIN_SENHA = 5;

    public Pessoa(String email, String senha) throws DadoInvalidoException {
        if (!Validate.validaIdentificador(email)){
            throw new DadoInvalidoException(DadoInvalidoException.EMAIL_INVALIDO);
        } else if (!Validate.validaSenha(senha)){
            throw new DadoInvalidoException(DadoInvalidoException.SENHA_INVALIDA);
        }
        this.email = email;
        this.senha = senha;
    }

    public Pessoa() {
    }

    public String getKeyAuth() {
        return keyAuth;
    }

    public void setKeyAuth(String keyAuth) {
        this.keyAuth = keyAuth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa)) return false;

        Pessoa pessoa = (Pessoa) o;

        if (senha != null ? !senha.equals(pessoa.senha) : pessoa.senha != null) return false;
        return email != null ? email.equals(pessoa.email) : pessoa.email == null;

    }

    @Override
    public int hashCode() {
        int result = senha != null ? senha.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Nome=" + nome + "\nEmail=" + email;
    }
}