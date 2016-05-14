package br.edu.ladoss.nutrif.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumeroValidator implements NutrIFValidator{

	private Pattern pattern;
	private Matcher matcher;

	private static final String STRING_PATTERN = "[0-9]*";

	public NumeroValidator() {
		pattern = Pattern.compile(STRING_PATTERN);
	}

	@Override
	public boolean validate(final String value) {
		if (value == null || value.trim().equals(""))
			return false;
		matcher = pattern.matcher(value.trim());
		return matcher.matches();
	}

	public boolean validate(final String value, int tamanho) {
		return (validate(value) 
				&& value.length() <= tamanho);
	}

	public boolean validate(final String value, int tamanhoMenor, 
			int tamanhoMaior) {
		
		return (validate(value) 
				&& (value.length() >= tamanhoMenor 
					&& value.length() <= tamanhoMaior));
	}

	public boolean isInteiroPositivo(int valor) {
		return (valor >= 0);
	}

	public boolean isDoublePositivo(double valor) {
		return (valor >= 0);
	}
	
	public boolean isMaiorZero(int valor) {
		return (valor > 0);
	}
}
