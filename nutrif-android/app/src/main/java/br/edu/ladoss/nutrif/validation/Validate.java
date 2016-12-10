package br.edu.ladoss.nutrif.validation;

import br.edu.ladoss.nutrif.model.Pessoa;

public class Validate {
	
	private static StringValidator stringValidator = new StringValidator();

	private static NumeroValidator numeroValidator = new NumeroValidator();

	private static EmailValidator emailValidator = new EmailValidator();

	private static ImageValidator imageValidator = new ImageValidator();

	private static DataValidator dataValidator = new DataValidator();

	public static int VALIDATE_OK = 0;

	/**
	 * Recebe um identificador e verifica se ele obedece a regra.
	 * Um identificador obedece a duas formatações, a de email e a de matrícula.
	 * É tentado identificar os dois padrões, se nenhum deles for encontrado, é retornado
	 * falso.
	 * @param identificador representa email ou matrícula a ser checada
	 * @return true se for encontrado o padrão
     */
	public static boolean validaIdentificador(String identificador) {

		boolean isValidated = false;

		if (emailValidator.validate(identificador)
				|| validaMatricula(identificador)){

			isValidated = true;
		}

		return isValidated;
	}

	/**
	 * Valida uma validaSenha recebida como parâmetro.
	 * @param senha
	 * @return true se a validaSenha estiver correta.
	 */
	public static boolean validaSenha(String senha) {

		boolean isValidated = false;

		isValidated = stringValidator.validate(senha, Pessoa.LENGHT_MIN_SENHA,
				Pessoa.LENGHT_MAX_SENHA);

		return isValidated;
	}

	/**
	 * Valida uma matrícula recebida como parâmetro.
	 * @param matricula
	 * @return true se a matrícula estiver correta.
     */
	public static boolean validaMatricula(String matricula){
		return stringValidator.validate(matricula, Pessoa.LENGHT_MATRICULA);
	}

	public static boolean validaCodigoAtivacao(String codigo){
		return stringValidator.validate(codigo);
	}

}
