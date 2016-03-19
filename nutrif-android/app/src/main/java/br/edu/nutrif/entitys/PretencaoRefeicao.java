package br.edu.nutrif.entitys;

import br.edu.nutrif.entitys.input.Id;

/**
 * Created by juan on 19/03/16.
 */
public class PretencaoRefeicao {
    private Id aluno;
    private Id dia;

    public PretencaoRefeicao(Id aluno, Id refeicao) {
        this.aluno = aluno;
        this.refeicao = refeicao;
    }

    private Id refeicao;

}
