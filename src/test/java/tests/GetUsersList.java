package tests;

import dto.UserDataResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetUsersList {

    @Test
    public void getUsersListPage2(){
        List<UserDataResponse> users = given().baseUri("https://reqres.in")
                .when().log().all()
                .get("/api/users?page=2")
                .then().log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList("data", UserDataResponse.class);
        System.out.println(users.get(0).getFirst_name());

        // all id in list "users" are positive values
//        for (UserDataResponse user : users) {
//            assertTrue(user.getId()>=0);
//            assertTrue(user.getEmail().endsWith("@reqres.in"));
//            assertTrue(user.getAvatar().contains("" + user.getId()));
//        }

        users.forEach(user -> assertTrue(user.getId()>=0));

        //all email ends with @reqres.in
        users.forEach(user -> assertTrue(user.getEmail().endsWith("@reqres.in")));

        //each user avatar contains id of this user
        users.forEach(user -> assertTrue(user.getAvatar().contains("" + user.getId())));


    }
}
