import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatientIntegrationTest {

    @BeforeAll
    static void setUP(){
        RestAssured.baseURI = "http://localhost:4000";
    }


   @Test
   @Order(1)
    public void shouldReturnAllPatentSuccess(){
        String payload = """
               {
               "email": "java456@gmail.com",
               "password": "java1212",
               "address": "Kathmandu",
               "role":"ADMIN"
               }
               """;

        String token =  given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/api/auth/signup")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");


       given()
               .header("Authorization", "Bearer " + token)
               .when()
               .get("/api/patent")
               .then()
               .statusCode(200)
               .body("$", notNullValue());


    }


    @Test
    @Order(2)
    void shouldSavePatentSuccessfully() {

        String payload = """
            {
               "email": "java456@gmail.com",
               "password": "java1212",
               "role":"ADMIN"
            }
            """;

        String payloadPatent = """
            {
              "name": "PatentDEMO",
              "email": "patentDEMO@gmail.com",
              "address": "Kathmandu",
              "dateOfBirth": "2000-01-01",
              "registerDate": "2024-01-01"
            }
            """;

        //get token
        String token =  given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");


        //add and check Patent
        Response response =  given()
                    .header("Authorization", "Bearer " + token)
                    .contentType("application/json")
                    .body(payloadPatent)
                    .when()
                    .post("/api/patent/save")
                    .then()
                    .statusCode(200)
                    .body("id", notNullValue())
                    .body("name", equalTo("PatentDEMO"))
                    .body("email", equalTo("patentDEMO@gmail.com"))
                    .extract()
                    .response();

        String id = response.path("id");

        //clean DB->Patent table
        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(payload)
                .when()
                .delete("/api/patent/delete/" + id)
                .then()
                .statusCode(204);

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
