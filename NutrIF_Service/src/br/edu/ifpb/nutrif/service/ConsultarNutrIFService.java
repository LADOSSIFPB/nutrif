package br.edu.ifpb.nutrif.service;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.AlunoDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Erro;

/**
 * Classe que reune serviços de consulta ao banco de dados.
 * 
 * @author Gustavo Ribeiro
 */

@Path("nutrif")
public class ConsultarNutrIFService {

	private static Logger logger = LogManager.getLogger(ConsultarNutrIFService.class);

	@GET
	@Path("/searchStudent")
	@Produces("application/json")
	public Response searchStudentByNameAndRegistration(@PathParam("nome") String nome, @PathParam("matricula") String matricula) {
		logger.info("Buscando aluno " + nome + " de matrícula " + matricula);
		
		ResponseBuilder builder;
		Aluno aluno = this.findStudentByNameAndRegistration(nome, matricula);
		
		if (aluno == null) {
			Erro error = ErrorFactory.getErrorFromIndex(ErrorFactory.ALUNO_NAO_ENCONTRADO);
			builder = Response.status(Response.Status.NOT_FOUND).entity(error);
			
			logger.info("Unsuccessful attempt to find student");
		} else {
			builder = Response.status(Response.Status.FOUND).entity(aluno);
			
			logger.info("Successful attempt to find student");
		}
		
		return builder.build();
	}
	
	private Aluno findStudentByNameAndRegistration(String nome, String matricula) {
		AlunoDAO alunoDAO = AlunoDAO.getInstance();
		
		try{
			Aluno aluno  = alunoDAO.getByNameAndRegistration(nome, matricula);
			return aluno;
		}catch(SQLExceptionNutrIF sqlenif){
			logger.error(sqlenif.getMessage());
			return null;
		}
	}
	
	@GET
	@Path("/getallMealBetweenDates")
	@Produces("application/json")
	public Response getAllMeatlBetweenDates(Date initialDate, Date finalDate) {
		//TODO implementar @gustavo
		return null;
	}
	
}
