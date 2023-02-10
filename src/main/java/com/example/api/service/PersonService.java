package com.example.api.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.api.controller.PersonController;
import com.example.api.repository.PersonRepository;
import com.example.api.util.exception.InvalidObjectPropertyException;
import com.example.api.util.exception.RequiredObjectIsNullException;
import com.example.api.util.exception.ResourceNotFoundException;
import com.example.api.util.mapper.PersonMapper;
import com.example.api.util.vo.v1.PersonVO;
import com.example.api.util.vo.v2.PersonVOV2;

import jakarta.transaction.Transactional;

@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public PersonVO findById(Long id) throws Exception {
        logger.info("Find Person by ID - V1");

        var entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        
        PersonVO vo = PersonMapper.INSTANCE.personToVO(entity);

        // Hateoas
        Link link = linkTo(methodOn(PersonController.class).findById(id)).withSelfRel();
        vo.add(link);

        return vo;
    }

    public PersonVOV2 findByIdV2(Long id) throws Exception {
        logger.info("Find Person by ID - V2");;

        var entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        
        PersonVOV2 vo = PersonMapper.INSTANCE.personToVOV2(entity);
        
        // Hateoas
        Link link = linkTo(methodOn(PersonController.class).findByIdV2(id)).withSelfRel();
        vo.add(link);
        
        return vo;
    }

    public List<PersonVO> findAll() throws Exception {
        logger.info("Find all Persons - V1");

        List<PersonVO> voList = PersonMapper.INSTANCE.personListToVOList(repository.findAll());

        // Hateoas
        for (PersonVO vo : voList) {
            Link link = linkTo(methodOn(PersonController.class).findById(vo.getId())).withSelfRel();
            vo.add(link);
        }

        return voList;
    }

    public List<PersonVOV2> findAllV2() throws Exception {
        logger.info("Find all Persons - V2");

        List<PersonVOV2> voList = PersonMapper.INSTANCE.personListToVOV2List(repository.findAll());

        // Hateoas
        for (PersonVOV2 vo : voList) {
            Link link = linkTo(methodOn(PersonController.class).findByIdV2(vo.getId())).withSelfRel();
            vo.add(link);
        }

        return voList;
    }

    public PersonVO create(PersonVO personVO) throws Exception {
        logger.info("Create one person - V1");

        if(personVO == null)
            throw new RequiredObjectIsNullException();
        
        if(personVO.getGender().length() > 6)
            throw new InvalidObjectPropertyException("Property <gender> length over max");

        var entity = PersonMapper.INSTANCE.VOToPerson(personVO);
        var vo = PersonMapper.INSTANCE.personToVO(repository.save(entity));

        // Hateoas
        Link link = linkTo(methodOn(PersonController.class).findById(vo.getId())).withSelfRel();
        vo.add(link);

        return vo;
    }

    public PersonVOV2 createV2(PersonVOV2 personVOV2) throws Exception {
        logger.info("Create one person - V2");

        if(personVOV2 == null)
            throw new RequiredObjectIsNullException();

        var entity = PersonMapper.INSTANCE.VOV2ToPerson(personVOV2);
        var vo = PersonMapper.INSTANCE.personToVOV2(repository.save(entity));

        // Hateoas
        Link link = linkTo(methodOn(PersonController.class).findByIdV2(vo.getId())).withSelfRel();
        vo.add(link);

        return vo;
    }

    public PersonVO update(PersonVO personVO) throws Exception {
        logger.info("Update the person - V1");

        if(personVO == null)
            throw new RequiredObjectIsNullException();

        var entity = repository.findById(personVO.getId())
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        
        entity.setFirstName(personVO.getFirstName());
        entity.setLastName(personVO.getLastName());
        entity.setAddress(personVO.getAddres());
        entity.setGender(personVO.getGender());

        var vo = PersonMapper.INSTANCE.personToVO(repository.save(entity));

        // Hateoas
        Link link = linkTo(methodOn(PersonController.class).findById(vo.getId())).withSelfRel();
        vo.add(link);

        return vo;
    }

    public PersonVOV2 updateV2(PersonVOV2 personVOV2) throws Exception {
        logger.info("Update the person - V2");

        if(personVOV2 == null)
            throw new RequiredObjectIsNullException();

        var entity = repository.findById(personVOV2.getId())
            .orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID!")
            );
        
        entity.setFirstName(personVOV2.getFirstName());
        entity.setLastName(personVOV2.getLastName());
        entity.setAddress(personVOV2.getAddress());
        entity.setGender(personVOV2.getGender());
        entity.setBirthday(personVOV2.getBirthday());

        var vo = PersonMapper.INSTANCE.personToVOV2(repository.save(entity));

        // Hateoas
        Link link = linkTo(methodOn(PersonController.class).findByIdV2(vo.getId())).withSelfRel();
        vo.add(link);

        return vo;
    }

    public void delete(Long id) {
        logger.info("Delete the person");

        var entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        
        repository.delete(entity);
    }

    @Transactional // como a alteração no banco foi criada por nós, precisamos garantir o ACID com essa annotation
    public PersonVOV2 disablePersonV2(Long id) throws Exception {
        logger.info("Disabling the person");

        repository.disablePerson(id);

        var entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        
        PersonVOV2 vo = PersonMapper.INSTANCE.personToVOV2(entity);
        
        // Hateoas
        Link link = linkTo(methodOn(PersonController.class).findByIdV2(id)).withSelfRel();
        vo.add(link);
        
        return vo;
    }
    
}
