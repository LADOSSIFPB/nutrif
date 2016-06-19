package br.edu.ifpb.nutrif.service;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

public class JaxbJacksonObjectMapper extends ObjectMapper {

    public JaxbJacksonObjectMapper() {
        
    	configure(Feature.WRAP_ROOT_VALUE, false); // IMPORTANT must be false
        configure(Feature.WRITE_EMPTY_JSON_ARRAYS, true);
        configure(Feature.WRAP_EXCEPTIONS, true);
        configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        final AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();

        super.getSerializationConfig().withSerializationInclusion(
        		Inclusion.ALWAYS);
        super.getDeserializationConfig().withAnnotationIntrospector(
        		introspector);
        super.getSerializationConfig().withAnnotationIntrospector(
        		introspector);
    }
}