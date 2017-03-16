package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.edu.ifpb.nutrif.dao.PeriodoDAO;
import br.edu.ladoss.entity.Periodo;

@Path("periodo")
public class PeriodoController {
	
	@PermitAll
	@GET
	@Path("/listar")
	@Produces("application/json")
	public List<Periodo> getAll() {
		
		List<Periodo> periodos = new ArrayList<Periodo>();
		
		periodos = PeriodoDAO.getInstance().getAll();
		
		return periodos;
	}
}
