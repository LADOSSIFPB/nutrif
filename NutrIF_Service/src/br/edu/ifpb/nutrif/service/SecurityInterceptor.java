package br.edu.ifpb.nutrif.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

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

import br.edu.ifpb.nutrif.dao.PessoaDAO;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Pessoa;
import br.edu.ladoss.entity.Role;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter {

	private static Logger logger = LogManager.getLogger(SecurityInterceptor.class);
	
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
    
    /**
     * Analisar níveis de acesso aos serviços baseado nas permissões dos
     * usuários.
     */
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
                if(!isUserAllowed(keyAuth, rolesSet)) {
                    
                	requestContext.abortWith(Response.status(
    						Response.Status.UNAUTHORIZED).build());
                    return;
                }
			}            
        }		
	}
	
	/**
	 * Verificar se o usuário autenticado possui permissão de acesso ao serviço.
	 * 
	 * @param keyAuth
	 * @param rolesSet
	 * @return
	 */
	private boolean isUserAllowed(final String keyAuth, final Set<String> rolesSet) {
		
		boolean isAllowed = false;
		
		int validacao = Validate.acessoServicoKeyAuth(keyAuth);
		
		if (validacao == Validate.VALIDATE_OK) {
			Pessoa pessoa = PessoaDAO.getInstance().getByKeyAuth(keyAuth);
			
			if (pessoa != null) {
				
				List<Role> rolesPessoa = pessoa.getRoles();
				
				for (Role rolePessoa: rolesPessoa) {
					
					isAllowed = rolesSet.contains(rolePessoa.getNome());
					
					if (isAllowed)
						break;
				}				
			}			
		}
		
		return isAllowed;
	}
}