package br.edu.ifpb.nutrif.hibernate;
  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
  
public class HibernateUtil {
  
	private static Logger logger = LogManager.getLogger(HibernateUtil.class);
	
    private static final SessionFactory sessionFactoryOld = buildSessionFactory("hibernate_old.cfg.xml");
    
    private static final SessionFactory sessionFactoryMigration = buildSessionFactory("hibernate_migration.cfg.xml");
    
    private static final SessionFactory sessionFactoryTransicao = buildSessionFactory("hibernate_transicao.cfg.xml");
  
    private static SessionFactory buildSessionFactory(String configName) {
        try {
        	
        	logger.info("Configurando conexão Hibernate->MySQL");
        	// Create the configuration based from hibernate_[nome].cfg.xml.
        	Configuration configuration = new Configuration().configure(configName);
        	
        	logger.info("Hibernate->MySQL - Properties: " + configuration.getProperties());
        	
        	// Create the SessionFactory from configuration.
            return configuration.buildSessionFactory();
        
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
        	logger.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
  
    public static SessionFactory getSessionFactoryOld() {
        return sessionFactoryOld;
    }
    
    public static SessionFactory getSessionFactoryMigration() {
        return sessionFactoryMigration;
    }
    
    public static SessionFactory getSessionFactoryTransicao() {
        return sessionFactoryTransicao;
    }
  
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactoryOld().close();
        getSessionFactoryMigration().close();
        getSessionFactoryTransicao().close();
    }
  
}