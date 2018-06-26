package br.edu.ifpb.nutrif.validation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.AlunoDAO;
import br.edu.ifpb.nutrif.dao.CursoDAO;
import br.edu.ifpb.nutrif.dao.PeriodoDAO;
import br.edu.ifpb.nutrif.dao.SituacaoMatriculaDAO;
import br.edu.ifpb.nutrif.dao.TurmaDAO;
import br.edu.ifpb.nutrif.dao.TurnoDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.util.StringUtil;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Campus;
import br.edu.ladoss.entity.ConfirmaPretensaoDia;
import br.edu.ladoss.entity.ConfirmaRefeicaoDia;
import br.edu.ladoss.entity.Curso;
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Edital;
import br.edu.ladoss.entity.Evento;
import br.edu.ladoss.entity.Funcionario;
import br.edu.ladoss.entity.Matricula;
import br.edu.ladoss.entity.Periodo;
import br.edu.ladoss.entity.PeriodoPretensaoRefeicao;
import br.edu.ladoss.entity.PeriodoRefeicaoRealizada;
import br.edu.ladoss.entity.PessoaAcesso;
import br.edu.ladoss.entity.PretensaoRefeicao;
import br.edu.ladoss.entity.Refeicao;
import br.edu.ladoss.entity.RefeicaoRealizada;
import br.edu.ladoss.entity.Role;
import br.edu.ladoss.entity.Setor;
import br.edu.ladoss.entity.SituacaoMatricula;
import br.edu.ladoss.entity.Turma;
import br.edu.ladoss.entity.Turno;
import br.edu.ladoss.enumeration.TipoArquivo;
import br.edu.ladoss.form.FileUploadForm;

public class Validate {

	private static Logger logger = LogManager.getLogger(Validate.class);
	
	private static StringValidator stringValidator = new StringValidator();
	private static NumeroValidator numeroValidator = new NumeroValidator();
	private static EmailValidator emailValidator = new EmailValidator();
	private static ImageValidator imageValidator = new ImageValidator();
	private static DataValidator dataValidator = new DataValidator();
	private static CpfValidator cpfValidator = new CpfValidator();
	private static Time24HoursValidator time24HoursValidator = new Time24HoursValidator();

	public static int VALIDATE_OK = 0;
	
	public static int MINIMO_DIGITOS_MATRICULA = 11;
	
	public static int MAXIMO_DIGITOS_MATRICULA = 13;
	
	public static int inserirAluno(Aluno aluno) {
		
		logger.info("Validação para inserção do Aluno.");
		
		// Nome
		if (!stringValidator.validateSomenteLetras(aluno.getNome()))
			return ErrorFactory.NOME_ALUNO_INVALIDO;
		
		// Cpf
		if (!cpfValidator.validate(aluno.getCpf()))
			return ErrorFactory.CPF_INVALIDO;
		
		// E-mail
		if (!emailValidator.validate(aluno.getEmail()))
			return ErrorFactory.EMAIL_USUARIO_INVALIDO;
		
		Campus campus = aluno.getCampus();
		if (campus == null 
				|| (campus != null && !numeroValidator.isMaiorZero(campus.getId()))) {
			return ErrorFactory.ID_CAMPUS_INVALIDO;
		}	
		
		return VALIDATE_OK;
	}
	
	public static int atualizarAluno(Aluno aluno) {
		
		logger.info("Validação para atualização do Aluno.");
		
		// Nome
		if (!stringValidator.validateSomenteLetras(aluno.getNome()))
			return ErrorFactory.NOME_ALUNO_INVALIDO;
		
		// Cpf
		if (!cpfValidator.validate(aluno.getCpf()))
			return ErrorFactory.CPF_INVALIDO;
		
		// E-mail
		String email = aluno.getEmail();
		if (!StringUtil.isEmptyOrNull(email)
				&& !emailValidator.validate(email))
			return ErrorFactory.EMAIL_USUARIO_INVALIDO;
		
		Campus campus = aluno.getCampus();
		if (campus == null 
				|| (campus != null && !numeroValidator.isMaiorZero(campus.getId()))) {
			return ErrorFactory.ID_CAMPUS_INVALIDO;
		}	
		
		return VALIDATE_OK;
	}
	
	public static int inserirAcessoAluno(Aluno aluno) {
		
		logger.info("Validação para inserção do acesso de Aluno.");
		
		// Nome
		if (!stringValidator.validateSomenteLetras(aluno.getNome()))
			return ErrorFactory.NOME_ALUNO_INVALIDO;
		
		// Cpf
		if (!cpfValidator.validate(aluno.getCpf()))
			return ErrorFactory.CPF_INVALIDO;
		
		// E-mail
		if (!emailValidator.validate(aluno.getEmail()))
			return ErrorFactory.EMAIL_USUARIO_INVALIDO;
		
		Campus campus = aluno.getCampus();
		if (campus==null 
				|| (campus != null && !numeroValidator.isMaiorZero(campus.getId()))) {
			return ErrorFactory.ID_CAMPUS_INVALIDO;
		}
		
		if (!stringValidator.validate(aluno.getSenha(), 5, 40))
			return ErrorFactory.SENHA_USUARIO_INVALIDA;
		
		return VALIDATE_OK;
	}
	
	public static int acessoServicoKeyAuth(String keyAuth) {
		
		logger.info("Validação para Chave de Autenticação.");
		
		if (!stringValidator.validate(keyAuth, 64))
			return ErrorFactory.CHAVE_AUTORIZACAO_PESSOA_INVALIDA;
		
		return VALIDATE_OK;
	}
	
	public static int confirmacaoChaveAluno(Aluno aluno) {
		
		logger.info("Validação para confirmação de aluno.");
		
		//TODO: Modificar para cpf.
		/*
		String matricula = aluno.getMatricula();
		
		if (matricula(matricula) != VALIDATE_OK) {
			
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
		}
		*/
		if (!stringValidator.validate(aluno.getKeyConfirmation(), 5))
			return ErrorFactory.KEY_CONFIRMATION_INVALIDA;
		
		return VALIDATE_OK;
	}

	public static int curso(Curso curso) {		
		
		logger.info("Validação para Curso.");
		
		String nome = curso.getNome();
		
		if (!stringValidator.validateSomenteLetras(nome))
			return ErrorFactory.NOME_CURSO_INVALIDO;		
		
		return VALIDATE_OK;
	}
	
	public static int campus(Campus campus) {		
		
		logger.info("Validação para Campus.");
		
		String cidade = campus.getCidade();
		
		if (!stringValidator.validateSomenteLetras(cidade))
			return ErrorFactory.CIDADE_INVALIDA;
		
		String sigla = campus.getSigla();
		
		if (!stringValidator.validateSomenteLetras(sigla))
			return ErrorFactory.SIGLA_INVALIDA;
		
		return VALIDATE_OK;
	}
	
	public static int role(Role role) {		
		
		logger.info("Validação para Role.");
		
		String nome = role.getNome();
		
		if (!stringValidator.validateSomenteLetras(nome))
			return ErrorFactory.NOME_ROLE_INVALIDO;		
		
		return VALIDATE_OK;
	}
	
	public static int refeicao(Refeicao refeicao) {
		
		logger.info("Validação para Refeição.");
		
		String tipo = refeicao.getTipo();
		if (!stringValidator.validateSomenteLetras(tipo))
			return ErrorFactory.TIPO_REFEICAO_INVALIDO;		
		
		Campus campus = refeicao.getCampus();
		if (campus == null 
				|| (campus != null
				&& !numeroValidator.isInteiroPositivo(campus.getId()))) {
			
			return ErrorFactory.ID_CAMPUS_INVALIDO;
		}
		
		Date horaInicio = refeicao.getHoraInicio();		
		Date horaFinal = refeicao.getHoraFinal();		
		if (!dataValidator.isGrowingDate(horaInicio, horaFinal)) {
			
			return ErrorFactory.PERIODO_REFEICAO_INVALIDO;
		}
		
		Date horaPrevisaoPretensao = refeicao.getHoraPrevisaoPretensao();
		if (horaPrevisaoPretensao != null 
				&& !time24HoursValidator.validate(horaPrevisaoPretensao)) {
			return ErrorFactory.PERIODO_PREVISAO_PRETENSAO;
		}
		
		BigDecimal custo = refeicao.getCusto();
		if (custo != null) {
			// TODO: Implementar validação para bigdecimal.
		}
		
		return VALIDATE_OK;
	}
	
	public static int inserirEdital(Edital edital) {
		
		logger.info("Validação para Edital.");
		
		// Evento
		Evento evento = edital.getEvento();
		if (evento == null 
				|| (evento != null
				&& !numeroValidator.isInteiroPositivo(evento.getId()))) {
			
			return ErrorFactory.ID_EVENTO_INVALIDO;
		}
		
		// Responsável
		Funcionario responsavel = edital.getResponsavel();
		if (responsavel == null 
				|| (responsavel != null
				&& !numeroValidator.isInteiroPositivo(responsavel.getId()))) {
			
			return ErrorFactory.ID_RESPONSAVEL_INVALIDO;
		}
		
		Campus campus = edital.getCampus();
		if (campus == null 
				|| (campus != null
				&& !numeroValidator.isInteiroPositivo(campus.getId()))) {
			
			return ErrorFactory.ID_CAMPUS_INVALIDO;
		}
		
		int quantidadeContemplado = edital.getQuantidadeBeneficiadosPrevista();
		if (!numeroValidator.isMaiorZero(quantidadeContemplado)) {
			
			return ErrorFactory.QTD_COMTEMPLADO_INVALIDO;
		}
		
		Date dataInicial = edital.getDataInicial();
		Date dataFinal = edital.getDataFinal();
		if (!dataValidator.isGrowingDate(dataInicial, dataFinal)) {
			
			return ErrorFactory.INTERVALO_DATA_INVALIDO;
		}
		
		Funcionario funcionario = edital.getFuncionario();
		if (funcionario == null || 
				(funcionario != null 
					&& !numeroValidator.isInteiroPositivo(funcionario.getId()))) {
			return ErrorFactory.ID_FUNCIONARIO_INVALIDO;
		}
				
		return VALIDATE_OK;
	}
	
	
	public static int atualizarEdital(Edital edital) {
		
		logger.info("Validação para atualizar o Edital.");
		
		int idEdital = edital.getId();
		
		if (!numeroValidator.isMaiorZero(idEdital)) {
			return ErrorFactory.ID_EDITAL_INVALIDO;
		}		
		
		// Evento				
		return inserirEdital(edital);
	}
	
	public static int removerEdital(int idEdital) {

		if (!numeroValidator.isInteiroPositivo(idEdital)) {
			
			return ErrorFactory.ID_EDITAL_INVALIDO;
		}
		
		return VALIDATE_OK;
	}
	
	public static int listarContempladosEdital(int idEdital) {

		if (!numeroValidator.isInteiroPositivo(idEdital)) {
			
			return ErrorFactory.ID_EDITAL_INVALIDO;
		}
		
		return VALIDATE_OK;
	}
	
	public static int listarAlunoEdital(Integer idEdital, String nome) {
		
		if (!numeroValidator.isInteiroPositivo(idEdital)) {
			
			return ErrorFactory.ID_EDITAL_INVALIDO;
		}
		
		if (!stringValidator.validateSomenteLetras(nome))
			return ErrorFactory.NOME_USUARIO_INVALIDO;
		
		return VALIDATE_OK;
	}
	
	public static int pretensaoRefeicao(PretensaoRefeicao pretensaoRefeicao) {
		
		logger.info("Validação para Pretensão Refeição.");
		
		ConfirmaPretensaoDia confirmaPretensaoDia = pretensaoRefeicao
				.getConfirmaPretensaoDia();
		
		if (confirmaPretensaoDia != null) {
			
			DiaRefeicao diaRefeicao = confirmaPretensaoDia.getDiaRefeicao();
			
			if (diaRefeicao == null 
					|| (diaRefeicao != null 
						&& !numeroValidator.isMaiorZero(diaRefeicao.getId()))) {
				
				return ErrorFactory.ID_DIA_REFEICAO_INVALIDO;
			}
			
		} else {
			
			return ErrorFactory.CONFIRMACAO_REFEICAO_INVALIDA;
		}
		
		return VALIDATE_OK;
	}
	
	public static int pretensaoRefeicaoKeyAccess(
			PretensaoRefeicao pretensaoRefeicao) {
		
		logger.info("Validação da chave de acesso para Pretensão da Refeicao.");
 
		if (pretensaoRefeicao == null 
				|| !(pretensaoRefeicao != null && stringValidator.validate(
						pretensaoRefeicao.getChaveAcesso(), 64)))
			return ErrorFactory.CHAVE_ACESSO_PRETENSAO_INVALIDA;
		
		return VALIDATE_OK;
	}
	
	public static int dia(Dia dia) {
		
		logger.info("Validação para Dia.");
		
		if (dia == null || 
				(dia != null 
					&& !numeroValidator.isInteiroPositivo(dia.getId()))) {
			
			return ErrorFactory.ID_DIA_INVALIDO;
		}
		
		return VALIDATE_OK;
	}

	public static int diaRefeicao(DiaRefeicao diaRefeicao) {
		
		logger.info("Validação para Dia da Refeição.");
		
		Matricula matricula = diaRefeicao.getMatricula();
		if (matricula == null || 
				(matricula != null 
					&& !numeroValidator.isMaiorZero(matricula.getId()))) {
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
		}
		
		Edital edital = diaRefeicao.getEdital();
		if (edital == null || 
				(edital != null 
					&& !numeroValidator.isMaiorZero(edital.getId()))) {
			
			return ErrorFactory.ID_EDITAL_INVALIDO;
		}
		
		Dia dia = diaRefeicao.getDia();
		if (dia == null || 
				(dia != null 
					&& !numeroValidator.isInteiroPositivo(dia.getId()))) {
			
			return ErrorFactory.ID_DIA_INVALIDO;
		}
		
		Refeicao refeicao = diaRefeicao.getRefeicao();
		if (refeicao == null || 
				(refeicao != null 
					&& !numeroValidator.isInteiroPositivo(refeicao.getId()))) {
			
			return ErrorFactory.ID_REFEICAO_INVALIDA;
		}		
		
		Funcionario funcionario = diaRefeicao.getFuncionario();
		if (funcionario == null || 
				(funcionario != null 
					&& !numeroValidator.isInteiroPositivo(funcionario.getId()))) {
			
			return ErrorFactory.ID_FUNCIONARIO_INVALIDO;
		}
		
		return VALIDATE_OK;
	}
	
	public static int diaRefeicao(int idDiaRefeicao) {
		
		if (!numeroValidator.isMaiorZero(idDiaRefeicao)) {
			
			return ErrorFactory.ID_DIA_REFEICAO_INVALIDO;
		}
		
		return VALIDATE_OK;
	}
	
	public static int quantidadeDiaRefeicao(DiaRefeicao diaRefeicao) {
		
		logger.info("Validação para quantificar o Dia da Refeição.");
		
		Dia dia = diaRefeicao.getDia();
		if (dia == null || 
				(dia != null 
					&& !numeroValidator.isInteiroPositivo(dia.getId()))) {
			
			return ErrorFactory.ID_DIA_INVALIDO;
		}
		
		Refeicao refeicao = diaRefeicao.getRefeicao();
		if (refeicao == null || 
				(refeicao != null 
					&& !numeroValidator.isInteiroPositivo(refeicao.getId()))) {
			
			return ErrorFactory.ID_REFEICAO_INVALIDA;
		}
		
		return VALIDATE_OK;
	}
	
	public static int refeicaoRealizada(RefeicaoRealizada refeicaoRealizada) {
		
		logger.info("Validação para Refeição Realizada.");
		
		ConfirmaRefeicaoDia confirmaRefeicaoDia = refeicaoRealizada
				.getConfirmaRefeicaoDia();
		
		if (confirmaRefeicaoDia != null) {
			
			DiaRefeicao diaRefeicao = confirmaRefeicaoDia.getDiaRefeicao();
			
			if (diaRefeicao == null 
					|| (diaRefeicao != null 
						&& !numeroValidator.isMaiorZero(diaRefeicao.getId()))) {
				
				return ErrorFactory.ID_DIA_REFEICAO_INVALIDO;
			}
			
		} else {
			
			return ErrorFactory.CONFIRMACAO_REFEICAO_INVALIDA;
		}
		
		Funcionario inspetor = refeicaoRealizada.getInspetor();
		
		if (inspetor == null 
				|| (inspetor != null 
					&& !numeroValidator.isMaiorZero(inspetor.getId()))) {
			
			return ErrorFactory.CODIGO_FUNCIONARIO_INSPETOR_INVALIDO;
		}		
		
		return VALIDATE_OK;
	}
	
	public static int inserirFuncionario(PessoaAcesso usuario) {
		
		logger.info("Validação para Funcionário.");
		
		String nome = usuario.getNome();
		if (!stringValidator.validateSomenteLetras(nome))
			return ErrorFactory.NOME_USUARIO_INVALIDO;
		
		
		if (!stringValidator.validate(usuario.getSenha(), 5, 40))
			return ErrorFactory.SENHA_USUARIO_INVALIDA;
		
		List<Role> roles = usuario.getRoles();
		if (roles == null || (roles != null && roles.size() == 0)) {
			
			return ErrorFactory.ROLES_INVALIDAS;			
		}
		
		Campus campus = usuario.getCampus();
		if (campus == null 
				|| (campus != null && !numeroValidator.isMaiorZero(
						campus.getId()))) {
			return ErrorFactory.ID_CAMPUS_INVALIDO;
		}
		
		return VALIDATE_OK;
	}
	
	public static int atualizaFuncionario(Funcionario funcionario) {
		
		logger.info("Validação para Funcionário.");
		
		String nome = funcionario.getNome();
		if (!stringValidator.validateSomenteLetras(nome))
			return ErrorFactory.NOME_USUARIO_INVALIDO;
		
		if (!emailValidator.validate(funcionario.getEmail()))
			return ErrorFactory.EMAIL_USUARIO_INVALIDO;
		
		List<Role> roles = funcionario.getRoles();
		if (roles == null || (roles != null && roles.size() == 0)) {
			
			return ErrorFactory.ROLES_INVALIDAS;			
		}
		
		return VALIDATE_OK;
	}
	
	public static int login(PessoaAcesso pessoaAcesso) {
		
		logger.info("Validação para login.");
		
		String matricula = pessoaAcesso.getMatricula();
		String email = pessoaAcesso.getEmail();
		
		if (!emailValidator.validate(email)) {
			if (matricula(matricula) != VALIDATE_OK) {
				return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
			} else {
				return ErrorFactory.EMAIL_USUARIO_INVALIDO;
			}
		}

		if (!stringValidator.validate(pessoaAcesso.getSenha(), 5, 40))
			return ErrorFactory.SENHA_USUARIO_INVALIDA;
		
		return VALIDATE_OK;
	}
	
	public static int logoutPessoa(String authorization) {
		
		logger.info("Validação para logout de Funcionário.");
		
		if (!stringValidator.validate(authorization)) {
			return ErrorFactory.AUTORIZATION_INVALIDO;
		}
		
		return VALIDATE_OK;
	}
	
	public static int downloadArquivo(TipoArquivo tipoArquivo, 
			String nomeRealArquivo) {
		
		logger.info("Validação para Download de Arquivo.");
	
		if (tipoArquivo == null) 
			return ErrorFactory.TIPO_ARQUIVO_INVALIDO;
		
		if (!imageValidator.validate(nomeRealArquivo))
			return ErrorFactory.NOME_ARQUIVO_INVALIDO;
		
		return VALIDATE_OK;
	}
	
	public static int downloadImagemPerfil(String matricula) {
		
		logger.info("Validação para Download da Imagem do Perfil.");
		
		if (matricula(matricula) != VALIDATE_OK) {
			
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
		}
		
		return VALIDATE_OK;
	}

	public static int uploadArquivo(TipoArquivo tipoArquivo, 
			FileUploadForm form) {
		
		logger.info("Validação para Upload de Arquivo.");
		
		if (tipoArquivo == null) 
			return ErrorFactory.TIPO_ARQUIVO_INVALIDO;
		
		if (form != null) {
			
			String fileName = form.getFileName();
			if (!imageValidator.validate(fileName)) {
				return ErrorFactory.NOME_ARQUIVO_INVALIDO;
			}
			
			int idPessoa = form.getIdPessoa();
			if (!numeroValidator.isInteiroPositivo(idPessoa)) {
				return ErrorFactory.ID_PESSOA_INVALIDO;
			}
			
			byte[] arquivo = form.getData();
			if (arquivo == null || (arquivo != null && arquivo.length <= 0)) {
				return ErrorFactory.TAMANHO_ARQUIVO_INVALIDO;
			}
		
		} else {
			return ErrorFactory.FORMULARIO_ARQUIVO_INVALIDO;
		}
		
		return VALIDATE_OK;
	}

	public static int quantidadeRefeicaoRealizada(DiaRefeicao diaRefeicao) {
		
		logger.info("Validação para obter a quandidade de Refeições Realizadas.");
		
		Dia dia = diaRefeicao.getDia();
		if (dia == null || 
				(dia != null 
					&& !numeroValidator.isInteiroPositivo(dia.getId()))) {
			return ErrorFactory.ID_DIA_INVALIDO;
		}
		
		Refeicao refeicao = diaRefeicao.getRefeicao();
		if (refeicao == null || 
				(refeicao != null 
					&& !numeroValidator.isInteiroPositivo(refeicao.getId()))) {
			return ErrorFactory.ID_REFEICAO_INVALIDA;
		}
		
		return VALIDATE_OK;
	}

	public static int quantidadePretensaoRefeicao(DiaRefeicao diaRefeicao) {
		
		logger.info("Validação para obter a quandidade de Pretensões de Refeição.");
		
		Dia dia = diaRefeicao.getDia();
		if (dia == null || 
				(dia != null 
					&& !numeroValidator.isInteiroPositivo(dia.getId()))) {
			return ErrorFactory.ID_DIA_INVALIDO;
		}
		
		Refeicao refeicao = diaRefeicao.getRefeicao();
		if (refeicao == null || 
				(refeicao != null 
					&& !numeroValidator.isInteiroPositivo(refeicao.getId()))) {
			return ErrorFactory.ID_REFEICAO_INVALIDA;
		}
		
		return VALIDATE_OK;
	}

	public static int periodoRefeicaoRealizada(PeriodoRefeicaoRealizada mapaRefeicoesRealizadas) {
		
		Refeicao refeicao = mapaRefeicoesRealizadas.getRefeicao();
		if (refeicao == null || 
				(refeicao != null 
					&& !numeroValidator.isInteiroPositivo(refeicao.getId()))) {
			return ErrorFactory.ID_REFEICAO_INVALIDA;
		}
		
		Date dataInicial = mapaRefeicoesRealizadas.getDataInicio();
		if (dataValidator.validateFormat(dataInicial, 
				DataValidator.FORMATO_DATA)) {
			
			return ErrorFactory.DATA_INVALIDA;
		}
		
		Date dataFinal = mapaRefeicoesRealizadas.getDataFim();
		if (dataValidator.validateFormat(dataFinal, 
				DataValidator.FORMATO_DATA)) {
			
			return ErrorFactory.DATA_INVALIDA;
		}
		
		return VALIDATE_OK;
	}
	
	public static int listarRefeicaoRealizadaByDiaRefeicao(Integer idDia, Integer idRefeicao) {

		logger.info("Validação para listar Refeições Realizadas para um Dia e Refeição.");
		
		if (!numeroValidator.isMaiorZero(idDia)) {
			return ErrorFactory.ID_DIA_INVALIDO;
		}
		
		if (!numeroValidator.isMaiorZero(idRefeicao)) {
			return ErrorFactory.ID_REFEICAO_INVALIDA;
		}
		
		return VALIDATE_OK;
	}
	
	public static int listarRefeicaoRealizadaByEditalAluno(Integer idEdital, String matricula) {

		logger.info("Validação para listar Refeições Realizadas do Aluno para um Edital.");
		
		if (!numeroValidator.isMaiorZero(idEdital)) {
			return ErrorFactory.ID_EDITAL_INVALIDO;
		}
		
		if (matricula(matricula) != VALIDATE_OK) {
			
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
		}
		
		return VALIDATE_OK;
	}

	public static int inserirMatricula(Matricula matricula) {
		
		String numero = matricula.getNumero();
		
		if (matricula(numero) != VALIDATE_OK) {			
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
		}
		
		// Aluno
		Aluno aluno = matricula.getAluno();
		if (aluno == null) {
			return ErrorFactory.ID_ALUNO_INVALIDO;
		}
		
		// Curso
		Curso curso = matricula.getCurso();
		if (curso == null) {
			return ErrorFactory.ID_CURSO_INVALIDO;
		}
		
		// Turno
		Turno turno = matricula.getTurno();
		if (turno == null) {
			return ErrorFactory.ID_TURNO_INVALIDO;
		}
		
		// Periodo
		Periodo periodo = matricula.getPeriodo();
		if (periodo == null) {
			return ErrorFactory.ID_PERIODO_INVALIDO;
		}
		
		// Turma
		Turma turma = matricula.getTurma();
		if (turma == null) {
			return ErrorFactory.ID_TURMA_INVALIDO;
		}	
		
		return VALIDATE_OK;
	}
	
	public static int atualizarMatricula(Matricula matricula) {
		
		int codigo = inserirMatricula(matricula);
		
		if (codigo != VALIDATE_OK) {
			return codigo;
		}
		
		SituacaoMatricula situacao = matricula.getSituacao();
		if (situacao == null) {
			return ErrorFactory.ID_SITUACAO_MATRICULA_INVALIDO;
		}
		
		return VALIDATE_OK;
	}
	
	public static int removerMatricula(Integer idMatricula) {
		
		if (!numeroValidator.isInteiroPositivo(idMatricula)) {
			return ErrorFactory.ID_MATRICULA_INVALIDO;
		}
		
		return VALIDATE_OK;
	}
	
	public static int matricula(String matricula) {
		
		if (!numeroValidator.validate(matricula)
				&& !numeroValidator.validate(matricula, 
				MINIMO_DIGITOS_MATRICULA, MAXIMO_DIGITOS_MATRICULA)) {
			
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
		}
		
		return VALIDATE_OK;
	}
	
	public static int nomeAluno(String nome) {
		
		if (!stringValidator.validate(nome, 3, 255)) {
			
			return ErrorFactory.NOME_ALUNO_INVALIDO;			
		}
		
		return VALIDATE_OK;
	}
	
	public static int evento(Evento evento) {
		
		String nome = evento.getNome();
		if (!stringValidator.validate(nome)) {
			
			return ErrorFactory.NOME_EVENTO_INVALIDO;			
		}
		
		String descricao = evento.getNome();
		if (!stringValidator.validate(descricao)) {
			
			return ErrorFactory.DESCRICAO_EVENTO_INVALIDO;			
		}
		
		return VALIDATE_OK;
	}

	public static int setor(Setor setor) {
		return VALIDATE_OK;
	}
	
	public static int turma(Turma turma) {
		return VALIDATE_OK;
	}

	public static int periodoPretensaoRefeicao(PeriodoPretensaoRefeicao periodoPretensaoRefeicao) {
		
		Refeicao refeicao = periodoPretensaoRefeicao.getRefeicao();
		if (refeicao == null || 
				(refeicao != null 
					&& !numeroValidator.isInteiroPositivo(refeicao.getId()))) {
			return ErrorFactory.ID_REFEICAO_INVALIDA;
		}
		
		Date inicio = periodoPretensaoRefeicao.getDataInicio();
		
		Date fim = periodoPretensaoRefeicao.getDataFim();
		
		if (!dataValidator.isGrowingDate(inicio, fim)) {
			
			return ErrorFactory.PERIODO_REFEICAO_INVALIDO;
		}
		
		return VALIDATE_OK;
	}

	public static int verificarAcessoAluno(String matricula) {
		
		return matricula(matricula);
	}
}
