package br.edu.ifpb.nutrif.dao.migration;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.GenericDao;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.migration.ExtratoRefeicao;

public class ExtratoRefeicaoDAO extends GenericDao<Integer, ExtratoRefeicao>{
	
	private static Logger logger = LogManager.getLogger(ExtratoRefeicaoDAO.class);
	
	private static ExtratoRefeicaoDAO instance;
	
	public ExtratoRefeicaoDAO() {
		super(HibernateUtil.getSessionFactoryMigration());
	}
	
	public static ExtratoRefeicaoDAO getInstance() {
		instance = new ExtratoRefeicaoDAO();
		return instance;
	}

	@Override
	public List<ExtratoRefeicao> getAll() throws SQLExceptionNutrIF {
		return super.getAll("ExtratoRefeicao.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return ExtratoRefeicao.class;
	}

	@Override
	public ExtratoRefeicao find(ExtratoRefeicao entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
