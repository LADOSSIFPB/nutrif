package br.edu.ladoss.nutrif.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.edu.ladoss.nutrif.util.StringUtil;

public class EmailValidator implements NutrIFValidator{

	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public EmailValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	/**
	 * Validate hex with regular expression
	 * 
	 * @param email
	 *            hex for validation
	 * @return true valid hex, false invalid hex
	 */
	public boolean validate(final String email) {
		
		boolean isEmail = false;
		
		if(!StringUtil.isEmptyOrNull(email)) {
			matcher = pattern.matcher(email.trim());
			isEmail = matcher.matches();
		}
		
		return isEmail;
	}
}
