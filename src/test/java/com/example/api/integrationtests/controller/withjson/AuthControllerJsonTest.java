package com.example.api.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.api.config.TestsConstants;
import com.example.api.integrationtests.util.containers.AbstractIntegrationTest;
import com.example.api.integrationtests.util.vo.v1.AccountCredentialsVO;
import com.example.api.integrationtests.util.vo.v1.TokenVO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerJsonTest extends AbstractIntegrationTest {

    private static TokenVO tokenVO;

    @Test
	@Order(1)
	public void testSignin() {
		AccountCredentialsVO accountCredentials = new AccountCredentialsVO("dilores", "102030");

		tokenVO = 
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
						.as(TokenVO.class);
		
		assertNotNull(tokenVO.getAccessToken());
		assertNotNull(tokenVO.getRefreshToken());
	}

    @Test
	@Order(2)
	public void testRefreshToken() {
		var newTokenVO = 
			given()
				.basePath("/auth/refresh")
				.port(TestsConstants.SERVER_PORT)
                .accept(TestsConstants.CONTENT_TYPE_JSON)
				.contentType(TestsConstants.CONTENT_TYPE_JSON)
				.pathParam("username", tokenVO.getUsername())
                .header(TestsConstants.HEADER_PARAM_AUTHORIZATION, "Bearer "+tokenVO.getRefreshToken())
				.when()
					.put("{username}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(TokenVO.class);
		
		assertNotNull(newTokenVO.getAccessToken());
		assertNotNull(newTokenVO.getRefreshToken());
	}
    
}
