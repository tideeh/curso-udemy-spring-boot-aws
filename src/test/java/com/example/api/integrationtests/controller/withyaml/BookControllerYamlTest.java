package com.example.api.integrationtests.controller.withyaml;

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
import com.example.api.integrationtests.util.vo.wrappers.xmlyaml.WrapperXmlYamlBookVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class BookControllerYamlTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static BookVO bookVO;
	private static ObjectMapper ymlMapper;

	@BeforeAll
	public static void setup() throws ParseException {
		ymlMapper = new ObjectMapper(new YAMLFactory());
		ymlMapper.findAndRegisterModules(); // registra LocalDateTime
		ymlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		ymlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		bookVO = MockBook.mockVO();
	}

	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO accountCredentials = new AccountCredentialsVO(TestsConstants.USERNAME_TEST, TestsConstants.PASSWORD_TEST);

		var content = 
			given()
				.config(RestAssuredConfig.config()
					.encoderConfig(EncoderConfig.encoderConfig()
						.encodeContentTypeAs(TestsConstants.CONTENT_TYPE_YML, ContentType.TEXT)))
				.basePath("/auth/signin")
				.port(TestsConstants.SERVER_PORT)
				.accept(TestsConstants.CONTENT_TYPE_YML)
				.contentType(TestsConstants.CONTENT_TYPE_YML)
				.body(ymlMapper.writeValueAsString(accountCredentials))
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		TokenVO tokenVO = ymlMapper.readValue(content, TokenVO.class);

		specification = new RequestSpecBuilder()
			.addHeader(TestsConstants.HEADER_PARAM_AUTHORIZATION, "Bearer "+tokenVO.getAccessToken())
			.addHeader(TestsConstants.HEADER_PARAM_ACCEPT, TestsConstants.CONTENT_TYPE_YML)
			.addHeader(TestsConstants.HEADER_PARAM_CONTENT_TYPE, TestsConstants.CONTENT_TYPE_YML)
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
				.config(RestAssuredConfig.config()
					.encoderConfig(EncoderConfig.encoderConfig()
						.encodeContentTypeAs(TestsConstants.CONTENT_TYPE_YML, ContentType.TEXT)))
				.spec(specification)
				.body(ymlMapper.writeValueAsString(bookVO))
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		BookVO persistedBookVO = ymlMapper.readValue(content, BookVO.class);
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
				.config(RestAssuredConfig.config()
					.encoderConfig(EncoderConfig.encoderConfig()
						.encodeContentTypeAs(TestsConstants.CONTENT_TYPE_YML, ContentType.TEXT)))
				.spec(specification)
				.body(ymlMapper.writeValueAsString(bookVO))
				.when()
					.put()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		BookVO persistedBookVO = ymlMapper.readValue(content, BookVO.class);

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

		BookVO persistedBookVO = ymlMapper.readValue(content, BookVO.class);

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
		
		WrapperXmlYamlBookVO wrapper = ymlMapper.readValue(content, WrapperXmlYamlBookVO.class);
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
			.addHeader(TestsConstants.HEADER_PARAM_ACCEPT, TestsConstants.CONTENT_TYPE_YML)
			.addHeader(TestsConstants.HEADER_PARAM_CONTENT_TYPE, TestsConstants.CONTENT_TYPE_YML)
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
		var unthreatedContent =
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
		
		var content = unthreatedContent.replace("\r", "").replace("\n", "");
		
		assertTrue(content.contains("  links:  - rel: \"self\"    href: \"http://localhost:8888/api/book/v1/278\""));

		assertTrue(content.contains("- rel: \"first\"  href: \"http://localhost:8888/api/book/v1?direction=desc&page=0&size=10&sort=author,desc\""));
		assertTrue(content.contains("- rel: \"prev\"  href: \"http://localhost:8888/api/book/v1?direction=desc&page=19&size=10&sort=author,desc\""));
		assertTrue(content.contains("- rel: \"self\"  href: \"http://localhost:8888/api/book/v1?page=20&size=10&direction=desc\""));
		assertTrue(content.contains("- rel: \"next\"  href: \"http://localhost:8888/api/book/v1?direction=desc&page=21&size=10&sort=author,desc\""));
		assertTrue(content.contains("- rel: \"last\"  href: \"http://localhost:8888/api/book/v1?direction=desc&page=101&size=10&sort=author,desc\""));

		assertTrue(content.contains("page:  size: 10  totalElements: 1015  totalPages: 102  number: 20"));
	}

}
