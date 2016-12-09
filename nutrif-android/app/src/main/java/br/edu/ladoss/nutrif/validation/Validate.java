package br.edu.ladoss.nutrif.validation;

import br.edu.ladoss.nutrif.model.Pessoa;

public class Validate {
	
	private static StringValidator stringValidator = new StringValidator();

	private static NumeroValidator numeroValidator = new NumeroValidator();

	private static EmailValidator emailValidator = new EmailValidator();

	private static ImageValidator imageValidator = new ImageValidator();

	private static DataValidator dataValidator = new DataValidator();

	public static int VALIDATE_OK = 0;

	public static boolean identificador(String identificador) {

		boolean isValidated = false;

		if (emailValidator.validate(identificador)
				|| matricula(identificador)){

			isValidated = true;
		}

		return isValidated;
	}

	public static boolean senha(String senha) {

		boolean isValidated = false;

		isValidated = stringValidator.validate(senha, Pessoa.LENGHT_MIN_SENHA,
				Pessoa.LENGHT_MAX_SENHA);

		return isValidated;
	}

	public static boolean matricula(String matricula){
		return stringValidator.validate(matricula, Pessoa.LENGHT_MATRICULA);
	}

	public static boolean codigoAtivacao(String codigo){
		return stringValidator.validate(codigo);
	}

}
