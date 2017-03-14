package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.edu.ifpb.nutrif.dao.TurmaDAO;
import br.edu.ladoss.entity.Turma;
import br.edu.ladoss.entity.Turno;

@Path("turma")
public class TurmaController {
	
	@PermitAll
	@GET
	@Path("/listar")
	@Produces("application/json")
	public List<Turma> getAll() {
		
		List<Turma> turmas = new ArrayList<Turma>();
		
		turmas = TurmaDAO.getInstance().getAll();
		
		return turmas;
	}
}
