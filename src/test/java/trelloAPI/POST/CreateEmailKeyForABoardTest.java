package trelloAPI.POST;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import trelloAPI.DELETE.DeleteABoardTest;
import trelloAPI.Specifications;

import static io.restassured.RestAssured.given;

public class CreateEmailKeyForABoardTest {
    public String BOARD_ID;

    @BeforeTest
    public void createNewBoard(){
        CreateABoardTest createABoardTest = new CreateABoardTest();
        createABoardTest.createABoard();
        BOARD_ID = createABoardTest.ID_BOARD;
    }
    @Test
    public void createEmailKeyForABoard() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        JsonPath jsonResponse = given()
                .header("Accept", "application/json")
        .when()
                .post("/1/boards/{id}/emailKey/generate", BOARD_ID)
        .then()
                .log().all()
                .extract().jsonPath();

        Assert.assertEquals(jsonResponse.get("id"), BOARD_ID);
    }
    @AfterTest
    public void deleteBoard(){
        DeleteABoardTest deleteABoardTest = new DeleteABoardTest();
        deleteABoardTest.BOARD_ID = BOARD_ID;
        deleteABoardTest.deleteABoardTest();
    }
}
