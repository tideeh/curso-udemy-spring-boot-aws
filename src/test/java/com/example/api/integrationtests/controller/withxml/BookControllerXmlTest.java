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
import com.example.api.integrationtests.util.mock.MockBook;
import com.example.api.integrationtests.util.vo.v1.security.AccountCredentialsVO;
import com.example.api.integrationtests.util.vo.wrappers.xmlyaml.WrapperXmlYamlBookVO;
import com.example.api.integrationtests.util.vo.v1.BookVO;
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
public class BookControllerXmlTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static XmlMapper xmlMapper;
	private static BookVO bookVO;

	@BeforeAll
	public static void setup() throws ParseException {
		xmlMapper = new XmlMapper();
		xmlMapper.findAndRegisterModules(); // registra LocalDateTime
		xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		bookVO = MockBook.mockVO();
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
			.setBasePath("/api/book/v1")
			.setPort(TestsConstants.SERVER_PORT)
			.build();
		
		if(TestsConstants.SHOW_LOG_DETAIL) {
			specification.filters(new RequestLoggingFilter(LogDetail.ALL));
			specification.filters(new ResponseLoggingFilter(LogDetail.ALL));
		}
	}

	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specification)
				.body(bookVO)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		BookVO persistedBookVO = xmlMapper.readValue(content, BookVO.class);
		bookVO = persistedBookVO;

		assertNotNull(persistedBookVO);
		assertNotNull(persistedBookVO.getId());
		assertNotNull(persistedBookVO.getTitle());
		assertNotNull(persistedBookVO.getAuthor());
		assertNotNull(persistedBookVO.getPrice());
		assertNotNull(persistedBookVO.getLaunchDate());

		assertTrue(persistedBookVO.getId() > 0);
		assertEquals("O Codigo Da Vinci", persistedBookVO.getTitle());
		assertEquals("Dan Brown", persistedBookVO.getAuthor());
		assertEquals(35.46, persistedBookVO.getPrice());
		assertTrue(persistedBookVO.getLaunchDate().isEqual(LocalDate.of(2021, 04, 15)));
	}

	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		bookVO.setTitle("O Codigo Da Vinci (Robert Langdon - Livro 2)");

		var content = 
			given()
				.spec(specification)
				.body(bookVO)
				.when()
					.put()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		BookVO persistedBookVO = xmlMapper.readValue(content, BookVO.class);

		assertNotNull(persistedBookVO);
		assertNotNull(persistedBookVO.getId());
		assertNotNull(persistedBookVO.getTitle());
		assertNotNull(persistedBookVO.getAuthor());
		assertNotNull(persistedBookVO.getPrice());
		assertNotNull(persistedBookVO.getLaunchDate());

		assertEquals(bookVO.getId(), persistedBookVO.getId());
		assertEquals("O Codigo Da Vinci (Robert Langdon - Livro 2)", persistedBookVO.getTitle());
		assertEquals("Dan Brown", persistedBookVO.getAuthor());
		assertEquals(35.46, persistedBookVO.getPrice());
		assertTrue(persistedBookVO.getLaunchDate().isEqual(LocalDate.of(2021, 04, 15)));
	}

	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specification)
				.pathParam("id", bookVO.getId())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		BookVO persistedBookVO = xmlMapper.readValue(content, BookVO.class);

		assertNotNull(persistedBookVO);
		assertNotNull(persistedBookVO.getId());
		assertNotNull(persistedBookVO.getTitle());
		assertNotNull(persistedBookVO.getAuthor());
		assertNotNull(persistedBookVO.getPrice());
		assertNotNull(persistedBookVO.getLaunchDate());

		assertEquals(bookVO.getId(), persistedBookVO.getId());
		assertEquals("O Codigo Da Vinci (Robert Langdon - Livro 2)", persistedBookVO.getTitle());
		assertEquals("Dan Brown", persistedBookVO.getAuthor());
		assertEquals(35.46, persistedBookVO.getPrice());
		assertTrue(persistedBookVO.getLaunchDate().isEqual(LocalDate.of(2021, 04, 15)));
	}

	@Test
	@Order(4)
	public void testDelete() throws JsonMappingException, JsonProcessingException {
		given()
			.spec(specification)
			.pathParam("id", bookVO.getId())
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

		WrapperXmlYamlBookVO wrapper = xmlMapper.readValue(content, WrapperXmlYamlBookVO.class);
		var listVO = wrapper.getContent();

		BookVO foundBookOne = listVO.get(0);

		assertNotNull(foundBookOne);
		assertNotNull(foundBookOne.getId());
		assertNotNull(foundBookOne.getTitle());
		assertNotNull(foundBookOne.getAuthor());
		assertNotNull(foundBookOne.getPrice());
		assertNotNull(foundBookOne.getLaunchDate());

		assertEquals(278, foundBookOne.getId());
		assertEquals("Fan, The", foundBookOne.getTitle());
		assertEquals("Robinette Ewings", foundBookOne.getAuthor());
		assertEquals(54.14, foundBookOne.getPrice());
		assertTrue(foundBookOne.getLaunchDate().isEqual(LocalDate.of(1975, 02, 28)));

		BookVO foundBookSix = listVO.get(5);

		assertNotNull(foundBookSix);
		assertNotNull(foundBookSix.getId());
		assertNotNull(foundBookSix.getTitle());
		assertNotNull(foundBookSix.getAuthor());
		assertNotNull(foundBookSix.getPrice());
		assertNotNull(foundBookSix.getLaunchDate());

		assertEquals(28, foundBookSix.getId());
		assertEquals("Ocean's Thirteen", foundBookSix.getTitle());
		assertEquals("Ricki Siddele", foundBookSix.getAuthor());
		assertEquals(42.54, foundBookSix.getPrice());
		assertTrue(foundBookSix.getLaunchDate().isEqual(LocalDate.of(2006, 02, 13)));
	}

	@Test
	@Order(6)
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
			.addHeader(TestsConstants.HEADER_PARAM_ACCEPT, TestsConstants.CONTENT_TYPE_XML)
			.addHeader(TestsConstants.HEADER_PARAM_CONTENT_TYPE, TestsConstants.CONTENT_TYPE_XML)
			.setBasePath("/api/book/v1")
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

		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/book/v1/278</href></links>"));

		assertTrue(content.contains("<links><rel>first</rel><href>http://localhost:8888/api/book/v1?direction=desc&amp;page=0&amp;size=10&amp;sort=author,desc</href></links>"));
		assertTrue(content.contains("<links><rel>prev</rel><href>http://localhost:8888/api/book/v1?direction=desc&amp;page=19&amp;size=10&amp;sort=author,desc</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/book/v1?page=20&amp;size=10&amp;direction=desc</href></links>"));
		assertTrue(content.contains("<links><rel>next</rel><href>http://localhost:8888/api/book/v1?direction=desc&amp;page=21&amp;size=10&amp;sort=author,desc</href></links>"));
		assertTrue(content.contains("<links><rel>last</rel><href>http://localhost:8888/api/book/v1?direction=desc&amp;page=101&amp;size=10&amp;sort=author,desc</href></links>"));

		assertTrue(content.contains("<page><size>10</size><totalElements>1015</totalElements><totalPages>102</totalPages><number>20</number></page>"));
	}

}
