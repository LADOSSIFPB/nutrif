package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.RefeicaoRealizada;

public class RefeicaoRealizadaDAO extends AbstractDAO<Integer, RefeicaoRealizada> {
	private static Logger logger = LogManager.getLogger(RefeicaoRealizadaDAO.class);

	private static RefeicaoRealizadaDAO instance;

	public static RefeicaoRealizadaDAO getInstance() {
		instance = new RefeicaoRealizadaDAO();
		return instance;
	}

	@Override
	public int delete(Integer pk) throws SQLExceptionNutrIF {
		// Não é necessário no momento @gustavo
		return 0;
	}

	@Override
	public List<RefeicaoRealizada> getAll() throws SQLExceptionNutrIF {
		return super.getAll("RefeicaoRealizada.getAll");
	}

	@Override
	public RefeicaoRealizada getById(Integer pk) throws SQLExceptionNutrIF {
		Session session = HibernateUtil.getSessionFactory().openSession();
		RefeicaoRealizada refeicaoRealizada = null;

		try {

			session.beginTransaction();
			refeicaoRealizada = (RefeicaoRealizada) session.get(RefeicaoRealizada.class, pk);
			Hibernate.initialize(refeicaoRealizada);
			session.getTransaction().commit();

		} catch (HibernateException e) {

			logger.error(e.getMessage());
			session.getTransaction().rollback();

		} finally {

			session.close();
		}

		return refeicaoRealizada;
	}
}
