package trelloAPI.GET;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import trelloAPI.Specifications;
import trelloAPI.TestRestClient;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static trelloAPI.Globals.*;

public class GetFilteredCardsOnABoardTest {
    public String BOARD_ID;
    @Test
    public void getFilteredCardsOnABoard() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        BOARD_ID = TestRestClient.createNewBoard(BOARD_NAME).get("id");
        JsonPath jsonResponse = given()
                .header("Accept", "application/json")
        .when()
                .get("/1/boards/{id}/cards/{filter}", BOARD_ID, FILTER)
        .then()
                .body("size()", greaterThanOrEqualTo(0))
                .log().all()
                .extract().jsonPath();

        Assert.assertEquals(jsonResponse.get().toString(), EMPTY);
    }
    @AfterTest
    public void deleteBoard(){
        TestRestClient.deleteBoard(BOARD_ID);
    }
}

