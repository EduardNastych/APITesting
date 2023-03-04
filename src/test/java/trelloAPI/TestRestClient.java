package trelloAPI;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
public class TestRestClient {

    public static JsonPath deleteBoard(String BOARD_ID){
        Specifications.installSpec(Specifications.requestSpec(),Specifications.responseSpecOK200());
        JsonPath jsonResponse = given()
                .header("Accept", "application/json")
                .when()
                .delete("/1/boards/{id}", BOARD_ID)
                .then().log().all()
                .extract().jsonPath();
        return jsonResponse;
    }

    public static JsonPath createNewBoard(String body) {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/1/boards/")
                .then().log().all()
                .extract().jsonPath();
    }
}
