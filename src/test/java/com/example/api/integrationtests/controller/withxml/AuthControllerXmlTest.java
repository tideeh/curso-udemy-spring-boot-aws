package com.example.api.integrationtests.controller.withxml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.api.config.TestsConstants;
import com.example.api.integrationtests.util.containers.AbstractIntegrationTest;
import com.example.api.integrationtests.util.vo.v1.AccountCredentialsVO;
import com.example.api.integrationtests.util.vo.v1.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerXmlTest extends AbstractIntegrationTest {

    private static TokenVO tokenVO;
	private static XmlMapper xmlMapper;

	@BeforeAll
	public static void setup() {
		xmlMapper = new XmlMapper();
		xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

    @Test
	@Order(1)
	public void testSignin() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO accountCredentials = new AccountCredentialsVO("dilores", "102030");

		var content = 
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
						.asString();
		
		tokenVO = xmlMapper.readValue(content, TokenVO.class);
		
		assertNotNull(tokenVO.getAccessToken());
		assertNotNull(tokenVO.getRefreshToken());
	}

    @Test
	@Order(2)
	public void testRefreshToken() throws JsonMappingException, JsonProcessingException {
		var content = 
			given()
				.basePath("/auth/refresh")
				.port(TestsConstants.SERVER_PORT)
				.accept(TestsConstants.CONTENT_TYPE_XML)
				.contentType(TestsConstants.CONTENT_TYPE_XML)
				.pathParam("username", tokenVO.getUsername())
                .header(TestsConstants.HEADER_PARAM_AUTHORIZATION, "Bearer "+tokenVO.getRefreshToken())
				.when()
					.put("{username}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		TokenVO newTokenVO = xmlMapper.readValue(content, TokenVO.class);
		
		assertNotNull(newTokenVO.getAccessToken());
		assertNotNull(newTokenVO.getRefreshToken());
	}
    
}
