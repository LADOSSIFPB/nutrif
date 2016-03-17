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
	public static final int NOME_ALUNO_INVALIDO = 3;
	public static final int MATRICULA_ALUNO_INVALIDA = 4;
	public static final int CHAVE_CONFIRMACAO_INVALIDA = 5;
	public static final int NOME_MATRICULA_ALUNO_INVALIDOS = 6;
	
	/*
	 * Error status: Curso.
	 */
	public static final int ID_CURSO_INVALIDO = 7;
	public static final int NOME_CURSO_INVALIDO = 8;
	
	/*
	 * Error status: Usuário.
	 */
	public static final int NOME_USUARIO_INVALIDO = 9;
	public static final int EMAIL_USUARIO_INVALIDO = 10;
	public static final int SENHA_USUARIO_INVALIDA = 11;
	public static final int KEY_CONFIRMATION_INVALIDA = 12;
	
	/*
	 * Error status: Dia da Refeição.
	 */
	public static final int ID_DIA_REFEICAO_INVALIDO = 13;
	public static final int ID_ALUNO_INVALIDO = 14;
	public static final int ID_DIA_INVALIDO = 15;
	public static final int ID_REFEICAO_INVALIDA = 16;
	
	/*
	 * Confirmação da Refeição.
	 */
	public static final int CONFIRMACAO_REFEICAO_INVALIDA = 17;
	
	public static final int IMPOSSIVEL_CRIPTOGRAFAR_VALOR = 18;
	
	
	/*
	 * Mapa de erros: código e mensagem.
	 */
	private static final Map<Integer, String> mapErrors = generateErrorMapping();

	private final static Map<Integer, String> generateErrorMapping() {
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

		hashMap.put(REGISTRO_DUPLICADO, "Registro duplicado.");
		hashMap.put(ALUNO_NAO_ENCONTRADO, "Aluno não encontrado.");		
		hashMap.put(NOME_ALUNO_INVALIDO, "Nome do aluno inválido.");
		hashMap.put(CHAVE_CONFIRMACAO_INVALIDA, "Chave confirmação inválida.");
		hashMap.put(MATRICULA_ALUNO_INVALIDA, "Matrícula do aluno inválida.");		
		hashMap.put(ID_CURSO_INVALIDO, "Curso inválido.");
		hashMap.put(NOME_CURSO_INVALIDO, "Nome do curso inválido.");
		hashMap.put(NOME_USUARIO_INVALIDO, "Nome do usuário inválido.");
		hashMap.put(EMAIL_USUARIO_INVALIDO, "E-mail do usuário inválido.");
		hashMap.put(SENHA_USUARIO_INVALIDA, "Senha do usuário inválida.");
		hashMap.put(ID_ALUNO_INVALIDO, "Aluno inválido.");
		hashMap.put(ID_DIA_INVALIDO, "Dia inválido.");
		hashMap.put(ID_REFEICAO_INVALIDA, "Refeição inválida.");
		hashMap.put(IMPOSSIVEL_CRIPTOGRAFAR_VALOR, "Impossível criptografar valor.");
		hashMap.put(KEY_CONFIRMATION_INVALIDA, "Chave de confirmação inválida.");
		hashMap.put(ID_DIA_REFEICAO_INVALIDO, "Dia da Refeição inválido.");
		hashMap.put(CONFIRMACAO_REFEICAO_INVALIDA, "Confirmação da refeição inválida.");
		hashMap.put(NOME_MATRICULA_ALUNO_INVALIDOS, "Nome e matrícula do aluno inválidos.");
		
		return hashMap;
	}

	public static final Erro getErrorFromIndex(int index) {
		
		Erro error = new Erro();
		error.setCodigo(index);
		error.setMensagem(mapErrors.get(index));
		
		return error;
	}
}
