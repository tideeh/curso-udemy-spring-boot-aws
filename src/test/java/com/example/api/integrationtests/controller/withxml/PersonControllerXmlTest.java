package com.example.api.integrationtests.controller.withxml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
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
import com.example.api.integrationtests.util.vo.v1.security.AccountCredentialsVO;
import com.example.api.integrationtests.util.vo.v1.PersonVO;
import com.example.api.integrationtests.util.vo.v2.PersonVOV2;
import com.example.api.integrationtests.util.vo.wrappers.xmlyaml.WrapperXmlYamlPersonVO;
import com.example.api.integrationtests.util.vo.wrappers.xmlyaml.WrapperXmlYamlPersonVOV2;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerXmlTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static RequestSpecification specificationV2;
	private static XmlMapper xmlMapper;
	private static PersonVO vo;
	private static PersonVOV2 voV2;

	@BeforeAll
	public static void setup() throws ParseException {
		xmlMapper = new XmlMapper();
		xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		vo = MockPerson.mockVO();
		voV2 = MockPerson.mockVOV2();
	}

	@Test
	@Order(0)
	public void authorization() {
		AccountCredentialsVO accountCredentials = new AccountCredentialsVO(TestsConstants.USERNAME_TEST, TestsConstants.PASSWORD_TEST);

		var accessToken = 
			given()
				.basePath("/auth/signin")
				.port(TestsConstants.SERVER_PORT)
				.accept(TestsConstants.CONTENT_TYPE_XML)
				.contentType(TestsConstants.CONTENT_TYPE_XML)
				.body(accountCredentials)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.path("TokenVO.accessToken");
		
		specification = new RequestSpecBuilder()
			.addHeader(TestsConstants.HEADER_PARAM_AUTHORIZATION, "Bearer "+accessToken)
			.addHeader(TestsConstants.HEADER_PARAM_ACCEPT, TestsConstants.CONTENT_TYPE_XML)
			.addHeader(TestsConstants.HEADER_PARAM_CONTENT_TYPE, TestsConstants.CONTENT_TYPE_XML)
			.setBasePath("/api/person/v1")
			.setPort(TestsConstants.SERVER_PORT)
			.build();

		specificationV2 = new RequestSpecBuilder()
			.addHeader(TestsConstants.HEADER_PARAM_AUTHORIZATION, "Bearer "+accessToken)
			.addHeader(TestsConstants.HEADER_PARAM_ACCEPT, TestsConstants.CONTENT_TYPE_XML)
			.addHeader(TestsConstants.HEADER_PARAM_CONTENT_TYPE, TestsConstants.CONTENT_TYPE_XML)
			.setBasePath("/api/person/v2")
			.setPort(TestsConstants.SERVER_PORT)
			.build();

		if(TestsConstants.SHOW_LOG_DETAIL) {
			specification.filters(new RequestLoggingFilter(LogDetail.ALL));
			specification.filters(new ResponseLoggingFilter(LogDetail.ALL));
			specificationV2.filters(new RequestLoggingFilter(LogDetail.ALL));
			specificationV2.filters(new ResponseLoggingFilter(LogDetail.ALL));
		}
	}

	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specification)
				.body(vo)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonVO persistedVO = xmlMapper.readValue(content, PersonVO.class);
		vo = persistedVO;

		assertNotNull(persistedVO);
		assertNotNull(persistedVO.getId());
		assertNotNull(persistedVO.getFirstName());
		assertNotNull(persistedVO.getLastName());
		assertNotNull(persistedVO.getAddres());
		assertNotNull(persistedVO.getGender());

		assertTrue(persistedVO.getId() > 0);
		assertEquals("Nelson", persistedVO.getFirstName());
		assertEquals("Piquet", persistedVO.getLastName());
		assertEquals("Brasilia - DF", persistedVO.getAddres());
		assertEquals("Male", persistedVO.getGender());
	}

	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		vo.setLastName("Piquet Souto Maior");

		var content = 
			given()
				.spec(specification)
				.body(vo)
				.when()
					.put()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonVO persistedVO = xmlMapper.readValue(content, PersonVO.class);

		assertNotNull(persistedVO);
		assertNotNull(persistedVO.getId());
		assertNotNull(persistedVO.getFirstName());
		assertNotNull(persistedVO.getLastName());
		assertNotNull(persistedVO.getAddres());
		assertNotNull(persistedVO.getGender());

		assertEquals(vo.getId(), persistedVO.getId());
		assertEquals("Nelson", persistedVO.getFirstName());
		assertEquals("Piquet Souto Maior", persistedVO.getLastName());
		assertEquals("Brasilia - DF", persistedVO.getAddres());
		assertEquals("Male", persistedVO.getGender());
	}

	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specification)
				.pathParam("id", vo.getId())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonVO persistedVO = xmlMapper.readValue(content, PersonVO.class);

		assertNotNull(persistedVO);
		assertNotNull(persistedVO.getId());
		assertNotNull(persistedVO.getFirstName());
		assertNotNull(persistedVO.getLastName());
		assertNotNull(persistedVO.getAddres());
		assertNotNull(persistedVO.getGender());

		assertEquals(vo.getId(), persistedVO.getId());
		assertEquals("Nelson", persistedVO.getFirstName());
		assertEquals("Piquet Souto Maior", persistedVO.getLastName());
		assertEquals("Brasilia - DF", persistedVO.getAddres());
		assertEquals("Male", persistedVO.getGender());
	}

	@Test
	@Order(4)
	public void testDelete() throws JsonMappingException, JsonProcessingException {
		given()
			.spec(specification)
			.pathParam("id", vo.getId())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}

	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specification)
				.queryParams("page", 20, "size", 10, "direction", "desc")
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		WrapperXmlYamlPersonVO wrapper = xmlMapper.readValue(content, WrapperXmlYamlPersonVO.class);
		var listVO = wrapper.getContent();

		PersonVO elementOne = listVO.get(0);

		assertNotNull(elementOne);
		assertNotNull(elementOne.getId());
		assertNotNull(elementOne.getFirstName());
		assertNotNull(elementOne.getLastName());
		assertNotNull(elementOne.getAddres());
		assertNotNull(elementOne.getGender());

		assertEquals(125, elementOne.getId());
		assertEquals("Rinaldo", elementOne.getFirstName());
		assertEquals("Chippindale", elementOne.getLastName());
		assertEquals("57 Magdeline Plaza", elementOne.getAddres());
		assertEquals("Male", elementOne.getGender());

		PersonVO elementSix = listVO.get(5);

		assertNotNull(elementSix);
		assertNotNull(elementSix.getId());
		assertNotNull(elementSix.getFirstName());
		assertNotNull(elementSix.getLastName());
		assertNotNull(elementSix.getAddres());
		assertNotNull(elementSix.getGender());

		assertEquals(928, elementSix.getId());
		assertEquals("Rhea", elementSix.getFirstName());
		assertEquals("Milham", elementSix.getLastName());
		assertEquals("420 Fair Oaks Road", elementSix.getAddres());
		assertEquals("Female", elementSix.getGender());
	}

	@Test
	@Order(6)
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
			.addHeader(TestsConstants.HEADER_PARAM_ACCEPT, TestsConstants.CONTENT_TYPE_XML)
			.addHeader(TestsConstants.HEADER_PARAM_CONTENT_TYPE, TestsConstants.CONTENT_TYPE_XML)
			.setBasePath("/api/person/v1")
			.setPort(TestsConstants.SERVER_PORT)
			.build();
		
		if(TestsConstants.SHOW_LOG_DETAIL) {
			specificationWithoutToken.filters(new RequestLoggingFilter(LogDetail.ALL));
			specificationWithoutToken.filters(new ResponseLoggingFilter(LogDetail.ALL));
		}
		
		given()
			.spec(specificationWithoutToken)
			.when()
				.get()
			.then()
				.statusCode(403);
	}

	@Test
	@Order(7)
	public void testCreateV2() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specificationV2)
				.body(voV2)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonVOV2 persistedVOV2 = xmlMapper.readValue(content, PersonVOV2.class);
		voV2 = persistedVOV2;

		assertNotNull(persistedVOV2);
		assertNotNull(persistedVOV2.getId());
		assertNotNull(persistedVOV2.getFirstName());
		assertNotNull(persistedVOV2.getLastName());
		assertNotNull(persistedVOV2.getAddress());
		assertNotNull(persistedVOV2.getGender());
		assertNotNull(persistedVOV2.getBirthday());
		assertNotNull(persistedVOV2.getEnabled());

		assertTrue(persistedVOV2.getId() > 0);
		assertEquals("Leonardo", persistedVOV2.getFirstName());
		assertEquals("Di Caprio", persistedVOV2.getLastName());
		assertEquals("Goiania - Goias", persistedVOV2.getAddress());
		assertEquals("Male", persistedVOV2.getGender());
		assertTrue(persistedVOV2.getBirthday().isEqual(LocalDate.of(1850, 06, 25)));
		assertTrue(persistedVOV2.getEnabled());
	}

	@Test
	@Order(8)
	public void testUpdateV2() throws JsonMappingException, JsonProcessingException {
		voV2.setLastName("Da Vinci");

		var content = 
			given()
				.spec(specificationV2)
				.body(voV2)
				.when()
					.put()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonVOV2 persistedVOV2 = xmlMapper.readValue(content, PersonVOV2.class);

		assertNotNull(persistedVOV2);
		assertNotNull(persistedVOV2.getId());
		assertNotNull(persistedVOV2.getFirstName());
		assertNotNull(persistedVOV2.getLastName());
		assertNotNull(persistedVOV2.getAddress());
		assertNotNull(persistedVOV2.getGender());
		assertNotNull(persistedVOV2.getBirthday());
		assertNotNull(persistedVOV2.getEnabled());

		assertTrue(persistedVOV2.getId() > 0);
		assertEquals("Leonardo", persistedVOV2.getFirstName());
		assertEquals("Da Vinci", persistedVOV2.getLastName());
		assertEquals("Goiania - Goias", persistedVOV2.getAddress());
		assertEquals("Male", persistedVOV2.getGender());
		assertTrue(persistedVOV2.getBirthday().isEqual(LocalDate.of(1850, 06, 25)));
		assertTrue(persistedVOV2.getEnabled());
	}

	@Test
	@Order(9)
	public void testDisablePersonV2() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specificationV2)
				.pathParam("id", voV2.getId())
				.when()
					.patch("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonVOV2 persistedVOV2 = xmlMapper.readValue(content, PersonVOV2.class);

		assertNotNull(persistedVOV2);
		assertNotNull(persistedVOV2.getId());
		assertNotNull(persistedVOV2.getFirstName());
		assertNotNull(persistedVOV2.getLastName());
		assertNotNull(persistedVOV2.getAddress());
		assertNotNull(persistedVOV2.getGender());
		assertNotNull(persistedVOV2.getBirthday());
		assertNotNull(persistedVOV2.getEnabled());

		assertTrue(persistedVOV2.getId() > 0);
		assertEquals("Leonardo", persistedVOV2.getFirstName());
		assertEquals("Da Vinci", persistedVOV2.getLastName());
		assertEquals("Goiania - Goias", persistedVOV2.getAddress());
		assertEquals("Male", persistedVOV2.getGender());
		assertTrue(persistedVOV2.getBirthday().isEqual(LocalDate.of(1850, 06, 25)));
		assertFalse(persistedVOV2.getEnabled());
	}

	@Test
	@Order(10)
	public void testFindByIdV2() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specificationV2)
				.pathParam("id", voV2.getId())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		PersonVOV2 persistedVOV2 = xmlMapper.readValue(content, PersonVOV2.class);

		assertNotNull(persistedVOV2);
		assertNotNull(persistedVOV2.getId());
		assertNotNull(persistedVOV2.getFirstName());
		assertNotNull(persistedVOV2.getLastName());
		assertNotNull(persistedVOV2.getAddress());
		assertNotNull(persistedVOV2.getGender());
		assertNotNull(persistedVOV2.getBirthday());
		assertNotNull(persistedVOV2.getEnabled());

		assertTrue(persistedVOV2.getId() > 0);
		assertEquals("Leonardo", persistedVOV2.getFirstName());
		assertEquals("Da Vinci", persistedVOV2.getLastName());
		assertEquals("Goiania - Goias", persistedVOV2.getAddress());
		assertEquals("Male", persistedVOV2.getGender());
		assertTrue(persistedVOV2.getBirthday().isEqual(LocalDate.of(1850, 06, 25)));
		assertFalse(persistedVOV2.getEnabled());
	}

	@Test
	@Order(11)
	public void testDeleteV2() throws JsonMappingException, JsonProcessingException {
		given()
			.spec(specificationV2)
			.pathParam("id", voV2.getId())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}

	@Test
	@Order(12)
	public void testFindAllV2() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specificationV2)
				.queryParams("page", 20, "size", 10, "direction", "desc")
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		WrapperXmlYamlPersonVOV2 wrapper = xmlMapper.readValue(content, WrapperXmlYamlPersonVOV2.class);
		var listVOV2 = wrapper.getContent();

		PersonVOV2 elementOne = listVOV2.get(0);

		assertNotNull(elementOne);
		assertNotNull(elementOne.getId());
		assertNotNull(elementOne.getFirstName());
		assertNotNull(elementOne.getLastName());
		assertNotNull(elementOne.getAddress());
		assertNotNull(elementOne.getGender());
		assertNotNull(elementOne.getBirthday());
		assertNotNull(elementOne.getEnabled());

		assertEquals(125, elementOne.getId());
		assertEquals("Rinaldo", elementOne.getFirstName());
		assertEquals("Chippindale", elementOne.getLastName());
		assertEquals("57 Magdeline Plaza", elementOne.getAddress());
		assertEquals("Male", elementOne.getGender());
		assertTrue(elementOne.getBirthday().isEqual(LocalDate.of(1989, 12, 31)));
		assertFalse(elementOne.getEnabled());

		PersonVOV2 elementSix = listVOV2.get(5);

		assertNotNull(elementSix);
		assertNotNull(elementSix.getId());
		assertNotNull(elementSix.getFirstName());
		assertNotNull(elementSix.getLastName());
		assertNotNull(elementSix.getAddress());
		assertNotNull(elementSix.getGender());
		assertNotNull(elementSix.getBirthday());
		assertNotNull(elementSix.getEnabled());

		assertEquals(928, elementSix.getId());
		assertEquals("Rhea", elementSix.getFirstName());
		assertEquals("Milham", elementSix.getLastName());
		assertEquals("420 Fair Oaks Road", elementSix.getAddress());
		assertEquals("Female", elementSix.getGender());
		assertTrue(elementSix.getBirthday().isEqual(LocalDate.of(1999, 01, 04)));
		assertFalse(elementSix.getEnabled());
	}

	@Test
	@Order(13)
	public void testFindByNameV2() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specificationV2)
				.pathParam("firstName", "ey")
				.queryParams("page", 0, "size", 6, "direction", "asc")
				.when()
					.get("search/{firstName}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		WrapperXmlYamlPersonVOV2 wrapper = xmlMapper.readValue(content, WrapperXmlYamlPersonVOV2.class);
		var listVOV2 = wrapper.getContent();

		PersonVOV2 elementOne = listVOV2.get(0);

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

		PersonVOV2 elementSix = listVOV2.get(5);

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
	}

	@Test
	@Order(14)
	public void testFindAllV2WithoutToken() throws JsonMappingException, JsonProcessingException {
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
			.addHeader(TestsConstants.HEADER_PARAM_ACCEPT, TestsConstants.CONTENT_TYPE_XML)
			.addHeader(TestsConstants.HEADER_PARAM_CONTENT_TYPE, TestsConstants.CONTENT_TYPE_XML)
			.setBasePath("/api/person/v2")
			.setPort(TestsConstants.SERVER_PORT)
			.build();

		if(TestsConstants.SHOW_LOG_DETAIL) {
			specificationWithoutToken.filters(new RequestLoggingFilter(LogDetail.ALL));
			specificationWithoutToken.filters(new ResponseLoggingFilter(LogDetail.ALL));
		}
		
		given()
			.spec(specificationWithoutToken)
			.when()
				.get()
			.then()
				.statusCode(403);
	}

	@Test
	@Order(15)
	public void testHateoas() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specification)
				.queryParams("page", 20, "size", 10, "direction", "desc")
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1/125</href></links>"));

		assertTrue(content.contains("<links><rel>first</rel><href>http://localhost:8888/api/person/v1?direction=desc&amp;page=0&amp;size=10&amp;sort=firstName,desc</href></links>"));
		assertTrue(content.contains("<links><rel>prev</rel><href>http://localhost:8888/api/person/v1?direction=desc&amp;page=19&amp;size=10&amp;sort=firstName,desc</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1?page=20&amp;size=10&amp;direction=desc</href></links>"));
		assertTrue(content.contains("<links><rel>next</rel><href>http://localhost:8888/api/person/v1?direction=desc&amp;page=21&amp;size=10&amp;sort=firstName,desc</href></links>"));
		assertTrue(content.contains("<links><rel>last</rel><href>http://localhost:8888/api/person/v1?direction=desc&amp;page=100&amp;size=10&amp;sort=firstName,desc</href></links>"));

		assertTrue(content.contains("<page><size>10</size><totalElements>1007</totalElements><totalPages>101</totalPages><number>20</number></page>"));
	}

}
