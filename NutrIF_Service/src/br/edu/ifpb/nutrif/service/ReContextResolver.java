package br.edu.ifpb.nutrif.service;

import java.text.SimpleDateFormat;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

@Provider
public final class ReContextResolver implements ContextResolver<ObjectMapper> {

    private ObjectMapper jacksonObjectMapper;

    public ReContextResolver() throws Exception {
        
    	jacksonObjectMapper = new JaxbJacksonObjectMapper();
        
        this.jacksonObjectMapper = new ObjectMapper();  
        this.jacksonObjectMapper.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));  
        this.jacksonObjectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    public ObjectMapper getContext(final Class<?> objectType) {
        return jacksonObjectMapper;
    }
}