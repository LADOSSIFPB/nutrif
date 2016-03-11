package br.edu.ifpb.nutrif.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Curso;
import br.edu.ladoss.entity.Dia;
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
		
		logger.info("Validação para aluno.");
		//TODO: implementar a validação para abertura de sala.
		return VALIDATE_OK;
	}
	
	public static int curso(Curso curso) {		
		
		logger.info("Validação para curso.");
		
		String nome = curso.getNome();
		
		if (!stringValidator.validateSomenteLetras(nome))
			return ErrorFactory.NOME_CURSO_INVALIDO;		
		
		return VALIDATE_OK;
	}
	
	public static int refeicao(Refeicao refeicao) {
		
		logger.info("Validação para curso.");
		//TODO: implementar a validação para abertura de sala.
		return VALIDATE_OK;
	}
	
	public static int dia(Dia aluno) {
		
		logger.info("Validação para aluno.");
		//TODO: implementar a validação para abertura de sala.
		return VALIDATE_OK;
	}

	public static int diaRefeicao(DiaRefeicao cronogramaRefeicao) {
		
		logger.info("Validação para curso.");
		//TODO: implementar a validação para abertura de sala.
		return VALIDATE_OK;
	}
	
	public static int refeicaoRealizada(RefeicaoRealizada refeicaoRealizada) {
		
		logger.info("Validação para Refeicao Realizada.");
		//TODO: implementar a validação para abertura de sala.
		return VALIDATE_OK;
	}
	
}
