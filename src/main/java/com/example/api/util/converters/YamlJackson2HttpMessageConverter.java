package com.example.api.util.converters;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public class YamlJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {

    public YamlJackson2HttpMessageConverter() {
        super(
            new YAMLMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL),
            MediaType.parseMediaType(com.example.api.util.MediaType.APPLICATION_YML_VALUE)
        );
    }
    
}
