package br.edu.ladoss.nutrif.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class StringUtil {
	
	public static final String STRING_VAZIO = "";

	public static boolean isEmptyOrNull(String value) {

		boolean isEmptyOrNull = false;
		
		if (value == null 
				|| (value != null && value.trim().equals(STRING_VAZIO))) {
			isEmptyOrNull = true;
		}
		
		return isEmptyOrNull;		
	}
	
	public static double tirarMascaraOrcamento(String orcamento) {

		orcamento = orcamento.replace(".", "");
		orcamento = orcamento.replace(",", ".");

		Double orc = Double.parseDouble(orcamento);

		return orc;
	}

	public static String tirarMascaraCNPJ(String cnpj) {

		cnpj = cnpj.replace(".", "");
		cnpj = cnpj.replace("/", "");
		cnpj = cnpj.replace("-", "");

		return cnpj;
	}

	public static String criptografarSha256(String valorPlano)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
		byte messageDigest[] = algorithm.digest(valorPlano.getBytes("UTF-8"));

		StringBuilder hexString = new StringBuilder();

		for (byte b : messageDigest) {
			hexString.append(String.format("%02X", 0xFF & b));
		}

		String senha = hexString.toString();

		return senha;
	}
	
	public static String replaceLastToEmptySpace(String text, String regex) {
		return replaceLast(text, regex, STRING_VAZIO);
	}

	public static String replaceLast(String text, String regex,
			String replacement) {
		return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
	}
}
