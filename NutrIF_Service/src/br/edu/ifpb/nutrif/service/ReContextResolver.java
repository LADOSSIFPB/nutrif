package br.edu.ifpb.nutrif.service;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.ObjectMapper;

@Provider
public final class ReContextResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper jacksonObjectMapper;

    public ReContextResolver() throws Exception {
        jacksonObjectMapper = new JaxbJacksonObjectMapper();
    }

    @Override
    public ObjectMapper getContext(final Class<?> objectType) {
        return jacksonObjectMapper;
    }
}