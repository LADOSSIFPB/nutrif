package br.edu.ifpb.nutrif.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Arquivo;
import br.edu.ladoss.entity.ConfirmaPretensaoDia;
import br.edu.ladoss.entity.ConfirmaRefeicaoDia;
import br.edu.ladoss.entity.Curso;
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Funcionario;
import br.edu.ladoss.entity.PessoaAcesso;
import br.edu.ladoss.entity.PretensaoRefeicao;
import br.edu.ladoss.entity.Refeicao;
import br.edu.ladoss.entity.RefeicaoRealizada;
import br.edu.ladoss.enumeration.TipoArquivo;
import br.edu.ladoss.form.FileUploadForm;

public class Validate {

	private static Logger logger = LogManager.getLogger(Validate.class);
	
	private static StringValidator stringValidator = new StringValidator();
	private static NumeroValidator numeroValidator = new NumeroValidator();
	private static EmailValidator emailValidator = new EmailValidator();
	private static ImageValidator imageValidator = new ImageValidator();
	private static DataValidator dataValidator = new DataValidator();

	public static int VALIDATE_OK = 0;	
	
	public static int inserirAluno(Aluno aluno) {
		
		logger.info("Valida��o para Aluno.");
		
		if (!stringValidator.validateSomenteLetras(aluno.getNome()))
			return ErrorFactory.NOME_ALUNO_INVALIDO;
			
		if (!numeroValidator.validate(aluno.getMatricula()))
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
			
		Curso curso = aluno.getCurso();
		if (curso != null 
				&& !numeroValidator.isMaiorZero(curso.getId())
				&& !numeroValidator.isMaiorZero(curso.getId()))
			return ErrorFactory.ID_CURSO_INVALIDO;		
		
		return VALIDATE_OK;
	}
	
	public static int acessoAluno(Aluno aluno) {
		
		logger.info("Valida��o para acesso de Aluno.");
		
		if (!stringValidator.validate(aluno.getMatricula(), 11))
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
		
		if (!emailValidator.validate(aluno.getEmail()))
			return ErrorFactory.EMAIL_USUARIO_INVALIDO;

		if (!stringValidator.validate(aluno.getSenha(), 5, 40))
			return ErrorFactory.SENHA_USUARIO_INVALIDA;
		
		return VALIDATE_OK;
	}
	
	public static int acessoServicoKeyAuth(String keyAuth) {
		
		logger.info("Valida��o para Chave de Autentica��o.");
		
		if (!stringValidator.validate(keyAuth, 64))
			return ErrorFactory.CHAVE_AUTORIZACAO_PESSOA_INVALIDA;
		
		return VALIDATE_OK;
	}
	
	public static int confirmacaoChaveAluno(Aluno aluno) {
		
		logger.info("Valida��o para confirma��o de aluno.");
		
		if (!stringValidator.validate(aluno.getMatricula(), 11))
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
		
		if (!stringValidator.validate(aluno.getKeyConfirmation(), 5))
			return ErrorFactory.KEY_CONFIRMATION_INVALIDA;
		
		return VALIDATE_OK;
	}

	public static int curso(Curso curso) {		
		
		logger.info("Valida��o para Curso.");
		
		String nome = curso.getNome();
		
		if (!stringValidator.validateSomenteLetras(nome))
			return ErrorFactory.NOME_CURSO_INVALIDO;		
		
		return VALIDATE_OK;
	}
	
	public static int refeicao(Refeicao refeicao) {
		
		logger.info("Valida��o para Refei��o.");
		//TODO: implementar a valida��o.
		return VALIDATE_OK;
	}
	
	public static int pretensaoRefeicao(PretensaoRefeicao pretensaoRefeicao) {
		
		logger.info("Valida��o para Pretens�o Refei��o.");
		
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
		
		logger.info("Valida��o da chave de acesso para Pretens�o da Refeicao.");
 
		if (pretensaoRefeicao == null 
				|| !(pretensaoRefeicao != null && stringValidator.validate(
						pretensaoRefeicao.getKeyAccess(), 64)))
			return ErrorFactory.CHAVE_ACESSO_PRETENSAO_INVALIDA;
		
		return VALIDATE_OK;
	}
	
	public static int dia(Dia aluno) {
		
		logger.info("Valida��o para Dia.");
		//TODO: implementar a valida��o para abertura de sala.
		return VALIDATE_OK;
	}

	public static int diaRefeicao(DiaRefeicao diaRefeicao) {
		
		logger.info("Valida��o para Dia da Refei��o.");
		
		Aluno aluno = diaRefeicao.getAluno();
		if (aluno == null || 
				(aluno != null 
					&& !numeroValidator.isMaiorZero(aluno.getId()))) {
			return ErrorFactory.ID_ALUNO_INVALIDO;
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
	
	public static int refeicaoRealizada(RefeicaoRealizada refeicaoRealizada) {
		
		logger.info("Valida��o para Refei��o Realizada.");
		
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
	
	public static int funcionario(Funcionario usuario) {
		
		logger.info("Valida��o para Funcion�rio.");
		
		String nome = usuario.getNome();
		if (!stringValidator.validateSomenteLetras(nome))
			return ErrorFactory.NOME_USUARIO_INVALIDO;
		
		
		if (!stringValidator.validate(usuario.getSenha(), 5, 40))
			return ErrorFactory.SENHA_USUARIO_INVALIDA;
		
		return VALIDATE_OK;
	}
	
	public static int acessoPessoa(PessoaAcesso pessoaAcesso) {
		
		logger.info("Valida��o para acesso de Funcion�rio.");
		
		if (!emailValidator.validate(pessoaAcesso.getEmail()))
			return ErrorFactory.EMAIL_USUARIO_INVALIDO;

		if (!stringValidator.validate(pessoaAcesso.getSenha(), 5, 40))
			return ErrorFactory.SENHA_USUARIO_INVALIDA;
		
		return VALIDATE_OK;
	}
	
	public static int downloadArquivo(TipoArquivo tipoArquivo, 
			String nomeRealArquivo) {
		
		logger.info("Valida��o para Download de Arquivo.");
	
		if (tipoArquivo == null) 
			return ErrorFactory.TIPO_ARQUIVO_INVALIDO;
		
		if (!imageValidator.validate(nomeRealArquivo))
			return ErrorFactory.NOME_ARQUIVO_INVALIDO;
		
		return VALIDATE_OK;
	}
	
	public static int downloadImagemPerfil(int idAluno) {
		
		logger.info("Valida��o para Download da Imagem do Perfil.");
		
		if (!numeroValidator.isMaiorZero(idAluno))
			return ErrorFactory.ID_ALUNO_INVALIDO;
		
		return VALIDATE_OK;
	}

	public static int uploadArquivo(TipoArquivo tipoArquivo, 
			FileUploadForm form) {
		
		logger.info("Valida��o para Upload de Arquivo.");
		
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
}
