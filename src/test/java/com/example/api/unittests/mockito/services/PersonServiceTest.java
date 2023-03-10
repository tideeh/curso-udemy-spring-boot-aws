package com.example.api.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.api.util.exception.RequiredObjectIsNullException;
import com.example.api.util.vo.v1.PersonVO;
import com.example.api.util.vo.v2.PersonVOV2;
import com.example.api.model.Person;
import com.example.api.repository.PersonRepository;
import com.example.api.service.PersonService;
import com.example.api.unittests.util.mock.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

	MockPerson input;
	
	@InjectMocks
	private PersonService service;
	
	@Mock
	PersonRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() throws Exception {
		Person entity = input.mockEntity(1); 
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getAddres());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}
	
	@Test
	void testCreate() throws Exception {
		Person entity = input.mockEntity(1); 
		entity.setId(1L);
		
		Person persisted = entity;
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setId(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.create(vo);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getAddres());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}
	
	@Test
	void testCreateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdate() throws Exception {
		Person entity = input.mockEntity(1); 
		
		Person persisted = entity;
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setId(1L);
		

		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.update(vo);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test1", result.getAddres());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}
	
	@Test
	void testUpdateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testDelete() {
		Person entity = input.mockEntity(1); 
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}

	@Test
	void testFindByIdV2() throws Exception {
		Person entity = input.mockEntityV2(1); 
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findByIdV2(1L);

		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());

		assertTrue(result.toString().contains("links: [</api/person/v2/1>;rel=\"self\"]"));
		assertEquals("Address Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
		assertTrue(result.getBirthday().isEqual(LocalDate.of(2001, 01, 25)));
		assertFalse(result.getEnabled());
	}
	
	@Test
	void testCreateV2() throws Exception {
		Person entity = input.mockEntityV2(1); 
		entity.setId(1L);
		
		Person persisted = entity;
		persisted.setId(1L);
		
		PersonVOV2 vo = input.mockVOV2(1);
		vo.setId(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.createV2(vo);

		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());

		assertTrue(result.toString().contains("links: [</api/person/v2/1>;rel=\"self\"]"));
		assertEquals("Address Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
		assertTrue(result.getBirthday().isEqual(LocalDate.of(2001, 01, 25)));
		assertFalse(result.getEnabled());
	}
	
	@Test
	void testCreateWithNullPersonV2() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.createV2(null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testUpdateV2() throws Exception {
		Person entity = input.mockEntityV2(1); 
		
		Person persisted = entity;
		persisted.setId(1L);
		persisted.setFirstName("First Name Test1 updated");
		
		PersonVOV2 vo = input.mockVOV2(1);
		vo.setId(1L);
		vo.setFirstName("First Name Test1 updated");

		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.updateV2(vo);

		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());

		assertTrue(result.toString().contains("links: [</api/person/v2/1>;rel=\"self\"]"));
		assertEquals("Address Test1", result.getAddress());
		assertEquals("First Name Test1 updated", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
		assertTrue(result.getBirthday().isEqual(LocalDate.of(2001, 01, 25)));
		assertFalse(result.getEnabled());
	}
	
	@Test
	void testUpdateWithNullPersonV2() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.updateV2(null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testDisableV2() throws Exception {
		Person entity = input.mockEntityV2(0);
		entity.setEnabled(true);
		
		Person persisted = entity;
		persisted.setEnabled(false);

		doNothing().when(repository).disablePerson(0L);
		when(repository.findById(0L)).thenReturn(Optional.of(persisted));

		var result = service.disablePersonV2(0L);

		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());

		assertTrue(result.toString().contains("links: [</api/person/v2/0>;rel=\"self\"]"));
		assertEquals("Address Test0", result.getAddress());
		assertEquals("First Name Test0", result.getFirstName());
		assertEquals("Last Name Test0", result.getLastName());
		assertEquals("Male", result.getGender());
		assertTrue(result.getBirthday().isEqual(LocalDate.of(2000, 01, 25)));
		assertFalse(result.getEnabled());
	}

}
