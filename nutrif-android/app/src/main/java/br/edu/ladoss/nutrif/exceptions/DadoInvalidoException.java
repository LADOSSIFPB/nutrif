package br.edu.ladoss.nutrif.exceptions;

/**
 * Created by Juan on 09/12/2016.
 */

public class DadoInvalidoException extends Exception {
    public static final String MATRICULA_INVALIDA = "Essa matrícula não obedece ao formato.";
    public static final String SENHA_INVALIDA = "A senha não obedece ao formato adequado.";
    public static final String EMAIL_INVALIDO = "O email não obedece ao formato adequado.";

    public DadoInvalidoException(String detailMessage) {
        super(detailMessage);
    }
}
