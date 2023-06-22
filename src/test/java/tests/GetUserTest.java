package tests;

import dto.UserDataResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class GetUserTest {

    @Test
    public void getUserTest(){
        int requestId = 2;
        UserDataResponse user = given().baseUri("https://reqres.in")
                .when().log().all()
                .get("/api/users/" + requestId)
                .then().log().all()
                .statusCode(200).extract().body().jsonPath().getObject("data", UserDataResponse.class);
        System.out.println(user.getId());
        //check that id equals to id from endpoint
        assertEquals(requestId, user.getId());
        //check that email is not empty
        assertFalse(user.getEmail().isEmpty());
        //email ends with @reqres.in
        // assertTrue(user.getEmail().contains("@reqres.in"));
        assertTrue(user.getEmail().endsWith("@reqres.in"));
        //avatar contains id-image
        assertTrue(user.getAvatar().contains(requestId + "-image"));

    }

}
