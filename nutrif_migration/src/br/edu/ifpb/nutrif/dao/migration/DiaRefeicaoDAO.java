package br.edu.ifpb.nutrif.dao.migration;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.GenericDao;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.migration.DiaRefeicao;

public class DiaRefeicaoDAO extends GenericDao<Integer, DiaRefeicao> {
	 
	private static Logger logger = LogManager.getLogger(DiaRefeicaoDAO.class);

	private static DiaRefeicaoDAO instance;

	public DiaRefeicaoDAO() {
		super(HibernateUtil.getSessionFactoryMigration());
	}
	
	public static DiaRefeicaoDAO getInstance() {
		instance = new DiaRefeicaoDAO();
		return instance;
	}
	
	@Override
	public void delete(DiaRefeicao entity) {
		// TODO Auto-generated method stub
		super.delete(entity);
	}
	
	@Override
	public List<DiaRefeicao> getAll() throws SQLExceptionNutrIF {
		return super.getAll("DiaRefeicao.getAll");
	}
	
	@Override
	public Class<?> getEntityClass() {
		return DiaRefeicao.class;
	}

	@Override
	public DiaRefeicao find(DiaRefeicao entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}	
}