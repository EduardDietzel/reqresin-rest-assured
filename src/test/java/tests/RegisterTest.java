package tests;

import dto.RegistrationRequest;
import dto.SuccessRegistrationResponse;
import dto.UnsuccessfulRegistrationResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {

    @Test
    public void successRegistration(){
        RegistrationRequest requestBody = new RegistrationRequest("eve.holt@reqres.in", "pistol");
        SuccessRegistrationResponse response = given().baseUri("https://reqres.in")
                .body(requestBody)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/api/register")
                .then().log().all()
                .statusCode(200).extract().body().jsonPath().getObject("", SuccessRegistrationResponse.class);
        System.out.println(response.getToken());
        // all fields are not empty
        assertNotNull(response.getId());
        assertFalse(response.getToken().isEmpty());
    }

    @Test
    public void registrationWithoutPassword(){
        RegistrationRequest requestBody = new RegistrationRequest("eve.holt@reqres.in", "");
        UnsuccessfulRegistrationResponse response = given().baseUri("https://reqres.in")
                .body(requestBody)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/api/register")
                .then().log().all()
                .statusCode(400).extract().body().jsonPath().getObject("", UnsuccessfulRegistrationResponse.class);
        System.out.println(response.getError());

        //check that error message text is Missing password
        assertEquals("Missing password", response.getError());

        //Для метода https://reqres.in/api/register реализовать необходимые автотесты для негативных сценариев
    }

}
