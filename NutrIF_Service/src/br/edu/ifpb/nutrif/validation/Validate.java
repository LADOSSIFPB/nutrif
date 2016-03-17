package br.edu.ifpb.nutrif.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.ConfirmaRefeicaoDia;
import br.edu.ladoss.entity.Curso;
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Funcionario;
import br.edu.ladoss.entity.PretencaoRefeicao;
import br.edu.ladoss.entity.Refeicao;
import br.edu.ladoss.entity.RefeicaoRealizada;

public class Validate {

	private static Logger logger = LogManager.getLogger(Validate.class);
	
	private static StringValidator stringValidator = new StringValidator();
	private static NumeroValidator numeroValidator = new NumeroValidator();
	private static EmailValidator emailValidator = new EmailValidator();
	private static DataValidator dataValidator = new DataValidator();

	public static int VALIDATE_OK = 0;	
	
	public static int inserirAluno(Aluno aluno) {
		
		logger.info("Validação para Aluno.");
		
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
		
		logger.info("Validação para acesso de Aluno.");
		
		if (!stringValidator.validate(aluno.getMatricula(), 11))
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
		
		if (!emailValidator.validate(aluno.getEmail()))
			return ErrorFactory.EMAIL_USUARIO_INVALIDO;

		if (!stringValidator.validate(aluno.getSenha(), 5, 40))
			return ErrorFactory.SENHA_USUARIO_INVALIDA;
		
		return VALIDATE_OK;
	}
	
	public static int loginAluno(Aluno aluno) {
		
		logger.info("Validação para acesso de Aluno.");
		String matricula = aluno.getMatricula();
		String email = aluno.getEmail();		
		
		if (matricula == null && email == null) {
			return ErrorFactory.NOME_MATRICULA_ALUNO_INVALIDOS;
		}		
		
		if (matricula != null 
				&& !stringValidator.validate(aluno.getMatricula(), 11))
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
		
		if (email != null 
				&& !emailValidator.validate(aluno.getEmail()))
			return ErrorFactory.EMAIL_USUARIO_INVALIDO;

		if (!stringValidator.validate(aluno.getSenha(), 5, 40))
			return ErrorFactory.SENHA_USUARIO_INVALIDA;
		
		return VALIDATE_OK;
	}
	
	public static int confirmacaoAluno(Aluno aluno) {
		
		logger.info("Validação para confirmação de aluno.");
		
		if (!stringValidator.validate(aluno.getMatricula(), 11))
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
	
	public static int refeicao(Refeicao refeicao) {
		
		logger.info("Validação para Refeição.");
		//TODO: implementar a validação.
		return VALIDATE_OK;
	}
	
	public static int pretencaoRefeicao(PretencaoRefeicao refeicao) {
		
		logger.info("Validação para Pretencao da Refeicao.");
		//TODO: implementar a validação.
		return VALIDATE_OK;
	}
	
	public static int dia(Dia aluno) {
		
		logger.info("Validação para aluno.");
		//TODO: implementar a validação para abertura de sala.
		return VALIDATE_OK;
	}

	public static int diaRefeicao(DiaRefeicao cronogramaRefeicao) {
		
		logger.info("Validação para Dia da Refeição.");
		
		Aluno aluno = cronogramaRefeicao.getAluno();
		if (aluno == null || 
				(aluno != null 
					&& !numeroValidator.isMaiorZero(aluno.getId()))) {
			return ErrorFactory.ID_ALUNO_INVALIDO;
		}
		
		Dia dia = cronogramaRefeicao.getDia();
		if (dia == null || 
				(dia != null 
					&& !numeroValidator.isInteiroPositivo(dia.getId()))) {
			return ErrorFactory.ID_DIA_INVALIDO;
		}
		
		Refeicao refeicao = cronogramaRefeicao.getRefeicao();
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
		
		return VALIDATE_OK;
	}
	
	public static int funcionario(Funcionario usuario) {
		
		logger.info("Validação para Funcionário.");
		
		String nome = usuario.getNome();
		if (!stringValidator.validateSomenteLetras(nome))
			return ErrorFactory.NOME_USUARIO_INVALIDO;
		
		String senha = usuario.getSenha();
		if (!stringValidator.validatePassword(senha))
			return ErrorFactory.SENHA_USUARIO_INVALIDA;
		
		return VALIDATE_OK;
	}	
}
