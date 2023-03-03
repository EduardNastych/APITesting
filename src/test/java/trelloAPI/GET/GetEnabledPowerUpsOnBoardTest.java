package trelloAPI.GET;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import trelloAPI.DELETE.DeleteABoardTest;
import trelloAPI.POST.CreateABoardTest;
import trelloAPI.Specifications;

import static io.restassured.RestAssured.given;

public class GetEnabledPowerUpsOnBoardTest {
    public String BOARD_ID;
    @BeforeTest
    public void createNewBoard(){
        CreateABoardTest createABoardTest = new CreateABoardTest();
        createABoardTest.createABoard();
        BOARD_ID = createABoardTest.ID_BOARD;
    }
    @Test
    public void getEnabledPowerUpsOnBoard() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        JsonPath jsonResponse = given()
        .when()
                .get("/1/boards/{id}/boardPlugins", BOARD_ID)
        .then()
                .log().all()
                .extract().jsonPath();

        Assert.assertEquals(jsonResponse.get("promotional"), true);
    }
    @AfterTest
    public void deleteBoard(){
        DeleteABoardTest deleteABoardTest = new DeleteABoardTest();
        deleteABoardTest.BOARD_ID = BOARD_ID;
        deleteABoardTest.deleteABoardTest();
    }
}
