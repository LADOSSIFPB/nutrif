package br.edu.ladoss.nutrif.model;

import java.io.Serializable;

public class DiaRefeicao implements Serializable{

    private Aluno aluno;
    private Integer id;
    private Dia dia;
    private Refeicao refeicao;
    private String codigo;
    private Matricula matricula;

    public DiaRefeicao(){
        aluno = new Aluno();
        id = new Integer(0);
        dia = new Dia();
        refeicao = new Refeicao();
        matricula = new Matricula();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    private boolean ativo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public Refeicao getRefeicao() {
        return refeicao;
    }

    public void setRefeicao(Refeicao refeicao) {
        this.refeicao = refeicao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Matricula getMatricula() {
        return matricula;
    }
}