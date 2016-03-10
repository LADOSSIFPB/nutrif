package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.CronogramaRefeicao;

public class CronogramaRefeicaoDAO extends AbstractDAO<Integer, CronogramaRefeicao> {
	private static Logger logger = LogManager.getLogger(CronogramaRefeicaoDAO.class);

	private static CronogramaRefeicaoDAO instance;

	public static CronogramaRefeicaoDAO getInstance() {
		instance = new CronogramaRefeicaoDAO();
		return instance;
	}

	@Override
	public int delete(Integer pk) throws SQLExceptionNutrIF {
		// Não é necessário no momento @gustavo
		return 0;
	}

	@Override
	public List<CronogramaRefeicao> getAll() throws SQLExceptionNutrIF {
		return super.getAll("CronogramaRefeicao.getAll");
	}

	@Override
	public CronogramaRefeicao getById(Integer pk) throws SQLExceptionNutrIF {
		Session session = HibernateUtil.getSessionFactory().openSession();
		CronogramaRefeicao cronogramaRefeicao = null;

		try {

			session.beginTransaction();
			cronogramaRefeicao = (CronogramaRefeicao) session.get(CronogramaRefeicao.class, pk);
			Hibernate.initialize(cronogramaRefeicao);
			session.getTransaction().commit();

		} catch (HibernateException e) {

			logger.error(e.getMessage());
			session.getTransaction().rollback();

		} finally {

			session.close();
		}

		return cronogramaRefeicao;
	}

}
