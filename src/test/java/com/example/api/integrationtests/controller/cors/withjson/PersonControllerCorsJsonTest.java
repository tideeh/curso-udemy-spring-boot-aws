package com.example.api.integrationtests.controller.cors.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.api.config.TestsConstants;
import com.example.api.integrationtests.util.containers.AbstractIntegrationTest;
import com.example.api.integrationtests.util.mock.MockPerson;
import com.example.api.integrationtests.util.vo.v1.AccountCredentialsVO;
import com.example.api.integrationtests.util.vo.v1.PersonVO;
import com.example.api.integrationtests.util.vo.v1.TokenVO;
import com.example.api.integrationtests.util.vo.v2.PersonVOV2;
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
public class PersonControllerCorsJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static RequestSpecification specificationV2;
	private static ObjectMapper objectMapper;
	private static PersonVO personVO;
	private static PersonVOV2 personVOV2;

	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules(); // registra LocalDateTime
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		personVO = MockPerson.mockVO();
		personVOV2 = MockPerson.mockVOV2();
	}

	@Test
	@Order(0)
	public void authorization() {
		AccountCredentialsVO accountCredentials = new AccountCredentialsVO("dilores", "102030");

		var accessToken = 
			given()
				.basePath("/auth/signin")
				.port(TestsConstants.SERVER_PORT)
				.accept(TestsConstants.CONTENT_TYPE_JSON)
				.contentType(TestsConstants.CONTENT_TYPE_JSON)
				.body(accountCredentials)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(TokenVO.class)
					.getAccessToken();
		
		specification = new RequestSpecBuilder()
			.addHeader(TestsConstants.HEADER_PARAM_AUTHORIZATION, "Bearer "+accessToken)
			.setBasePath("/api/person/v1")
			.setPort(TestsConstants.SERVER_PORT)
			.addFilter(new RequestLoggingFilter(LogDetail.ALL))
			.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
		
		specificationV2 = new RequestSpecBuilder()
			.addHeader(TestsConstants.HEADER_PARAM_AUTHORIZATION, "Bearer "+accessToken)
			.setBasePath("/api/person/v2")
			.setPort(TestsConstants.SERVER_PORT)
			.addFilter(new RequestLoggingFilter(LogDetail.ALL))
			.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
	}

	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specification)
				.accept(TestsConstants.CONTENT_TYPE_JSON)
				.contentType(TestsConstants.CONTENT_TYPE_JSON)
				.header(TestsConstants.HEADER_PARAM_ORIGIN, TestsConstants.ORIGIN_GOOGLE)
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
		
		assertEquals("Nelson", persistedPersonVO.getFirstName());
		assertEquals("Piquet", persistedPersonVO.getLastName());
		assertEquals("Brasilia - DF", persistedPersonVO.getAddres());
		assertEquals("Male", persistedPersonVO.getGender());
	}

	@Test
	@Order(2)
	public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specification)
				.accept(TestsConstants.CONTENT_TYPE_JSON)
				.contentType(TestsConstants.CONTENT_TYPE_JSON)
				.header(TestsConstants.HEADER_PARAM_ORIGIN, TestsConstants.ORIGIN_APPLE)
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
		var content = 
			given()
				.spec(specification)
				.accept(TestsConstants.CONTENT_TYPE_JSON)
				.contentType(TestsConstants.CONTENT_TYPE_JSON)
				.header(TestsConstants.HEADER_PARAM_ORIGIN, TestsConstants.ORIGIN_GOOGLE)
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
		
		assertEquals("Nelson", persistedPersonVO.getFirstName());
		assertEquals("Piquet", persistedPersonVO.getLastName());
		assertEquals("Brasilia - DF", persistedPersonVO.getAddres());
		assertEquals("Male", persistedPersonVO.getGender());
	}

	@Test
	@Order(4)
	public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specification)
				.accept(TestsConstants.CONTENT_TYPE_JSON)
				.contentType(TestsConstants.CONTENT_TYPE_JSON)
				.header(TestsConstants.HEADER_PARAM_ORIGIN, TestsConstants.ORIGIN_APPLE)
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

	@Test
	@Order(5)
	public void testCreateV2() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specificationV2)
				.accept(TestsConstants.CONTENT_TYPE_JSON)
				.contentType(TestsConstants.CONTENT_TYPE_JSON)
				.header(TestsConstants.HEADER_PARAM_ORIGIN, TestsConstants.ORIGIN_GOOGLE)
				.body(personVOV2)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonVOV2 persistedPersonVOV2 = objectMapper.readValue(content, PersonVOV2.class);
		personVOV2 = persistedPersonVOV2;

		assertNotNull(persistedPersonVOV2);
		assertNotNull(persistedPersonVOV2.getId());
		assertNotNull(persistedPersonVOV2.getFirstName());
		assertNotNull(persistedPersonVOV2.getLastName());
		assertNotNull(persistedPersonVOV2.getAddress());
		assertNotNull(persistedPersonVOV2.getGender());
		assertNotNull(persistedPersonVOV2.getBirthday());

		assertTrue(persistedPersonVOV2.getId() > 0);
		
		assertEquals("Leonardo", persistedPersonVOV2.getFirstName());
		assertEquals("Di Caprio", persistedPersonVOV2.getLastName());
		assertEquals("Goiania - Goias", persistedPersonVOV2.getAddress());
		assertEquals("Male", persistedPersonVOV2.getGender());
		assertTrue(persistedPersonVOV2.getBirthday().isEqual(LocalDate.of(1850, 06, 25)));
	}
	
	@Test
	@Order(6)
	public void testCreateV2WithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specificationV2)
				.accept(TestsConstants.CONTENT_TYPE_JSON)
				.contentType(TestsConstants.CONTENT_TYPE_JSON)
				.header(TestsConstants.HEADER_PARAM_ORIGIN, TestsConstants.ORIGIN_APPLE)
				.body(personVOV2)
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
	@Order(7)
	public void testFindByIdV2() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specificationV2)
				.accept(TestsConstants.CONTENT_TYPE_JSON)
				.contentType(TestsConstants.CONTENT_TYPE_JSON)
				.header(TestsConstants.HEADER_PARAM_ORIGIN, TestsConstants.ORIGIN_GOOGLE)
				.pathParam("id", personVOV2.getId())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonVOV2 persistedPersonVOV2 = objectMapper.readValue(content, PersonVOV2.class);
		personVOV2 = persistedPersonVOV2;

		assertNotNull(persistedPersonVOV2);
		assertNotNull(persistedPersonVOV2.getId());
		assertNotNull(persistedPersonVOV2.getFirstName());
		assertNotNull(persistedPersonVOV2.getLastName());
		assertNotNull(persistedPersonVOV2.getAddress());
		assertNotNull(persistedPersonVOV2.getGender());

		assertTrue(persistedPersonVOV2.getId() > 0);
		
		assertEquals("Leonardo", persistedPersonVOV2.getFirstName());
		assertEquals("Di Caprio", persistedPersonVOV2.getLastName());
		assertEquals("Goiania - Goias", persistedPersonVOV2.getAddress());
		assertEquals("Male", persistedPersonVOV2.getGender());
		assertTrue(persistedPersonVOV2.getBirthday().isEqual(LocalDate.of(1850, 06, 25)));
	}
	
	@Test
	@Order(8)
	public void testFindByIdV2WithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specificationV2)
				.accept(TestsConstants.CONTENT_TYPE_JSON)
				.contentType(TestsConstants.CONTENT_TYPE_JSON)
				.header(TestsConstants.HEADER_PARAM_ORIGIN, TestsConstants.ORIGIN_APPLE)
				.pathParam("id", personVOV2.getId())
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

}
