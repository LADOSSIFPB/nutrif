package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.edu.ifpb.nutrif.dao.TurnoDAO;
import br.edu.ladoss.entity.Turno;

@Path("turno")
public class TurnoController {
	
	@PermitAll
	@GET
	@Produces("application/json")
	public List<Turno> getAll() {
		
		List<Turno> turnos = new ArrayList<Turno>();
		
		turnos = TurnoDAO.getInstance().getAll();
		
		return turnos;
	}
}
