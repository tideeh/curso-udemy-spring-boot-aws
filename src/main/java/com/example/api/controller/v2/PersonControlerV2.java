package com.example.api.controller.v2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.service.PersonService;
import com.example.api.util.MediaType;
import com.example.api.util.data.vo.v2.PersonVOV2;

@RestController
@RequestMapping(value = {"/api/person/v2", "/api/person"})
public class PersonControlerV2 {

    @Autowired
    private PersonService service;
    // private PersonServices service = new PersonServices();

    @GetMapping(
        value = "/{id}",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    public PersonVOV2 findByIdV2(@PathVariable(value = "id") Long id) throws Exception {
        return service.findByIdV2(id);
    }

    @GetMapping(
        value = "",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    public List<PersonVOV2> findAllV2() throws Exception {
        return service.findAllV2();
    }

    @PostMapping(
        value = "",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    public PersonVOV2 createV2(@RequestBody PersonVOV2 person) throws Exception {
        return service.createV2(person);
    }

    @PutMapping(
        value = "", 
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    public PersonVOV2 updateV2(@RequestBody PersonVOV2 person) throws Exception {
        return service.updateV2(person);
    }

    @DeleteMapping(
        value = "/{id}"
    )
    public ResponseEntity<?> deleteV2(@PathVariable(value = "id") Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
