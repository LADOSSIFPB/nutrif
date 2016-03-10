package br.edu.ifpb.nutrif.exception;

import java.util.HashMap;
import java.util.Map;

public class ErrorFactory {

	private ErrorFactory() {
		/*Default constructor*/
	}

	private static int index = 0;

	/*
	 * Error status relatives to users.
	 */
	public static final int DUPLICATE_REGISTRATION = index++;
	public static final int STUDENT_NOT_FOUND = index++;
	
	private static final Map<Integer, String> mapErrors = generateErrorMapping();


	private final static Map<Integer, String> generateErrorMapping() {
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

		hashMap.put(DUPLICATE_REGISTRATION, "Duplicate entry for registration");
		hashMap.put(STUDENT_NOT_FOUND, "Stundent not found");

		return hashMap;
	}

	public static final NutrIFError getErrorFromIndex(int index) {
		NutrIFError error = new NutrIFError();
		error.setCodigo(index);
		error.setMensagem(mapErrors.get(index));
		return error;
	}
}
