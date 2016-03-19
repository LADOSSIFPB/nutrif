package br.edu.nutrif.entitys.input;

import br.edu.nutrif.entitys.Aluno;

/**
 * Created by juan on 19/03/16.
 */
public class FormularioLogin {
    public String getEmail() {
        return email;
    }

    public FormularioLogin(Aluno aluno) {
        email = aluno.getEmail();
        senha = aluno.getSenha();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    private String email;
    private String senha;

}
