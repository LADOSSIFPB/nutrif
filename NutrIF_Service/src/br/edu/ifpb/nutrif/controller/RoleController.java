package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.edu.ifpb.nutrif.dao.RoleDAO;
import br.edu.ladoss.entity.Role;
import br.edu.ladoss.enumeration.TipoRole;

@Path("role")
public class RoleController {
	
	@RolesAllowed({TipoRole.ADMIN})
	@GET
	@Produces("application/json")
	public List<Role> getAll() {
		
		List<Role> roles = new ArrayList<Role>();
		
		roles = RoleDAO.getInstance().getAll();
		
		return roles;
	}
}
