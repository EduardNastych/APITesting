package trelloAPI.PUT;

import io.restassured.http.ContentType;
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
import static trelloAPI.Globals.NEW_BOARD_NAME;

public class UpdateABoardTest {
    public String BOARD_ID;
    @BeforeTest
    public void createNewBoard(){
        CreateABoardTest createABoardTest = new CreateABoardTest();
        createABoardTest.createABoard();
        BOARD_ID = createABoardTest.ID_BOARD;
    }
    @Test
    public void updateABoard() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        JsonPath jsonResponse = given()
                .contentType(ContentType.JSON)
                .body(NEW_BOARD_NAME)
                .when()
                .put("/1/boards/{id}", BOARD_ID)
                .then().log().all()
                .extract().jsonPath();

        Assert.assertEquals(jsonResponse.get("name"), Globals.NEW_NAME_OF_BOARD);
    }
    @AfterTest
    public void deleteBoard(){
        DeleteABoardTest deleteABoardTest = new DeleteABoardTest();
        deleteABoardTest.BOARD_ID = BOARD_ID;
        deleteABoardTest.deleteABoardTest();
    }
}
