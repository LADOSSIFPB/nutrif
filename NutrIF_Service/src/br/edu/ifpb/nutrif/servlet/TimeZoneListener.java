package br.edu.ifpb.nutrif.servlet;

import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class TimeZoneListener implements ServletContextListener {

	private static Logger logger = LogManager.getLogger(TimeZoneListener.class);

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
    	
		// Timezone para o Nordeste do Brasil.
    	TimeZone.setDefault(TimeZone.getTimeZone("America/Fortaleza"));
    	
    	// Verificação.
    	Calendar now = Calendar.getInstance();
    	logger.info("--------- TimeZone ----------");
        logger.info(now.getTimeZone());
        logger.info(now.getTime());
        logger.info("-----------------------------");

	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}
}
