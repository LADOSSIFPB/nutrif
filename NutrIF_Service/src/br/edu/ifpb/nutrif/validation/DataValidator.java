package br.edu.ifpb.nutrif.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataValidator implements NutrIFValidator {

	public static final String FORMATO_DATA = "dd/MM/yyyy";
	
	public static final int ANO_ZERO = 0;

	public DataValidator() {}

	@Override
	public boolean validate(final String value) {

		if (value == null || value.trim().equals(""))
			return false;

		boolean valido = true;

		try {
			
			SimpleDateFormat format = new SimpleDateFormat(FORMATO_DATA);
			format.setLenient(false);
			format.parse(value.trim());
			
		} catch (ParseException e) {
			
			valido = false;
		}

		return valido;
	}

	public boolean datesInOrder(final Date dataMenor, final Date dataMaior) {

		if (dataMenor == null || dataMaior == null)
			return false;

		int valido = dataMenor.compareTo(dataMaior);

		return valido <= 0 ? true : false;
	}
}
