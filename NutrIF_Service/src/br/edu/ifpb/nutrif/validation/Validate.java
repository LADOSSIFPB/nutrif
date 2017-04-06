package br.edu.ifpb.nutrif.validation;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.ErrorFactory;
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
import br.edu.ladoss.entity.Periodo;
import br.edu.ladoss.entity.PeriodoPretensaoRefeicao;
import br.edu.ladoss.entity.PeriodoRefeicaoRealizada;
import br.edu.ladoss.entity.PessoaAcesso;
import br.edu.ladoss.entity.PretensaoRefeicao;
import br.edu.ladoss.entity.Refeicao;
import br.edu.ladoss.entity.RefeicaoRealizada;
import br.edu.ladoss.entity.Role;
import br.edu.ladoss.entity.Setor;
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
	private static Time24HoursValidator time24HoursValidator = new Time24HoursValidator();

	public static int VALIDATE_OK = 0;
	
	public static int ANO_MATRICULA_12_DIGITOS = 2016;
	
	public static int MINIMO_DIGITOS_MATRICULA = 11;
	
	public static int MAXIMO_DIGITOS_MATRICULA = 13;
	
	public static int inserirAluno(Aluno aluno) {
		
		logger.info("Validação para Aluno.");
		
		if (!stringValidator.validateSomenteLetras(aluno.getNome()))
			return ErrorFactory.NOME_ALUNO_INVALIDO;
			
		if (!numeroValidator.validate(aluno.getMatricula()) 
				|| !stringValidator.validate(aluno.getMatricula(), 
						MINIMO_DIGITOS_MATRICULA, 
						MAXIMO_DIGITOS_MATRICULA))
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
			
		Curso curso = aluno.getCurso();
		if (curso == null 
				|| (curso != null 
					&& !numeroValidator.isMaiorZero(curso.getId())))
			return ErrorFactory.ID_CURSO_INVALIDO;
		
		Campus campus = aluno.getCampus();
		if (campus==null 
				|| (campus != null && !numeroValidator.isMaiorZero(campus.getId()))) {
			return ErrorFactory.ID_CAMPUS_INVALIDO;
		}
		
		Periodo periodo = aluno.getPeriodo();
		if (periodo == null 
				|| (periodo != null
				&& !numeroValidator.isInteiroPositivo(periodo.getId()))) {
			
			return ErrorFactory.ID_PERIODO_INVALIDO;
		}
		
		Turma turma = aluno.getTurma();
		if (turma == null 
				|| (turma != null
				&& !numeroValidator.isInteiroPositivo(turma.getId()))) {
			
			return ErrorFactory.ID_TURMA_INVALIDO;
		}
		
		Turno turno = aluno.getTurno();
		if (turno == null 
				|| (turno != null
				&& !numeroValidator.isInteiroPositivo(turno.getId()))) {
			
			return ErrorFactory.ID_TURNO_INVALIDO;
		}
		
		return VALIDATE_OK;
	}
	
	public static int inserirAcessoAluno(Aluno aluno) {
		
		logger.info("Validação para inserção do acesso de Aluno.");
		
		if (!numeroValidator.validate(aluno.getMatricula()) 
				|| !stringValidator.validate(aluno.getMatricula(), 
						MINIMO_DIGITOS_MATRICULA, 
						MAXIMO_DIGITOS_MATRICULA))
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
		
		if (!emailValidator.validate(aluno.getEmail()))
			return ErrorFactory.EMAIL_USUARIO_INVALIDO;

		Periodo periodo = aluno.getPeriodo();
		if (periodo == null 
				|| (periodo != null
				&& !numeroValidator.isInteiroPositivo(periodo.getId()))) {
			
			return ErrorFactory.ID_PERIODO_INVALIDO;
		}
		
		Turma turma = aluno.getTurma();
		if (turma == null 
				|| (turma != null
				&& !numeroValidator.isInteiroPositivo(turma.getId()))) {
			
			return ErrorFactory.ID_TURMA_INVALIDO;
		}
		
		Turno turno = aluno.getTurno();
		if (turno == null 
				|| (turno != null
				&& !numeroValidator.isInteiroPositivo(turno.getId()))) {
			
			return ErrorFactory.ID_TURNO_INVALIDO;
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
		
		if (!stringValidator.validate(aluno.getMatricula(), 
				MINIMO_DIGITOS_MATRICULA, 
				MAXIMO_DIGITOS_MATRICULA))
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
		
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
		
		Date horaInicio = refeicao.getHoraInicio();		
		Date horaFinal = refeicao.getHoraFinal();		
		if (!dataValidator.isGrowingDate(horaInicio, horaFinal)) {
			
			return ErrorFactory.PERIODO_REFEICAO_INVALIDO;
		}
		
		Date horaPrevisaoPretensao = refeicao.getHoraPrevisaoPretensao();
		if (!time24HoursValidator.validate(horaPrevisaoPretensao)) {
			return ErrorFactory.PERIODO_PREVISAO_PRETENSAO;
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
	
	public static int diaRefeicaoEdital(int idEdital) {

		if (!numeroValidator.isInteiroPositivo(idEdital)) {
			
			return ErrorFactory.ID_EDITAL_INVALIDO;
		}
		
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
						pretensaoRefeicao.getKeyAccess(), 64)))
			return ErrorFactory.CHAVE_ACESSO_PRETENSAO_INVALIDA;
		
		return VALIDATE_OK;
	}
	
	public static int dia(Dia aluno) {
		
		logger.info("Validação para Dia.");
		//TODO: implementar a validação para abertura de sala.
		return VALIDATE_OK;
	}

	public static int diaRefeicao(DiaRefeicao diaRefeicao) {
		
		logger.info("Validação para Dia da Refeição.");
		
		Aluno aluno = diaRefeicao.getAluno();
		if (aluno == null || 
				(aluno != null 
					&& !numeroValidator.isMaiorZero(aluno.getId()))) {
			
			return ErrorFactory.ID_ALUNO_INVALIDO;
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
	
	public static int loginPessoa(PessoaAcesso pessoaAcesso) {
		
		logger.info("Validação para login de Funcionário.");
		
		if (!emailValidator.validate(pessoaAcesso.getEmail()))
			return ErrorFactory.EMAIL_USUARIO_INVALIDO;

		if (!stringValidator.validate(pessoaAcesso.getSenha(), 5, 40))
			return ErrorFactory.SENHA_USUARIO_INVALIDA;
		
		return VALIDATE_OK;
	}
	
	public static int loginAluno(PessoaAcesso pessoaAcesso) {
		
		logger.info("Validação para login de Aluno.");
		
		int validate = VALIDATE_OK;
		
		validate = matricula(pessoaAcesso.getMatricula());

		if (validate == VALIDATE_OK 
				&& !stringValidator.validate(pessoaAcesso.getSenha(), 5, 40))
			return ErrorFactory.SENHA_USUARIO_INVALIDA;
		
		return validate;
	}
	
	public static int logoutPessoa(String authorization) {
		logger.info("Validação para logout de Funcionário.");
		return 0;
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
	
	public static int downloadImagemPerfil(int idAluno) {
		
		logger.info("Validação para Download da Imagem do Perfil.");
		
		if (!numeroValidator.isMaiorZero(idAluno))
			return ErrorFactory.ID_ALUNO_INVALIDO;
		
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

	public static int matricula(String matricula) {
		
		Integer ano = Integer.valueOf(matricula.substring(0, 4));
		
		int MINIMO_DIGITOS_MATRICULA_ANO_2016 = 12;
		
		// No ano de 2016 a matrícula passou a ter 12 dígitos.
		if (ano >= ANO_MATRICULA_12_DIGITOS) {
			
			if (!numeroValidator.validate(matricula, 
					MINIMO_DIGITOS_MATRICULA_ANO_2016, 
					MAXIMO_DIGITOS_MATRICULA)) {
				
				return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
			}
			
		} else {
			
			if (!numeroValidator.validate(matricula, 
					MINIMO_DIGITOS_MATRICULA)) {
				
				return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
			}
		}
		
		return VALIDATE_OK;
	}
	
	public static int nomeAlunoBusca(String nome) {
		
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
		
		if (!numeroValidator.validate(matricula) 
				|| !stringValidator.validate(matricula, 
						MINIMO_DIGITOS_MATRICULA, 
						MAXIMO_DIGITOS_MATRICULA))
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
		
		return VALIDATE_OK;
	}
}
