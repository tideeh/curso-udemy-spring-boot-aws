package com.example.api.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.repositories.PersonRepository;
import com.example.api.util.data.vo.v1.PersonVO;
import com.example.api.util.data.vo.v2.PersonVOV2;
import com.example.api.util.exceptions.ResourceNotFoundException;
import com.example.api.util.mapper.MapperVO;

@Service
public class PersonServices {

    @Autowired
    PersonRepository repository;

    @Autowired
    MapperVO mapper;

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public PersonVO findById(Long id) {
        logger.info("Find Person by ID");;

        var entity = repository.findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID!")
            );
        
        return mapper.personToPersonVO(entity);
    }

    public List<PersonVO> findAll() {
        logger.info("Find all Persons");

        return mapper.personListToPersonVOList(repository.findAll());
    }

    public PersonVO create(PersonVO personVO) {
        logger.info("Create one person");

        var entity = mapper.personVOToPerson(personVO);
        var vo = mapper.personToPersonVO(repository.save(entity));

        return vo;
    }

    public PersonVOV2 createV2(PersonVOV2 personVOV2) {
        logger.info("Create one person - V2");

        var entity = mapper.personVOV2ToPerson(personVOV2);
        var vo = mapper.personToPersonVOV2(repository.save(entity));

        return vo;
    }

    public PersonVO update(PersonVO personVO) {
        logger.info("Update the person");

        var entity = repository.findById(personVO.getId())
            .orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID!")
            );
        
        entity.setFirstName(personVO.getFirstName());
        entity.setLastName(personVO.getLastName());
        entity.setAddres(personVO.getAddres());
        entity.setGender(personVO.getGender());

        var vo = mapper.personToPersonVO(repository.save(entity));

        return vo;
    }

    public void delete(Long id) {
        logger.info("Delete the person");

        var entity = repository.findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID!")
            );
        
        repository.delete(entity);
    }

    /*
    private Person mockPerson(Long i) {
        
        Person person = new Person();
        person.setId(i);
        person.setFirstName("Person name "+i);
        person.setLastName("Last name "+i);
        person.setAddres("Address "+i);
        person.setGender("Gender "+i);

        return person;
    }
     */
    
}
