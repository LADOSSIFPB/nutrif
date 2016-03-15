package br.edu.ifpb.nutrif.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Curso;
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Funcionario;
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
		
		logger.info("Valida��o para aluno.");
		
		if (!stringValidator.validateSomenteLetras(aluno.getNome()))
			return ErrorFactory.NOME_ALUNO_INVALIDO;
			
		if (!numeroValidator.validate(aluno.getMatricula()))
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
			
		Curso curso = aluno.getCurso();
		if (curso != null 
				&& !numeroValidator.isInteiroPositivo(curso.getId())
				&& curso.getId() == BancoUtil.IDVAZIO)
			return ErrorFactory.ID_CURSO_INVALIDO;		
		
		return VALIDATE_OK;
	}
	
	public static int acessoAluno(Aluno aluno) {
		
		logger.info("Valida��o para acesso de aluno.");
		
		if (!stringValidator.validate(aluno.getMatricula(), 11))
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
		
		if (!emailValidator.validate(aluno.getEmail()))
			return ErrorFactory.EMAIL_USUARIO_INVALIDO;

		if (!stringValidator.validate(aluno.getSenha(), 5, 40))
			return ErrorFactory.SENHA_USUARIO_INVALIDA;
		
		return VALIDATE_OK;
	}
	
	public static int confirmacaoAluno(Aluno aluno) {
		
		logger.info("Valida��o para confirma��o de aluno.");
		
		if (!stringValidator.validate(aluno.getMatricula(), 11))
			return ErrorFactory.MATRICULA_ALUNO_INVALIDA;
		
		if (!stringValidator.validate(aluno.getKeyConfirmation(), 5))
			return ErrorFactory.KEY_CONFIRMATION_INVALIDA;
		
		return VALIDATE_OK;
	}

	public static int curso(Curso curso) {		
		
		logger.info("Valida��o para curso.");
		
		String nome = curso.getNome();
		
		if (!stringValidator.validateSomenteLetras(nome))
			return ErrorFactory.NOME_CURSO_INVALIDO;		
		
		return VALIDATE_OK;
	}
	
	public static int refeicao(Refeicao refeicao) {
		
		logger.info("Valida��o para curso.");
		//TODO: implementar a valida��o para abertura de sala.
		return VALIDATE_OK;
	}
	
	public static int dia(Dia aluno) {
		
		logger.info("Valida��o para aluno.");
		//TODO: implementar a valida��o para abertura de sala.
		return VALIDATE_OK;
	}

	public static int diaRefeicao(DiaRefeicao cronogramaRefeicao) {
		
		logger.info("Valida��o para Dia da Refei��o.");
		
		Aluno aluno = cronogramaRefeicao.getAluno();
		if (aluno == null || 
				(aluno != null 
					&& !numeroValidator.isInteiroPositivo(aluno.getId()))) {
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
		
		logger.info("Valida��o para Refeicao Realizada.");
		//TODO: implementar a valida��o para abertura de sala.
		return VALIDATE_OK;
	}
	
	public static int funcionario(Funcionario usuario) {
		
		logger.info("Valida��o para Usu�rio.");
		
		String nome = usuario.getNome();
		if (!stringValidator.validateSomenteLetras(nome))
			return ErrorFactory.NOME_USUARIO_INVALIDO;
		
		String senha = usuario.getSenha();
		if (!stringValidator.validatePassword(senha))
			return ErrorFactory.SENHA_USUARIO_INVALIDA;
		
		return VALIDATE_OK;
	}	
}
