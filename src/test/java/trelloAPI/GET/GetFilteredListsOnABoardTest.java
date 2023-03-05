package trelloAPI.GET;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import trelloAPI.Specifications;
import trelloAPI.TestRestClient;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static trelloAPI.Globals.BOARD_NAME;
import static trelloAPI.Globals.FILTER;

public class GetFilteredListsOnABoardTest {
    public String BOARD_ID;
    @Test
    public void getFilteredListsOnABoard() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        BOARD_ID = TestRestClient.createNewBoard(BOARD_NAME).get("id");
        given()
                .header("Accept", "application/json")
        .when()
                .get("/1/boards/{id}/lists/{filter}", BOARD_ID, FILTER)
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
