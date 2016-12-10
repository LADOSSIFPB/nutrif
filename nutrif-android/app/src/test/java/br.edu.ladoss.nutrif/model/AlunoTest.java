package br.edu.ladoss.nutrif.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.edu.ladoss.nutrif.exceptions.DadoInvalidoException;

/**
 * Created by Juan on 09/12/2016.
 */

public class AlunoTest {

    private Aluno aluno;

    @Before
    public void initAluno(){
        aluno = new Aluno();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testConstrutorMatriculaIncorreta() throws DadoInvalidoException {
        thrown.expect(DadoInvalidoException.class);
        thrown.expectMessage(DadoInvalidoException.MATRICULA_INVALIDA);
        new Aluno("201210040422", "juanlyrabarros@gmail.com", "s123mioi");
    }
}
