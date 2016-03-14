package br.edu.ifpb.nutrif.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.ErrorFactory;
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
	
	public static int aluno(Aluno aluno) {
		
		logger.info("Valida��o para aluno.");
		//TODO: implementar a valida��o para abertura de sala.
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
	
	public static int usuario(Funcionario usuario) {
		
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
