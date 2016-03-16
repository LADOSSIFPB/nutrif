package br.edu.ifpb.nutrif.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.core.ResourceMethodInvoker;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter {

	private static Logger logger = LogManager.getLogger(SecurityInterceptor.class);
	
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
    
	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		
		MultivaluedMap<String, String> headers = requestContext.getHeaders();
		
		ResourceMethodInvoker methodInvoker = 
				(ResourceMethodInvoker) requestContext.getProperty(
						"org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();
        
        if(!method.isAnnotationPresent(PermitAll.class)) {
        	
        	final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

            //If no authorization information present; block access
            if(authorization == null || authorization.isEmpty()) {
                
            	requestContext.abortWith(Response.status(
                		Response.Status.UNAUTHORIZED).build());
                return;
            }
            
            if(method.isAnnotationPresent(DenyAll.class)){
            	
				requestContext.abortWith(Response.status(
						Response.Status.UNAUTHORIZED).build());
				return;
			}
			
            // Analisar perfil do usuário.
			if(method.isAnnotationPresent(RolesAllowed.class)){
				
			}            
        }		
	}	
}