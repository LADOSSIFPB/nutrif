package br.edu.ifpb.nutrif.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.task.Constants;
import br.edu.ifpb.nutrif.task.MyTaskCollector;
import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.TaskCollector;

//@WebListener
public class ExtratoRefeicaoTaskListener implements ServletContextListener {

	private static Logger logger = LogManager.getLogger(TimeZoneListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		logger.info("--------- Init: ExtratoRefeicaoTaskListener ----------");
		
		ServletContext context = event.getServletContext();
		// 1. Creates the scheduler.
		Scheduler scheduler = new Scheduler();
		// 2. Registers a custom task collector.
		TaskCollector collector = new MyTaskCollector();
		scheduler.addTaskCollector(collector);
		// 3. Starts the scheduler.
		scheduler.start();
		// 4. Registers the scheduler.
		context.setAttribute(Constants.SCHEDULER, scheduler);
		
		logger.info("--------- Started: ExtratoRefeicaoTaskListener ----------");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
		logger.info("--------- Finalized: ExtratoRefeicaoTaskListener ----------");
		ServletContext context = event.getServletContext();
		// 1. Retrieves the scheduler from the context.
		Scheduler scheduler = (Scheduler) context.getAttribute(Constants.SCHEDULER);
		// 2. Removes the scheduler from the context.
		context.removeAttribute(Constants.SCHEDULER);
		// 3. Stops the scheduler.
		scheduler.stop();	
	}
}
