package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.Turno;

public class TurnoDAO extends GenericDao<Integer, Turno>{
	
	private static Logger logger = LogManager.getLogger(TurnoDAO.class);
	
	private static TurnoDAO instance;
	
	public static TurnoDAO getInstance() {
		instance = new TurnoDAO();
		return instance;
	}

	@Override
	public List<Turno> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Turno.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return TurnoDAO.class;
	}

	@Override
	public Turno find(Turno entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}	
}
