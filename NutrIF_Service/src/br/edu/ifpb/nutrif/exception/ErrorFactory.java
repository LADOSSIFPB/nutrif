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
	public static final int MATRICULA_ALUNO_DUPLICADA = 8;
	
	/*
	 * Error status: Curso.
	 */
	public static final int ID_CURSO_INVALIDO = 9;
	public static final int NOME_CURSO_INVALIDO = 10;
	
	/*
	 * Error status: Usuário.
	 */
	public static final int NOME_USUARIO_INVALIDO = 11;
	public static final int EMAIL_USUARIO_INVALIDO = 12;
	public static final int SENHA_USUARIO_INVALIDA = 13;
	public static final int KEY_CONFIRMATION_INVALIDA = 14;
	
	/*
	 * Error status: Dia da Refeição.
	 */
	public static final int ID_DIA_REFEICAO_INVALIDO = 15;
	public static final int ID_ALUNO_INVALIDO = 16;
	public static final int ID_DIA_INVALIDO = 17;
	public static final int ID_REFEICAO_INVALIDA = 18;
	
	/*
	 * Confirmação da Refeição.
	 */
	public static final int CONFIRMACAO_REFEICAO_INVALIDA = 19;
	
	/*
	 * Pretensão da Refeição
	 */
	public static final int PRETENSAO_REFEICAO_NAO_ENCONTRADA = 20;
	public static final int PRETENSAO_REFEICAO_INVALIDA = 21;
	public static final int CONFIRMACAO_PRETENSAO_INVALIDA = 22;
	public static final int CHAVE_ACESSO_PRETENSAO_INVALIDA = 23;
	
	/*
	 * Realização da Refeição
	 */
	public static final int REFEICAO_REALIZADA_NAO_ENCONTRADA = 24;
	
	/*
	 * Funcionário
	 */
	public static final int CODIGO_FUNCIONARIO_INSPETOR_INVALIDO = 25;
	public static final int ID_FUNCIONARIO_INVALIDO = 26;
	
	/*
	 * Pessoa
	 */
	public static final int CHAVE_AUTORIZACAO_PESSOA_INVALIDA = 27;
	public static final int ID_PESSOA_INVALIDO = 28;
	
	public static final int IMPOSSIVEL_CRIPTOGRAFAR_VALOR = 29;
	
	/*
	 * Arquivo
	 */
	public static final int TIPO_ARQUIVO_INVALIDO = 30;
	public static final int NOME_ARQUIVO_INVALIDO = 31;
	public static final int TAMANHO_ARQUIVO_INVALIDO = 32;
	public static final int FORMULARIO_ARQUIVO_INVALIDO = 33;
	
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
		hashMap.put(CHAVE_ACESSO_PRETENSAO_INVALIDA, "Chave de acesso da pretensão da refeição inválida.");
		hashMap.put(CODIGO_FUNCIONARIO_INSPETOR_INVALIDO, "Código do inspetor inválido.");
		hashMap.put(CHAVE_AUTORIZACAO_PESSOA_INVALIDA, "Chave de autorização de acesso para usuário inválida.");
		hashMap.put(PRETENSAO_REFEICAO_INVALIDA, "Pretensão de refeição inválida.");
		hashMap.put(ID_FUNCIONARIO_INVALIDO, "Funcionário inválido.");
		hashMap.put(TIPO_ARQUIVO_INVALIDO, "Tipo do arquivo inválido.");		
		hashMap.put(NOME_ARQUIVO_INVALIDO, "Nome arquivo inválido.");
		hashMap.put(ID_PESSOA_INVALIDO, "Identificador da pessoa inválido.");
		hashMap.put(TAMANHO_ARQUIVO_INVALIDO, "Tamanho do arquivo inválido.");
		hashMap.put(FORMULARIO_ARQUIVO_INVALIDO, "Formulário com dados da submissão do inválido.");
		hashMap.put(MATRICULA_ALUNO_DUPLICADA, "Matrícula do aluno duplicada.");		
		
		return hashMap;
	}

	public static final Error getErrorFromIndex(int index) {
		
		Error error = new Error();
		error.setCodigo(index);
		error.setMensagem(mapErrors.get(index));
		
		return error;
	}
}
