package br.com.caelum.leilao.teste;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

import org.junit.*;

import br.com.caelum.leilao.modelo.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;

public class OutrosTest {
	@Test
	public void deveGerarUmCookie() {
		expect()
			.cookie("rest-assured", "funciona")
		.when()
			.get("/cookie/teste");
	}
	
	@Test
	public void deveGerarUmHeader() {
		expect()
			.header("novo-header", "abc")
		.when()
			.get("/cookie/teste");
	}
	
	/*
	 * https://github.com/rest-assured/rest-assured/wiki/Usage#authentication
	 * https://github.com/rest-assured/rest-assured/wiki/Usage#session-support
given().
        multiPart(new File("/path/para/arquivo")).
when().
        post("/upload");
	 */
}