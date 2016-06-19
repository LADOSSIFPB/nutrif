package br.edu.ifpb.nutrif.service;

import java.io.UnsupportedEncodingException;

import javax.annotation.security.PermitAll;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Root of RESTful api. It provides login and logout. Also have method for
 * printing every method which is provides by RESTful api.
 * 
 * @author Rostislav Novak (Computing and Information Centre, CTU in Prague)
 * 
 */
@Path("/")
public class NutrIFRestIndex {
    
	private static Logger logger = LogManager.getLogger(NutrIFRestIndex.class);

    @javax.ws.rs.core.Context public static ServletContext servletContext;

    /**
     * Return html page with information about REST api. It contains methods all
     * methods provide by REST api.
     * 
     * @return HTML page which has information about all methods of REST api.
     */
    @PermitAll
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() { 
    	
    	logger.info("Acesso ao index do serviço.");
    	
        return "<html>"
        		+ "<head>"
        		+ "	<meta http-equiv='content-type' content='text/html; charset=UTF-8'>"
        		+ "	<title>NutrIF Service - Home</title>"
        		+ "</head>" 
        		+ "<body>"
                	+ " <h1>NutrIF - Services </h1>"
                	+ " Server path: " + servletContext.getContextPath()
                	+ " <a href='servicos'>Serviços</a>"
                + "</body></html> ";
    }

    /**
     * Method to check current status of the service and logged in user.
     * 
     * okay: true | false
     * authenticated: true | false
     * epersonEMAIL: user@example.com
     * epersonNAME: John Doe
     * @param headers
     * @return
     */
    @PermitAll
    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)	
    public Status status(@Context HttpHeaders headers) 
    		throws UnsupportedEncodingException {
    	
    	return Response.Status.OK;
    }
}
