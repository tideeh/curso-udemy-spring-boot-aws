package com.example.api.integrationtests.controller.cors.withjson;

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
import com.example.api.integrationtests.util.vo.v1.BookVO;
import com.example.api.integrationtests.util.vo.v1.security.TokenVO;
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
public class BookControllerCorsJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	private static BookVO bookVO;

	@BeforeAll
	public static void setup() throws ParseException {
		objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules(); // registra LocalDateTime
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
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
			.addHeader(TestsConstants.HEADER_PARAM_ACCEPT, TestsConstants.CONTENT_TYPE_JSON)
			.addHeader(TestsConstants.HEADER_PARAM_CONTENT_TYPE, TestsConstants.CONTENT_TYPE_JSON)
			.setBasePath("/api/book/v1")
			.setPort(TestsConstants.SERVER_PORT)
			.addFilter(new RequestLoggingFilter(LogDetail.ALL))
			.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
	}

	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException, ParseException {
		var content = 
			given()
				.spec(specification)
				.header(TestsConstants.HEADER_PARAM_ORIGIN, TestsConstants.ORIGIN_GOOGLE)
				.body(bookVO)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		BookVO persistedBookVO = objectMapper.readValue(content, BookVO.class);
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
	public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException, ParseException {
		var content = 
			given()
				.spec(specification)
				.header(TestsConstants.HEADER_PARAM_ORIGIN, TestsConstants.ORIGIN_APPLE)
				.body(bookVO)
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
	public void testFindById() throws JsonMappingException, JsonProcessingException, ParseException {
		var content = 
			given()
				.spec(specification)
				.header(TestsConstants.HEADER_PARAM_ORIGIN, TestsConstants.ORIGIN_GOOGLE)
				.pathParam("id", bookVO.getId())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		BookVO persistedBookVO = objectMapper.readValue(content, BookVO.class);
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
	@Order(4)
	public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.spec(specification)
				.header(TestsConstants.HEADER_PARAM_ORIGIN, TestsConstants.ORIGIN_APPLE)
				.pathParam("id", bookVO.getId())
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
	public void testDelete() throws JsonMappingException, JsonProcessingException {
		given()
			.spec(specification)
			.header(TestsConstants.HEADER_PARAM_ORIGIN, TestsConstants.ORIGIN_GOOGLE)
			.pathParam("id", bookVO.getId())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}

}
