package br.edu.ifpb.nutrif.dao.migration;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.GenericDao;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.migration.SituacaoMatricula;

public class SituacaoMatriculaDAO extends GenericDao<Integer, SituacaoMatricula>{
	
	private static Logger logger = LogManager.getLogger(SituacaoMatriculaDAO.class);
	
	private static SituacaoMatriculaDAO instance;
	
	private SituacaoMatriculaDAO() {
		super(HibernateUtil.getSessionFactoryMigration());
	}
	
	public static SituacaoMatriculaDAO getInstance() {
		instance = new SituacaoMatriculaDAO();
		return instance;
	}

	@Override
	public List<SituacaoMatricula> getAll() throws SQLExceptionNutrIF {
		return super.getAll("SituacaoMatricula.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return SituacaoMatricula.class;
	}

	@Override
	public SituacaoMatricula find(SituacaoMatricula entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}	
}
