package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.Motivo;

public class MotivoDAO extends GenericDao<Integer, Motivo>{
	
	private static Logger logger = LogManager.getLogger(MotivoDAO.class);
	
	private static MotivoDAO instance;
	
	public MotivoDAO() {
		super(HibernateUtil.getSessionFactoryOld());
	}
	
	public static MotivoDAO getInstance() {
		instance = new MotivoDAO();
		return instance;
	}

	@Override
	public List<Motivo> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Motivo.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Motivo.class;
	}

	@Override
	public Motivo find(Motivo entity) throws SQLExceptionNutrIF {
		return null;
	}	
}
