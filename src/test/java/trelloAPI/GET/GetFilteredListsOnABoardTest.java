package trelloAPI.GET;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import trelloAPI.DELETE.DeleteABoardTest;
import trelloAPI.POST.CreateABoardTest;
import trelloAPI.Specifications;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static trelloAPI.Globals.FILTER;

public class GetFilteredListsOnABoardTest {
    public String BOARD_ID;

    @BeforeTest
    public void createNewBoard() {
        CreateABoardTest createABoardTest = new CreateABoardTest();
        createABoardTest.createABoard();
        BOARD_ID = createABoardTest.ID_BOARD;
    }

    @Test
    public void getFilteredListsOnABoard() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
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
    public void deleteBoard() {
        DeleteABoardTest deleteABoardTest = new DeleteABoardTest();
        deleteABoardTest.BOARD_ID = BOARD_ID;
        deleteABoardTest.deleteABoardTest();
    }
}
