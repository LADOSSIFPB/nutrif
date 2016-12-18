package br.edu.ladoss.nutrif.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.edu.ladoss.nutrif.exceptions.DadoInvalidoException;

/**
 * Created by Juan on 09/12/2016.
 */

public class PessoaTest {
    private Pessoa pessoa;

    @Before
    public void initAluno(){
        pessoa = new Pessoa();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testConstrutorSenhaIncorreta() throws DadoInvalidoException {
        thrown.expect(DadoInvalidoException.class);
        thrown.expectMessage(DadoInvalidoException.SENHA_INVALIDA);
        new Pessoa("juanlyrabarros@gmail.com", "s");
    }

    @Test
    public void testConstrutorEmailIncorreta() throws DadoInvalidoException {
        thrown.expect(DadoInvalidoException.class);
        thrown.expectMessage(DadoInvalidoException.EMAIL_INVALIDO);
        new Pessoa("juanlyrabarros@@gmail.com", "s123mioi");
    }

}
