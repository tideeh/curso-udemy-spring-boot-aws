package com.example.api.integrationtests.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import com.example.api.integrationtests.util.containers.AbstractIntegrationTest;
import com.example.api.model.Person;
import com.example.api.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    PersonRepository repository;

    private static Long id;
    
    @Test
	@Order(0)
	public void testFindByNameV2() throws JsonMappingException, JsonProcessingException {

        Pageable pageable = PageRequest.of(0, 6, Sort.by(Direction.ASC, "firstName"));
        var list = repository.findPersonsByName("ey", pageable).getContent();

        Person elementOne = list.get(0);

		assertNotNull(elementOne);
		assertNotNull(elementOne.getId());
		assertNotNull(elementOne.getFirstName());
		assertNotNull(elementOne.getLastName());
		assertNotNull(elementOne.getAddress());
		assertNotNull(elementOne.getGender());
		assertNotNull(elementOne.getBirthday());
		assertNotNull(elementOne.getEnabled());

		assertEquals(283, elementOne.getId());
		assertEquals("Ailey", elementOne.getFirstName());
		assertEquals("Higgoe", elementOne.getLastName());
		assertEquals("089 Dennis Place", elementOne.getAddress());
		assertEquals("Female", elementOne.getGender());
		assertTrue(elementOne.getBirthday().isEqual(LocalDate.of(1951, 04, 27)));
		assertFalse(elementOne.getEnabled());

		Person elementSix = list.get(5);

		assertNotNull(elementSix);
		assertNotNull(elementSix.getId());
		assertNotNull(elementSix.getFirstName());
		assertNotNull(elementSix.getLastName());
		assertNotNull(elementSix.getAddress());
		assertNotNull(elementSix.getGender());
		assertNotNull(elementSix.getBirthday());
		assertNotNull(elementSix.getEnabled());

		assertEquals(920, elementSix.getId());
		assertEquals("Davey", elementSix.getFirstName());
		assertEquals("Rucklidge", elementSix.getLastName());
		assertEquals("972 Kingsford Crossing", elementSix.getAddress());
		assertEquals("Male", elementSix.getGender());
		assertTrue(elementSix.getBirthday().isEqual(LocalDate.of(1964, 10, 13)));
		assertTrue(elementSix.getEnabled());

        id = elementSix.getId();
	}
    
    @Test
	@Order(1)
	public void testDisablePersonV2() throws JsonMappingException, JsonProcessingException {

        repository.disablePerson(id);

        Pageable pageable = PageRequest.of(0, 6, Sort.by(Direction.ASC, "firstName"));
        var list = repository.findPersonsByName("ey", pageable).getContent();

		Person elementSix = list.get(5);

		assertNotNull(elementSix);
		assertNotNull(elementSix.getId());
		assertNotNull(elementSix.getFirstName());
		assertNotNull(elementSix.getLastName());
		assertNotNull(elementSix.getAddress());
		assertNotNull(elementSix.getGender());
		assertNotNull(elementSix.getBirthday());
		assertNotNull(elementSix.getEnabled());

		assertEquals(920, elementSix.getId());
		assertEquals("Davey", elementSix.getFirstName());
		assertEquals("Rucklidge", elementSix.getLastName());
		assertEquals("972 Kingsford Crossing", elementSix.getAddress());
		assertEquals("Male", elementSix.getGender());
		assertTrue(elementSix.getBirthday().isEqual(LocalDate.of(1964, 10, 13)));
		assertFalse(elementSix.getEnabled());
	}
}
