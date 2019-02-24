package br.edu.ifpb.nutrif.dao.suap;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.GenericDao;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.model.suap.AlunoSuap;

public class AlunoSuapDAO extends GenericDao<Integer, AlunoSuap> {

	private static Logger logger = LogManager.getLogger(AlunoSuapDAO.class);
	
	private static AlunoSuapDAO instance;
	
	public AlunoSuapDAO() {
		super(HibernateUtil.getSessionFactoryOld());
	}
	
	public static AlunoSuapDAO getInstance() {		
		instance = new AlunoSuapDAO();		
		return instance;
	}

	@Override
	public List<AlunoSuap> getAll() throws SQLExceptionNutrIF {
		return super.getAll("AlunoSuap.getAll");
	}

	@Override
	public Class<?> getEntityClass() {		
		return AlunoSuap.class;
	}

	@Override
	public AlunoSuap find(AlunoSuap entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
