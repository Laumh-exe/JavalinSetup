import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

import io.restassured.RestAssured;

public class ContextDemoTest {
    @BeforeAll
    public static void setupAll() {
        RestAssured.baseURI = "http://localhost:7770/api";
        ContextDemo.startServer(7770);
    }

    @AfterAll
    public static void tearDown() {
        ContextDemo.stopServer();
    }

    @Test
    @DisplayName("Test server is running")
    public void testServerIsRunning() {
        RestAssured
        .given()
        .when()
        .get("/contextdemo/test")
        .then()
        .statusCode(200);
    }

    @Test
    @DisplayName("Test post request")
    public void testPostRequest() {
        RestAssured
        .given()
        .when()
        .post("/contextdemo/post")
        .then()
        .statusCode(200);
    }

    @Test
    @DisplayName("Test response body")
    public void testResponseBody() {
        RestAssured
        .given()
        .when()
        .get("/contextdemo/hello/John")
        .then()
        .statusCode(200)
        .assertThat()
        .body(org.hamcrest.Matchers.equalTo("Hello, John"));
    }

    @Test
    @DisplayName("Test response body person")
    public void testResponseBodyPerson() {
        RestAssured
        .given()
        .contentType("application/json")
        .when()
        .get("/persons/name/john")
        .then()
        .statusCode(200)
        .assertThat()
        .body("name", org.hamcrest.Matchers.equalTo("John")
        , "age", org.hamcrest.Matchers.equalTo(25));
    }
}   
