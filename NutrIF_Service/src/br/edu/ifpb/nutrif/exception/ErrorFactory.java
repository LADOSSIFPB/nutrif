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
	 * Error status: Usu�rio.
	 */
	public static final int NOME_USUARIO_INVALIDO = 11;
	public static final int EMAIL_USUARIO_INVALIDO = 12;
	public static final int SENHA_USUARIO_INVALIDA = 13;
	public static final int KEY_CONFIRMATION_INVALIDA = 14;
	
	/*
	 * Error status: Dia da Refei��o.
	 */
	public static final int ID_DIA_REFEICAO_INVALIDO = 15;
	public static final int ID_ALUNO_INVALIDO = 16;
	public static final int ID_DIA_INVALIDO = 17;
	public static final int ID_REFEICAO_INVALIDA = 18;
	
	/*
	 * Confirma��o da Refei��o.
	 */
	public static final int CONFIRMACAO_REFEICAO_INVALIDA = 19;
	
	/*
	 * Pretens�o da Refei��o
	 */
	public static final int PRETENSAO_REFEICAO_NAO_ENCONTRADA = 20;
	public static final int PRETENSAO_REFEICAO_INVALIDA = 21;
	public static final int CONFIRMACAO_PRETENSAO_INVALIDA = 22;
	public static final int CHAVE_ACESSO_PRETENSAO_INVALIDA = 23;
	
	/*
	 * Realiza��o da Refei��o
	 */
	public static final int REFEICAO_REALIZADA_NAO_ENCONTRADA = 24;
	
	/*
	 * Funcion�rio
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
	 * Mapa de erros: c�digo e mensagem.
	 */
	private static final Map<Integer, String> mapErrors = generateErrorMapping();

	private final static Map<Integer, String> generateErrorMapping() {
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

		hashMap.put(REGISTRO_DUPLICADO, "Registro duplicado.");
		hashMap.put(ALUNO_NAO_ENCONTRADO, "Aluno n�o encontrado.");		
		hashMap.put(NOME_ALUNO_INVALIDO, "Nome do aluno inv�lido.");
		hashMap.put(CHAVE_CONFIRMACAO_INVALIDA, "Chave confirma��o inv�lida.");
		hashMap.put(MATRICULA_ALUNO_INVALIDA, "Matr�cula do aluno inv�lida.");		
		hashMap.put(ID_CURSO_INVALIDO, "Curso inv�lido.");
		hashMap.put(NOME_CURSO_INVALIDO, "Nome do curso inv�lido.");
		hashMap.put(NOME_USUARIO_INVALIDO, "Nome do usu�rio inv�lido.");
		hashMap.put(EMAIL_USUARIO_INVALIDO, "E-mail do usu�rio inv�lido.");
		hashMap.put(SENHA_USUARIO_INVALIDA, "Senha do usu�rio inv�lida.");
		hashMap.put(ID_ALUNO_INVALIDO, "Aluno inv�lido.");
		hashMap.put(ID_DIA_INVALIDO, "Dia inv�lido.");
		hashMap.put(ID_REFEICAO_INVALIDA, "Refei��o inv�lida.");
		hashMap.put(IMPOSSIVEL_CRIPTOGRAFAR_VALOR, "Imposs�vel criptografar valor.");
		hashMap.put(KEY_CONFIRMATION_INVALIDA, "Chave de confirma��o inv�lida.");
		hashMap.put(ID_DIA_REFEICAO_INVALIDO, "Dia da Refei��o inv�lido.");
		hashMap.put(CONFIRMACAO_REFEICAO_INVALIDA, "Confirma��o da refei��o inv�lida.");
		hashMap.put(NOME_MATRICULA_ALUNO_INVALIDOS, "Nome e matr�cula do aluno inv�lidos.");
		hashMap.put(PRETENSAO_REFEICAO_NAO_ENCONTRADA, "Pretens�o da refei��o n�o encontrada.");
		hashMap.put(ACESSO_ALUNO_NAO_PERMITIDO, "Acesso n�o permitido. Dados de login n�o conferem.");
		hashMap.put(REFEICAO_REALIZADA_NAO_ENCONTRADA, "Refei��o realizada n�o encotrada.");
		hashMap.put(CONFIRMACAO_PRETENSAO_INVALIDA, "Confirma��o de pretens�o inv�lida.");
		hashMap.put(CHAVE_ACESSO_PRETENSAO_INVALIDA, "Chave de acesso da pretens�o da refei��o inv�lida.");
		hashMap.put(CODIGO_FUNCIONARIO_INSPETOR_INVALIDO, "C�digo do inspetor inv�lido.");
		hashMap.put(CHAVE_AUTORIZACAO_PESSOA_INVALIDA, "Chave de autoriza��o de acesso para usu�rio inv�lida.");
		hashMap.put(PRETENSAO_REFEICAO_INVALIDA, "Pretens�o de refei��o inv�lida.");
		hashMap.put(ID_FUNCIONARIO_INVALIDO, "Funcion�rio inv�lido.");
		hashMap.put(TIPO_ARQUIVO_INVALIDO, "Tipo do arquivo inv�lido.");		
		hashMap.put(NOME_ARQUIVO_INVALIDO, "Nome arquivo inv�lido.");
		hashMap.put(ID_PESSOA_INVALIDO, "Identificador da pessoa inv�lido.");
		hashMap.put(TAMANHO_ARQUIVO_INVALIDO, "Tamanho do arquivo inv�lido.");
		hashMap.put(FORMULARIO_ARQUIVO_INVALIDO, "Formul�rio com dados da submiss�o do inv�lido.");
		hashMap.put(MATRICULA_ALUNO_DUPLICADA, "Matr�cula do aluno duplicada.");		
		
		return hashMap;
	}

	public static final Error getErrorFromIndex(int index) {
		
		Error error = new Error();
		error.setCodigo(index);
		error.setMensagem(mapErrors.get(index));
		
		return error;
	}
}
