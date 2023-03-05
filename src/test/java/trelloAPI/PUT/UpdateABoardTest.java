package trelloAPI.PUT;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import trelloAPI.Specifications;
import trelloAPI.TestRestClient;

import static io.restassured.RestAssured.given;
import static trelloAPI.Globals.*;

public class UpdateABoardTest {
    public String BOARD_ID;
    @Test
    public void updateABoard() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        BOARD_ID = TestRestClient.createNewBoard(BOARD_NAME).get("id");
        JsonPath jsonResponse = given()
                .contentType(ContentType.JSON)
                .body(NEW_BOARD_NAME)
        .when()
                .put("/1/boards/{id}", BOARD_ID)
        .then()
                .log().all()
                .extract().jsonPath();

        Assert.assertEquals(jsonResponse.get("name"), NEW_NAME_OF_BOARD);
    }
    @AfterTest
    public void deleteBoard(){
        TestRestClient.deleteBoard(BOARD_ID);
    }
}
