package br.edu.ifpb.nutrif.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter {

	private static Logger logger = LogManager.getLogger(SecurityInterceptor.class);
	
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
    private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401, new Headers<Object>());
    
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
				
				//Get encoded username and password
	            final String keyAuth = authorization.get(0).replaceFirst(
	            		AUTHENTICATION_SCHEME_BASIC + " ", "");
	            
				RolesAllowed rolesAnnotation = method.getAnnotation(
						RolesAllowed.class);
                Set<String> rolesSet = new HashSet<String>(Arrays.asList(
                		rolesAnnotation.value()));
                 
                //Is user valid?
                /*if( ! isUserAllowed(keyAuth, rolesSet))
                {
                    requestContext.abortWith(ACCESS_DENIED);
                    return;
                }*/
			}            
        }		
	}
	
	private boolean isUserAllowed(final String keyAuth, final Set<String> rolesSet) {
		boolean isAllowed = false;

		// Step 1. Fetch password from database and match with password in
		// argument
		// If both match then get the defined role for user from database and
		// continue; else return isAllowed [false]
		// Access the database and do this part yourself
		// String userRole = userMgr.getUserRole(username);
		String userRole = "ADMIN";

		// Step 2. Verify user role
		if (rolesSet.contains(userRole)) {
			isAllowed = true;
		}
		return isAllowed;
	}
}