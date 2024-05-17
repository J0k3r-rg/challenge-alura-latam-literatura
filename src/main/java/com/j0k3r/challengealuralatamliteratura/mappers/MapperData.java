package com.j0k3r.challengealuralatamliteratura.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperData implements IConverterData{

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> clas) {
        try {
            return mapper.readValue(json, clas);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
