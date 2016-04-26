package br.edu.ladoss.nutrif.util;

import br.edu.ladoss.nutrif.entitys.Pessoa;

/**
 * Created by juan on 15/03/16.
 */
public class ValidateUtil {
    public final static int EMAIL = 1;
    public final static int NOME = 2;
    public final static int MATRICULA = 3;
    public final static int OK = 0;
    public final static int SENHA = 4;

    public static int validateField(String field, int tipo) {
        switch (tipo) {
            case (EMAIL): {
                return (field.length() <= Pessoa.LENGHT_MAX_EMAIL
                        && field.length() >= Pessoa.LENGHT_MIN_EMAIL
                        && field.matches("^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$")) ? OK : EMAIL;
            }
            case (MATRICULA): {
                return (field.matches("^[0-9]{" + Pessoa.LENGHT_MATRICULA + "}$") ? OK : MATRICULA);
            }
            case (SENHA): {
                return (field.length() <= Pessoa.LENGHT_MAX_SENHA
                        && field.length() >= Pessoa.LENGHT_MIN_SENHA) ? OK : SENHA;
            }
            default:
                return OK;
        }
    }
}
