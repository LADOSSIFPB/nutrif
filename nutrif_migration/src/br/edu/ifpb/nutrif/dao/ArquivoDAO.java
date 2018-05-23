package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ladoss.entity.Arquivo;
import br.edu.ladoss.enumeration.TipoArquivo;

public class ArquivoDAO extends GenericDao<Integer, Arquivo> {

	private static Logger logger = LogManager.getLogger(ArquivoDAO.class);
	
	private static ArquivoDAO instance;
	
	public ArquivoDAO() {
		super(HibernateUtil.getSessionFactoryOld());
	}
	
	public static ArquivoDAO getInstance() {		
		instance = new ArquivoDAO();		
		return instance;
	}
	
	public Arquivo getByNomeSistema(String nomeSistemaArquivo) {
		
		Arquivo arquivo = null;

		Session session = HibernateUtil.getSessionFactoryOld().openSession();
		
		try {
			
			String hql = "from Arquivo as a"
					+ " where a.nomeSistemaArquivo = :nomeSistemaArquivo";
			
			Query query = session.createQuery(hql);
			query.setParameter("nomeSistemaArquivo", nomeSistemaArquivo);
			
			arquivo = (Arquivo) query.uniqueResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return arquivo;		
	}
	
	public Arquivo getImagemPerfilByIdAluno(int idAluno) throws SQLExceptionNutrIF {
		
		Arquivo arquivo = null;

		Session session = HibernateUtil.getSessionFactoryOld().openSession();
		
		try {
			
			String hql = "from Arquivo as a"
					+ " where a.submetedor.id = :id"
					+ " and a.tipoArquivo = :tipoArquivo"
					+ " and a.ativo = :ativo"
					+ " order by a.registro DESC";
			
			Query query = session.createQuery(hql)
					.setMaxResults(1);
			query.setParameter("id", idAluno);
			query.setParameter("tipoArquivo", TipoArquivo.ARQUIVO_FOTO_PERFIL);
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			arquivo = (Arquivo) query.uniqueResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return arquivo;		
	}
	
	public List<Arquivo> getByIdPessoa(Integer idPessoa) {
		
		List<Arquivo> arquivos = null;

		Session session = HibernateUtil.getSessionFactoryOld().openSession();
		
		try {
			
			String hql = "from Arquivo as a"
					+ " where a.submetedor.id = :idPessoa"
					+ " and a.ativo = :ativo";
			
			Query query = session.createQuery(hql);
			query.setParameter("idPessoa", idPessoa);
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			arquivos = (List<Arquivo>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return arquivos;		
	}
	
	@Override
	public List<Arquivo> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Arquivo.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Arquivo.class;
	}

	@Override
	public Arquivo find(Arquivo entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
