package trelloAPI.GET;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import trelloAPI.Specifications;
import trelloAPI.TestRestClient;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static trelloAPI.Globals.BOARD_NAME;

public class GetBoardStarsOnABoardTest {
    public String BOARD_ID;
    @Test
    public void getBoardStarsOnABoard() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        BOARD_ID = TestRestClient.createNewBoard(BOARD_NAME).get("id");
        given()
                .header("Accept", "application/json")
        .when()
                .get("/1/boards/{id}/boardStars", BOARD_ID)
        .then()
                .body("size()", greaterThanOrEqualTo(0))
                .log().all()
                .extract().jsonPath();
    }
    @AfterTest
    public void deleteBoard(){
        TestRestClient.deleteBoard(BOARD_ID);
    }
}
