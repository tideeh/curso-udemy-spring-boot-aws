package com.example.api.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.exceptions.ResourceNotFoundException;
import com.example.api.model.Person;
import com.example.api.repositories.PersonRepository;

@Service
public class PersonServices {

    @Autowired
    PersonRepository repository;

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public Person findById(Long id) {
        
        logger.info("Find Person by ID");;

        return repository.findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID!")
            );
    }

    public List<Person> findAll() {

        logger.info("Find all Persons");

        return repository.findAll();
    }

    public Person create(Person person) {

        logger.info("Create one person");

        return repository.save(person);
    }

    public Person update(Person person) {

        logger.info("Update the person");

        var entity = repository.findById(person.getId())
            .orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID!")
            );
        
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddres(person.getAddres());
        entity.setGender(person.getGender());

        return repository.save(entity);
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
