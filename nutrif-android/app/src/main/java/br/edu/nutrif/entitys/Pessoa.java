package br.edu.nutrif.entitys;

public class Pessoa {
    private Integer id;
    private String nome;
    protected String senha;
    private String keyauth;
    protected String email;
    private String tipo;
    private boolean ativo;

    public static final int LENGHT_MATRICULA = 11;
    public static final int LENGHT_MAX_EMAIL = 30;
    public static final int LENGHT_MIN_EMAIL = 4;
    public static final int LENGHT_MAX_SENHA = 40;
    public static final int LENGHT_MIN_SENHA = 5;

    public String getKeyauth() {
        return keyauth;
    }

    public void setKeyauth(String keyauth) {
        this.keyauth = keyauth;
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
    public String toString() {
        return "Pessoa [id=" + id + ", nome=" + nome + "email=" + email
                + " tipo=" + tipo + " ativo=" + ativo + "]";
    }
}