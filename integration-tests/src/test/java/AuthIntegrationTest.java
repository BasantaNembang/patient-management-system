import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthIntegrationTest {

    @BeforeAll
    static void setUP(){
        RestAssured.baseURI = "http://localhost:4000";
    }


    @Test
    @Order(1)
    public void shouldSignUpSuccessfully(){
       String payload = """
               {
               "email": "java456@gmail.com",
               "password": "java1212",
               "address": "Kathmandu",
               "role":"ADMIN"
               }
               """;

        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/api/auth/signup")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("role", equalTo("ADMIN"));

    }

    @Test
    @Order(2)
    public void shouldFailWhenUserAlreadyExists(){
        String payload = """
               {
               "email": "java456@gmail.com",
               "password": "java1212",
               "address": "Kathmandu",
               "role":"ADMIN"
               }
               """;

        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/api/auth/signup")
                .then()
                .statusCode(400);

    }


    @Test
    @Order(3)
    public void shouldLoginSuccessfully(){
        String payload = """
               {
               "email": "java456@gmail.com",
               "password": "java1212",
               "role":"ADMIN"
               }
               """;

        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("role", equalTo("ADMIN"));

    }

    @Test
    @Order(4)
    public void shouldLoginFail(){
        String payload = """
               {
               "email": "java456@gmail.com",
               "password": "wrong_password",
               "role":"ADMIN"
               }
               """;

        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(400);


    }


    @AfterAll
    static void cleanUp() {

            given()
                    .when()
                    .delete("/api/auth/delete/" + "java456@gmail.com")
                    .then()
                    .statusCode(204);

    }

}

