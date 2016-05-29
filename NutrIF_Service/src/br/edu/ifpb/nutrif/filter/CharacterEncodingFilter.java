package br.edu.ifpb.nutrif.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebFilter(filterName = "CharacterEncodingFilter", urlPatterns = { "/*" })
public class CharacterEncodingFilter implements Filter {

	private static Logger logger = LogManager.getLogger(
			CharacterEncodingFilter.class);
	
	public CharacterEncodingFilter() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain chain) throws IOException, ServletException {
		
		// setting the charset
		response.setCharacterEncoding(StandardCharsets.ISO_8859_1.toString());
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("Init filter: " + CharacterEncodingFilter.class);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
}