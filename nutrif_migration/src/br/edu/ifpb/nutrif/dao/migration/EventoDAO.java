package br.edu.ifpb.nutrif.dao.migration;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.dao.GenericDao;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.migration.Evento;

public class EventoDAO extends GenericDao<Integer, Evento>{
	
	private static Logger logger = LogManager.getLogger(EventoDAO.class);
	
	private static EventoDAO instance;
	
	public EventoDAO() {
		super(HibernateUtil.getSessionFactoryMigration());
	}
	
	public static EventoDAO getInstance() {
		instance = new EventoDAO();
		return instance;
	}
	
	public List<Evento> listByNome(String nome) {
		
		Session session = HibernateUtil.getSessionFactoryOld().openSession();
		
		List<Evento> eventos = null;
		
		try {
			
			String hql = "from Evento as e"
					+ " where e.nome like :nome";
			
			Query query = session.createQuery(hql);
			query.setParameter("nome", "%" + nome + "%");
			
			eventos = (List<Evento>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return eventos;
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
