package com.example.api.controller.v1;

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
import com.example.api.util.data.vo.v1.PersonVO;

@RestController
@RequestMapping("/api/person/v1")
public class PersonControlerV1 {

    @Autowired
    private PersonService service;
    // private PersonServices service = new PersonServices();

    @GetMapping(
        value = "/{id}",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    public PersonVO findById(@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping(
        value = "",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    public List<PersonVO> findAll() throws Exception {
        return service.findAll();
    }

    @PostMapping(
        value = "",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    public PersonVO create(@RequestBody PersonVO person) throws Exception {
        return service.create(person);
    }

    @PutMapping(
        value = "", 
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YML_VALUE }
    )
    public PersonVO update(@RequestBody PersonVO person) throws Exception {
        return service.update(person);
    }

    @DeleteMapping(
        value = "/{id}"
    )
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
