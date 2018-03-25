package br.edu.ifpb.nutrif.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Campus;

public class CampusDAO extends GenericDao<Integer, Campus> {

	private static CampusDAO instance;

	public static CampusDAO getInstance() {
		instance = new CampusDAO();
		return instance;
	}

	@Override
	public List<Campus> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Campus.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Campus.class;
	}

	@Override
	public Campus find(Campus entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Pesquisar o Campus pela Cidade.
	 * 
	 * @param cidade
	 * @return
	 */
	public List<Campus> listByCidade(String cidade) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<Campus> campi = null;

		try {

			String hql = "from Campus as c" 
					+ " where c.cidade like :cidade";

			Query query = session.createQuery(hql, Campus.class);
			query.setParameter("cidade", "%" + cidade + "%");

			campi = (List<Campus>) query.getResultList();

		} catch (HibernateException hibernateException) {

			session.getTransaction().rollback();

			throw new SQLExceptionNutrIF(hibernateException);

		} finally {

			session.close();
		}

		return campi;
	}
}
