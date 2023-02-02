package com.example.api.integrationtests.controller.withyaml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.api.config.TestsConstants;
import com.example.api.integrationtests.controller.withyaml.mapper.YMLMapper;
import com.example.api.integrationtests.util.containers.AbstractIntegrationTest;
import com.example.api.integrationtests.util.vo.v1.AccountCredentialsVO;
import com.example.api.integrationtests.util.vo.v1.TokenVO;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerYamlTest extends AbstractIntegrationTest {

	private static YMLMapper ymlMapper;
    private static TokenVO tokenVO;

	@BeforeAll
	public static void setup() {
		ymlMapper = new YMLMapper();
	}

    @Test
	@Order(1)
	public void testSignin() {
		AccountCredentialsVO accountCredentials = new AccountCredentialsVO("dilores", "102030");

		RequestSpecification specification = new RequestSpecBuilder()
			.addFilter(new RequestLoggingFilter(LogDetail.ALL))
			.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();

		tokenVO = 
			given().spec(specification)
				.config(RestAssuredConfig.config()
					.encoderConfig(EncoderConfig.encoderConfig()
						.encodeContentTypeAs(TestsConstants.CONTENT_TYPE_YML, ContentType.TEXT)))
				.basePath("/auth/signin")
				.port(TestsConstants.SERVER_PORT)
				.contentType(TestsConstants.CONTENT_TYPE_YML)
				.accept(TestsConstants.CONTENT_TYPE_YML)
				.body(accountCredentials, ymlMapper)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(TokenVO.class, ymlMapper);
		
		assertNotNull(tokenVO.getAccessToken());
		assertNotNull(tokenVO.getRefreshToken());
	}

    @Test
	@Order(2)
	public void testRefreshToken() {
		var newTokenVO = 
			given()
				.config(RestAssuredConfig.config()
					.encoderConfig(EncoderConfig.encoderConfig()
						.encodeContentTypeAs(TestsConstants.CONTENT_TYPE_YML, ContentType.TEXT)))
				.basePath("/auth/refresh")
				.port(TestsConstants.SERVER_PORT)
				.contentType(TestsConstants.CONTENT_TYPE_YML)
				.accept(TestsConstants.CONTENT_TYPE_YML)
				.pathParam("username", tokenVO.getUsername())
                .header(TestsConstants.HEADER_PARAM_AUTHORIZATION, "Bearer "+tokenVO.getRefreshToken())
				.when()
					.put("{username}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(TokenVO.class, ymlMapper);
		
		assertNotNull(newTokenVO.getAccessToken());
		assertNotNull(newTokenVO.getRefreshToken());
	}
    
}
