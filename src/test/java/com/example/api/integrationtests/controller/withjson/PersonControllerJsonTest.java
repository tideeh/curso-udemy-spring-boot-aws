package com.example.api.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.api.config.TestConfig;
import com.example.api.integrationtests.testcontainers.AbstractIntegrationTest;
import com.example.api.integrationtests.vo.PersonVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;

	private static PersonVO personVO;

	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		personVO = new PersonVO();
	}

	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();

		specification = new RequestSpecBuilder()
			.addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_GOOGLE)
			.setBasePath("/api/person/v1")
			.setPort(TestConfig.SERVER_PORT)
			.addFilter(new RequestLoggingFilter(LogDetail.ALL))
			.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();

		var content = 
			given()
				.spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
				.body(personVO)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonVO persistedPersonVO = objectMapper.readValue(content, PersonVO.class);
		personVO = persistedPersonVO;

		assertNotNull(persistedPersonVO);
		assertNotNull(persistedPersonVO.getId());
		assertNotNull(persistedPersonVO.getFirstName());
		assertNotNull(persistedPersonVO.getLastName());
		assertNotNull(persistedPersonVO.getAddres());
		assertNotNull(persistedPersonVO.getGender());

		assertTrue(persistedPersonVO.getId() > 0);
		
		assertEquals("Richard", persistedPersonVO.getFirstName());
		assertEquals("Stallman", persistedPersonVO.getLastName());
		assertEquals("New York City, New York, US", persistedPersonVO.getAddres());
		assertEquals("Male", persistedPersonVO.getGender());
	}

	@Test
	@Order(2)
	public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockPerson();

		specification = new RequestSpecBuilder()
			.addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_APPLE)
			.setBasePath("/api/person/v1")
			.setPort(TestConfig.SERVER_PORT)
			.addFilter(new RequestLoggingFilter(LogDetail.ALL))
			.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();

		var content = 
			given()
				.spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
				.body(personVO)
				.when()
					.post()
				.then()
					.statusCode(403)
				.extract()
					.body()
						.asString();

		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}

	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();

		specification = new RequestSpecBuilder()
			.addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_GOOGLE)
			.setBasePath("/api/person/v1")
			.setPort(TestConfig.SERVER_PORT)
			.addFilter(new RequestLoggingFilter(LogDetail.ALL))
			.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();

		var content = 
			given()
				.spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
				.pathParam("id", personVO.getId())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonVO persistedPersonVO = objectMapper.readValue(content, PersonVO.class);
		personVO = persistedPersonVO;

		assertNotNull(persistedPersonVO);
		assertNotNull(persistedPersonVO.getId());
		assertNotNull(persistedPersonVO.getFirstName());
		assertNotNull(persistedPersonVO.getLastName());
		assertNotNull(persistedPersonVO.getAddres());
		assertNotNull(persistedPersonVO.getGender());

		assertTrue(persistedPersonVO.getId() > 0);
		
		assertEquals("Richard", persistedPersonVO.getFirstName());
		assertEquals("Stallman", persistedPersonVO.getLastName());
		assertEquals("New York City, New York, US", persistedPersonVO.getAddres());
		assertEquals("Male", persistedPersonVO.getGender());
	}

	@Test
	@Order(4)
	public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockPerson();

		specification = new RequestSpecBuilder()
			.addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_APPLE)
			.setBasePath("/api/person/v1")
			.setPort(TestConfig.SERVER_PORT)
			.addFilter(new RequestLoggingFilter(LogDetail.ALL))
			.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();

		var content = 
			given()
				.spec(specification)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
				.pathParam("id", personVO.getId())
				.when()
					.get("{id}")
				.then()
					.statusCode(403)
				.extract()
					.body()
						.asString();
		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}

	private void mockPerson() {
		personVO.setFirstName("Richard");
		personVO.setLastName("Stallman");
		personVO.setAddres("New York City, New York, US");
		personVO.setGender("Male");
	}
}
