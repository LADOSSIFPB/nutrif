package br.edu.ifpb.nutrif.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.edu.ifpb.nutrif.util.StringUtil;

public class StringValidator implements NutrIFValidator {

	private Pattern pattern;
	private Pattern patternLetras;
	private Pattern patternPassword;
	private Matcher matcher;

	private static final String STRING_PATTERN = "[0-9a-zA-ZáàâãéèêíïóôõöúüçñÁÀÂÃÉÈÍÏÓÔÕÖÚÜÇÑ .,/-ºª ]*";
	private static final String STRING_PATTERN_SOMENTE_LETRAS = "[a-zA-ZáàâãéèêíïóôõöúüçñÁÀÂÃÉÈÍÏÓÔÕÖÚÜÇÑ ]*";

	// Verifica se há, ao menos:
	// - um número ou caractere especial;
	// - uma letra minúscula;
	// - uma letra maiúscula.
	// O tamanho deve está entre 8 e 25 caracteres.
	private static final String PASSWORD_PATTERN = "((?=.*[0-9a-zA-Z]).{6,25})";

	public StringValidator() {
		pattern = Pattern.compile(STRING_PATTERN);
		patternLetras = Pattern.compile(STRING_PATTERN_SOMENTE_LETRAS);
		patternPassword = Pattern.compile(PASSWORD_PATTERN);
	}

	@Override
	public boolean validate(final String value) {
		
		boolean isValidate = false;
		
		if (!StringUtil.isEmptyOrNull(value)) {
		
			matcher = pattern.matcher(value.trim());
			isValidate = matcher.matches();
		}
		
		return isValidate;
	}
	
	public boolean validateSomenteLetras(final String value) {
		
		boolean isValidate = false;
		
		if (!StringUtil.isEmptyOrNull(value)) {
			matcher = patternLetras.matcher(value.trim());
			isValidate = matcher.matches();
		}		
		
		return isValidate;
	}

	public boolean validate(final String value, int tamanho) {
		
		boolean isValidate = validate(value);
		
		return (isValidate && value.length() <= tamanho);
	}

	public boolean validate(final String value, int tamanhoMenor,
			int tamanhoMaior) {
		
		return (!StringUtil.isEmptyOrNull(value) 
				&& value.length() >= tamanhoMenor 
				&& value.length() <= tamanhoMaior);
		
	}

	public boolean validate(String pattern, final String value,
			int tamanhoMenor, int tamanhoMaior) {
		
		return (validate(value) && (value.length() >= tamanhoMenor 
				&& value.length() <= tamanhoMaior));
	}

	public boolean validatePassword(final String password) {
		
		boolean isValidate = false;
		
		if (password != null 
				&& !password.trim().equals(StringUtil.STRING_VAZIO)) {

			matcher = patternPassword.matcher(password);
			isValidate = matcher.matches();
		}
		
		return isValidate;
	}
}