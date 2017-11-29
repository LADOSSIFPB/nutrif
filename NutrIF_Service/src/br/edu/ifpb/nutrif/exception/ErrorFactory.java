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
	public static final int DIA_REFEICAO_DUPLICADO = 19;
	public static final int DIA_REFEICAO_NAO_DEFINIDO = 20;
	public static final int QUANTIDADE_BENEFICIARIOS_EXCEDENTE = 21;
	
	/*
	 * Confirmação da Refeição.
	 */
	public static final int CONFIRMACAO_REFEICAO_INVALIDA = 22;
	
	/*
	 * Pretensão da Refeição
	 */
	public static final int PRETENSAO_REFEICAO_NAO_ENCONTRADA = 23;
	public static final int PRETENSAO_REFEICAO_INVALIDA = 24;
	public static final int CONFIRMACAO_PRETENSAO_INVALIDA = 25;
	public static final int CHAVE_ACESSO_PRETENSAO_INVALIDA = 26;
	
	/*
	 * Realização da Refeição
	 */
	public static final int REFEICAO_REALIZADA_NAO_ENCONTRADA = 27;
	public static final int REFEICAO_JA_REALIZADA = 28;
	
	/*
	 * Funcionário
	 */
	public static final int CODIGO_FUNCIONARIO_INSPETOR_INVALIDO = 29;
	public static final int ID_FUNCIONARIO_INVALIDO = 30;
	public static final int ID_RESPONSAVEL_INVALIDO = 31;
	
	/*
	 * Pessoa
	 */
	public static final int CHAVE_AUTORIZACAO_PESSOA_INVALIDA = 32;
	public static final int ID_PESSOA_INVALIDO = 33;
	
	public static final int IMPOSSIVEL_CRIPTOGRAFAR_VALOR = 34;
	
	/*
	 * Arquivo
	 */
	public static final int TIPO_ARQUIVO_INVALIDO = 35;
	public static final int NOME_ARQUIVO_INVALIDO = 36;
	public static final int TAMANHO_ARQUIVO_INVALIDO = 37;
	public static final int FORMULARIO_ARQUIVO_INVALIDO = 38;
	public static final int ARQUIVO_PERFIL_INVALIDO = 39;
	
	/*
	 * Roles
	 */
	public static final int ROLES_INVALIDAS = 40;
	public static final int NOME_ROLE_INVALIDO = 41;
	public static final int DATA_INVALIDA = 42;
	
	/*
	 * Campus
	 */
	public static final int ID_CAMPUS_INVALIDO = 43;
	
	/*
	 * Edital
	 */
	public static final int ID_EDITAL_INVALIDO = 44;
	public static final int QTD_COMTEMPLADO_INVALIDO = 45;
	public static final int INTERVALO_DATA_INVALIDO = 46;
	
	/*
	 * Evento
	 */
	public static final int ID_EVENTO_INVALIDO = 47;
	public static final int NOME_EVENTO_INVALIDO = 48;
	public static final int DESCRICAO_EVENTO_INVALIDO = 49;
	
	/*
	 * Refeição
	 */
	public static final int PERIODO_REFEICAO_INVALIDO = 50;
	public static final int TIPO_REFEICAO_INVALIDO = 51;
	public static final int DIA_PREVISTO_PRETENSAO_INVALIDO = 52;
	public static final int PERIODO_PREVISAO_PRETENSAO = 53;
	
	/*
	 * Período, turma e turno.
	 */
	public static final int ID_PERIODO_INVALIDO = 54;
	public static final int ID_TURMA_INVALIDO = 55;
	public static final int ID_TURNO_INVALIDO = 56;
	
	public static final int DIA_REFEICAO_NAO_DEFINIDO_EDITAL = 57;
	public static final int DIA_REFEICAO_NAO_DEFINIDO_ALUNO = 58;	
	
	public static final int REFEICAO_NAO_REALIZADA = 59;	
	
	public static final int ERRO_INTERNO_SERVICO = 60;
	
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
		hashMap.put(ARQUIVO_PERFIL_INVALIDO, "Arquivo do perfil não encontrado.");
		hashMap.put(ROLES_INVALIDAS, "Perfis de usuário não informados.");
		hashMap.put(NOME_ROLE_INVALIDO, "Nome do perfil inválido.");
		hashMap.put(DIA_REFEICAO_DUPLICADO, "Dia de refeição duplicado para o Aluno.");
		hashMap.put(DATA_INVALIDA, "Data inválida.");
		hashMap.put(ID_CAMPUS_INVALIDO, "Campus inválido.");
		hashMap.put(QTD_COMTEMPLADO_INVALIDO, "Quantidade de contemplados inválido.");
		hashMap.put(INTERVALO_DATA_INVALIDO, "Intervalo de datas inválido.");
		hashMap.put(ID_EDITAL_INVALIDO, "Edital inválido.");
		hashMap.put(ID_EVENTO_INVALIDO, "Evento inválido.");
		hashMap.put(PERIODO_REFEICAO_INVALIDO, "Período de Refeição inválido.");
		hashMap.put(REFEICAO_JA_REALIZADA, "Refeição já realizada.");
		hashMap.put(DIA_REFEICAO_NAO_DEFINIDO, "Dia de Refeição não definido.");
		hashMap.put(DIA_REFEICAO_NAO_DEFINIDO_ALUNO, "Dia de Refeição não definido para o Aluno.");
		hashMap.put(DIA_REFEICAO_NAO_DEFINIDO_EDITAL, "Dia de Refeição não definido para o Edital.");
		hashMap.put(NOME_EVENTO_INVALIDO, "Nome do evento inválido.");
		hashMap.put(DESCRICAO_EVENTO_INVALIDO, "Descrição do evento inválido.");
		hashMap.put(TIPO_REFEICAO_INVALIDO, "Tipo que descreve a refeição inválido.");	
		hashMap.put(DIA_PREVISTO_PRETENSAO_INVALIDO, "Quantidade de dia(s) previtos para a pretensão da refeição inválido.");
		hashMap.put(PERIODO_PREVISAO_PRETENSAO, "Período de Previsão da Pretensão inválido");
		hashMap.put(ERRO_INTERNO_SERVICO, "Ops! Aconteceu algo estranho no NutrIF. Contacte os administradores.");
		hashMap.put(QUANTIDADE_BENEFICIARIOS_EXCEDENTE, "Inserção do novo dia de refeição excede a quantidade prevista para o Edital.");				
		hashMap.put(ID_PERIODO_INVALIDO, "Período da Turma inválido.");
		hashMap.put(ID_TURMA_INVALIDO, "Turma do Curso inválida.");
		hashMap.put(ID_TURNO_INVALIDO, "Turno da Turma inválido.");
		hashMap.put(REFEICAO_NAO_REALIZADA, "Refeição não realizada.");
		
		
		return hashMap;
	}

	public static final Error getErrorFromIndex(int index) {
		
		Error error = new Error();
		error.setCodigo(index);
		error.setMensagem(mapErrors.get(index));
		
		return error;
	}
}
