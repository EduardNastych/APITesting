package trelloAPI.POST;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import trelloAPI.DELETE.DeleteABoardTest;
import trelloAPI.Globals;
import trelloAPI.Specifications;

import static io.restassured.RestAssured.given;
import static trelloAPI.Globals.board_name;

public class CreateABoardTest {
    public String ID_BOARD;
    @Test
    public void createABoard() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        JsonPath jsonResponse = given()
                .contentType(ContentType.JSON)
                .body(board_name)
                .when()
                .post("/1/boards/")
                .then().log().all()
                .extract().jsonPath();

        ID_BOARD = jsonResponse.get("id");
        Assert.assertEquals(jsonResponse.get("name"), Globals.NAME);
    }
    @AfterTest
    public void deleteCard(){
        DeleteABoardTest deleteABoardTest = new DeleteABoardTest();
        deleteABoardTest.BOARD_ID = ID_BOARD;
        deleteABoardTest.deleteABoardTest();
    }
}
