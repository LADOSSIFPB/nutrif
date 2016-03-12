package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Usuario;

public class UsuarioDAO extends GenericDao<Integer, Usuario> {

	private static Logger logger = LogManager.getLogger(UsuarioDAO.class);
	
	private static UsuarioDAO instance;
	
	public static UsuarioDAO getInstance() {		
		instance = new UsuarioDAO();		
		return instance;
	}

	@Override
	public List<Usuario> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Usuario.getAll");
	}

	@Override
	public Class<?> getEntityClass() {		
		return Usuario.class;
	}
}
