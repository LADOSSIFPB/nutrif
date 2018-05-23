package br.edu.ifpb.nutrif.servlet;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.util.FileUtil;

/**
 * Cria��o de estrutura de diret�rio para armazenamento das imagens de perfil.
 * A rotina verificar� se alguma estrutura j� foi criada anteriormente.
 * 
 * @author Rhavy
 *
 */
@WebListener
public class DirectoryListener implements ServletContextListener {
	
	private static Logger logger = LogManager.getLogger(DirectoryListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		logger.info("Verificando diret�rios.");
		logger.info("Base:" + FileUtil.BASE_PATH);
		
		List<Path> paths = new ArrayList<Path>();
		
		Path basePath = Paths.get(FileUtil.BASE_PATH);
		paths.add(basePath);
		
		logger.info("Base path: " + basePath);
		
		Path projetoPath = Paths.get(FileUtil.PERFIL_PATH);
		paths.add(projetoPath);
		
		if (Files.notExists(basePath)) {
			
			logger.warn("Os diret�rios de arquivos n�o existem!");
			
			for (Path path: paths) {
				
				File file = new File(path.toUri());
				file.mkdir();
				
				logger.info("Diret�rio " + path.getFileName() + " criado.");
			}
			
		} else {
			
			logger.info("Estrutura de diret�rio j� estabelecida.");
		}
	}

}
