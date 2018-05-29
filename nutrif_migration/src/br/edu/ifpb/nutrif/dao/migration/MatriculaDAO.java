package br.edu.ifpb.nutrif.dao.migration;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.GenericDao;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.migration.Matricula;

public class MatriculaDAO extends GenericDao<Integer, Matricula> {

	private static Logger logger = LogManager.getLogger(MatriculaDAO.class);
	
	private static MatriculaDAO instance;
	
	public MatriculaDAO() {
		super(HibernateUtil.getSessionFactoryMigration());
	}
	
	public static MatriculaDAO getInstance() {		
		instance = new MatriculaDAO();		
		return instance;
	}

	@Override
	public List<Matricula> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Matricula.getAll");
	}

	@Override
	public Class<?> getEntityClass() {		
		return Matricula.class;
	}

	@Override
	public Matricula find(Matricula entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
