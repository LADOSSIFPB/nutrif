package br.edu.ifpb.nutrif.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.Role;

public class RoleDAO extends GenericDao<Integer, Role>{
	
	private static Logger logger = LogManager.getLogger(RoleDAO.class);
	
	private static RoleDAO instance;
	
	public RoleDAO() {
		super(HibernateUtil.getSessionFactoryOld());
	}
	
	public static RoleDAO getInstance() {
		instance = new RoleDAO();
		return instance;
	}

	public List<Role> getRolesByRolesId(List<Role> rolesId) 
			throws SQLExceptionNutrIF{
		
		List<Role> roles = new ArrayList<Role>();
		
		for (Role roleId: rolesId) {
			
			Role role = getById(roleId.getId());
			
			if (role != null) {
				
				roles.add(role);
			}			
		}
		
		return roles;		
	}
	
	@Override
	public List<Role> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Role.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Role.class;
	}

	@Override
	public Role find(Role entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
