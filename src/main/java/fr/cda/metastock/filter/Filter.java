package fr.cda.metastock.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Filter {
    
    public static <T> T buildFromJson(String jsonString, Class<T> returnClass) throws JsonMappingException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, returnClass);
    }
}
