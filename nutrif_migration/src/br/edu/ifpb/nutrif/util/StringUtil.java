package br.edu.ifpb.nutrif.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

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
	
	public static String criptografarBase64(String valor) throws UnsupportedEncodingException{
		
		try {
			
			valor = DatatypeConverter
					.printBase64Binary(valor.getBytes("UTF-8"));
			
		} catch (UnsupportedEncodingException ex) {
			
			throw new IllegalStateException("Base-64: Cannot encode with UTF-8");
		}

		return valor;
	}
	
	public static String getRadomKeyConfirmation () {
		
		UUID uuid = UUID.randomUUID();
		String myRandom = uuid.toString();

		return myRandom.substring(0,5);
	}
	
	public static String replaceLastToEmptySpace(String text, String regex) {
		return replaceLast(text, regex, STRING_VAZIO);
	}

	public static String replaceLast(String text, String regex,
			String replacement) {
		return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
	}
	
	public static String upperCaseFirstChar(String nomeCompleto) {
		
		String nomeRetorno = WordsCapitalizer.capitalizeEveryWord(nomeCompleto);
		
		nomeRetorno = nomeRetorno.replace(" De ", " de ");
		nomeRetorno = nomeRetorno.replace(" Do ", " do ");        
		nomeRetorno = nomeRetorno.replace(" Dos ", " dos ");
		nomeRetorno = nomeRetorno.replace(" Da ", " da ");
		nomeRetorno = nomeRetorno.replace(" Das ", " das ");
		nomeRetorno = nomeRetorno.replace(" E ", " e ");
		nomeRetorno = nomeRetorno.replace(" Para ", " para ");
		
		return nomeRetorno;
	}
}
