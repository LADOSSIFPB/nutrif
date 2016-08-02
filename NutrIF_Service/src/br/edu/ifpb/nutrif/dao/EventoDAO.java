package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.Evento;

public class EventoDAO extends GenericDao<Integer, Evento>{
	
	private static Logger logger = LogManager.getLogger(EventoDAO.class);
	
	private static EventoDAO instance;
	
	public static EventoDAO getInstance() {
		instance = new EventoDAO();
		return instance;
	}

	@Override
	public List<Evento> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Evento.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Evento.class;
	}

	@Override
	public Evento find(Evento entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
