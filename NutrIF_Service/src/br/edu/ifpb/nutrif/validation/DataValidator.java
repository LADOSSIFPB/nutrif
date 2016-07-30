package br.edu.ifpb.nutrif.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.ifpb.nutrif.util.StringUtil;

public class DataValidator implements NutrIFValidator {

	public static final String FORMATO_DATA_HORA = "yyyy-MM-dd HH:mm:ss";
	
	public static final String FORMATO_DATA = "yyyy-MM-dd";
	
	public static final int ANO_ZERO = 0;

	public DataValidator() {}

	@Override
	public boolean validate(final String date) {
		
		return false;
	}

	public boolean datesInOrder(final Date dataMenor, final Date dataMaior) {

		if (dataMenor == null || dataMaior == null)
			return false;

		int valido = dataMenor.compareTo(dataMaior);

		return valido <= 0 ? true : false;
	}

	public boolean validateFormat(final Date date, String format) {

		boolean valido = true;
		
		try {			
			
			if (date != null) {
				
				String value = date.toString().trim();
				
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
				simpleDateFormat.setLenient(false);
				simpleDateFormat.parse(value);
			
			} else {
				
				valido = false;
			}			
			
		} catch (ParseException e) {
			
			valido = false;
		}

		return valido;
	}
}
