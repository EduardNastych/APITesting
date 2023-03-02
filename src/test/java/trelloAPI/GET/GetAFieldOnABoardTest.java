package trelloAPI.GET;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import trelloAPI.DELETE.DeleteABoardTest;
import trelloAPI.Globals;
import trelloAPI.POST.CreateABoardTest;
import trelloAPI.Specifications;

import static io.restassured.RestAssured.given;

public class GetAFieldOnABoardTest {
    public String BOARD_ID;
    public String NAME;

    @BeforeTest
    public void createNewBoard(){
        CreateABoardTest createABoardTest = new CreateABoardTest();
        createABoardTest.createABoard();
        BOARD_ID = createABoardTest.ID_BOARD;
        NAME = createABoardTest.NAME;
    }
    @Test
    public void getAFieldOnABoard() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        JsonPath jsonResponse = given()
                .header("Accept", "application/json")
                .when()
                .get("/1/boards/{id}", BOARD_ID)
                .then().log().all()
                .extract().jsonPath();

        Assert.assertEquals(jsonResponse.get("name"), Globals.NAME);
    }
    @AfterTest
    public void deleteBoard(){
        DeleteABoardTest deleteABoardTest = new DeleteABoardTest();
        deleteABoardTest.BOARD_ID = BOARD_ID;
        deleteABoardTest.deleteABoardTest();
    }
}
