package br.edu.ifpb.nutrif.exception;

import java.util.HashMap;
import java.util.Map;

import br.edu.ladoss.entity.Erro;

public class ErrorFactory {

	private ErrorFactory() {}

	/*
	 * Error status: Aluno.
	 */
	public static final int REGISTRO_DUPLICADO = 1;
	public static final int ALUNO_NAO_ENCONTRADO = 2;
	
	/*
	 * Error status: Curso.
	 */
	public static final int NOME_CURSO_INVALIDO = 3;
	
	/*
	 * Error status: Usu�rio.
	 */
	public static final int NOME_USUARIO_INVALIDO = 4;
	public static final int SENHA_USUARIO_INVALIDA = 5;
	
	/*
	 * Mapa de erros: c�digo e mensagem.
	 */
	private static final Map<Integer, String> mapErrors = generateErrorMapping();

	private final static Map<Integer, String> generateErrorMapping() {
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

		hashMap.put(REGISTRO_DUPLICADO, "Registro duplicado.");
		hashMap.put(ALUNO_NAO_ENCONTRADO, "Aluno n�o encontrado.");
		hashMap.put(NOME_CURSO_INVALIDO, "Nome do curso inv�lido.");
		hashMap.put(NOME_USUARIO_INVALIDO, "Nome do usu�rio inv�lido.");
		hashMap.put(SENHA_USUARIO_INVALIDA, "Senha do usu�rio inv�lida.");

		return hashMap;
	}

	public static final Erro getErrorFromIndex(int index) {
		
		Erro error = new Erro();
		error.setCodigo(index);
		error.setMensagem(mapErrors.get(index));
		
		return error;
	}
}
