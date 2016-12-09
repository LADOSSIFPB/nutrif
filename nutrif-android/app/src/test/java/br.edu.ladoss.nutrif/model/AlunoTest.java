package br.edu.ladoss.nutrif.model;

import org.junit.Before;
import org.junit.Test;

import br.edu.ladoss.nutrif.exceptions.AlunoInvalidoException;
import br.edu.ladoss.nutrif.model.Aluno;

/**
 * Created by Juan on 09/12/2016.
 */

public class AlunoTest {

    private Aluno aluno;

    @Before
    public void initAluno(){
        aluno = new Aluno();
    }

    @Test//(expected = AlunoInvalidoException.class)
    public void testConstrutorNulo(){
        new Aluno();
    }
}
