package br.edu.ifpb.nutrif.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.CampusDAO;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.Campus;
import br.edu.ladoss.enumeration.TipoRole;

@Path("migracao")
public class MigracaoController {

	private static Logger logger = LogManager.getLogger(MigracaoController.class);

	/**
	 * 
	 * @return
	 */
	@RolesAllowed({ TipoRole.ADMIN })
	@GET
	@Path("/iniciar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response iniciar() {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {
			
			// Campus
			migraCampus();			
			
			// Nível
			// Turma
			// Turno
			// Ensino
			// Motivo
			// Roles
			// Refeição
			// Pessoa
			// Pessoa-Role
			// Funcionario
			// Aluno
			// DiaRefeicao
			// RefeicaoRealizada
			// Login		

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getError());
		}

		return builder.build();
	}

	private void migraCampus() {
		
		logger.info("Migrar Campus");
		
		List<Campus> campi = CampusDAO.getInstance().getAll();
		
		for (Campus campus: campi) {
			br.edu.ladoss.entity.migration.Campus campusMigracao = 
					new br.edu.ladoss.entity.migration.Campus();
			
			campusMigracao.setId(campus.getId());
			campusMigracao.setCidade(campus.getCidade());
			campusMigracao.setSigla(campus.getSigla());
			campusMigracao.setAtivo(campus.isAtivo());
			
			logger.info(campusMigracao);
		}		
	}
}