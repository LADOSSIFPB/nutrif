package br.edu.ifpb.nutrif.exception;

import java.util.HashMap;
import java.util.Map;

import br.edu.ladoss.entity.Error;

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
	public static final int ACESSO_ALUNO_NAO_PERMITIDO = 7;
	
	/*
	 * Error status: Curso.
	 */
	public static final int ID_CURSO_INVALIDO = 8;
	public static final int NOME_CURSO_INVALIDO = 9;
	
	/*
	 * Error status: Usuário.
	 */
	public static final int NOME_USUARIO_INVALIDO = 10;
	public static final int EMAIL_USUARIO_INVALIDO = 11;
	public static final int SENHA_USUARIO_INVALIDA = 12;
	public static final int KEY_CONFIRMATION_INVALIDA = 13;
	
	/*
	 * Error status: Dia da Refeição.
	 */
	public static final int ID_DIA_REFEICAO_INVALIDO = 14;
	public static final int ID_ALUNO_INVALIDO = 15;
	public static final int ID_DIA_INVALIDO = 16;
	public static final int ID_REFEICAO_INVALIDA = 17;
	
	/*
	 * Confirmação da Refeição.
	 */
	public static final int CONFIRMACAO_REFEICAO_INVALIDA = 18;
	
	/*
	 * Pretensão da Refeição
	 */
	public static final int PRETENSAO_REFEICAO_NAO_ENCONTRADA = 19;
	public static final int CONFIRMACAO_PRETENSAO_INVALIDA = 20;
	public static final int CHAVE_ACESSO_INVALIDA = 21;
	
	/*
	 * Realização da Refeição
	 */
	public static final int REFEICAO_REALIZADA_NAO_ENCONTRADA = 22;
	
	/*
	 * Funcionário
	 */
	public static final int CODIGO_FUNCIONARIO_INSPETOR_INVALIDO = 23;
	
	public static final int IMPOSSIVEL_CRIPTOGRAFAR_VALOR = 24;
	
	
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
		hashMap.put(PRETENSAO_REFEICAO_NAO_ENCONTRADA, "Pretensão da refeição não encontrada.");
		hashMap.put(ACESSO_ALUNO_NAO_PERMITIDO, "Acesso não permitido. Dados de login não conferem.");
		hashMap.put(REFEICAO_REALIZADA_NAO_ENCONTRADA, "Refeição realizada não encotrada.");
		hashMap.put(CONFIRMACAO_PRETENSAO_INVALIDA, "Confirmação de pretensão inválida.");
		hashMap.put(CHAVE_ACESSO_INVALIDA, "Chave de acesso da pretensão da refeição inválida.");
		hashMap.put(CODIGO_FUNCIONARIO_INSPETOR_INVALIDO, "Código do inspetor inválido.");
		
		
		return hashMap;
	}

	public static final Error getErrorFromIndex(int index) {
		
		Error error = new Error();
		error.setCodigo(index);
		error.setMensagem(mapErrors.get(index));
		
		return error;
	}
}
