package trelloAPI.PUT;

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
import static trelloAPI.Globals.SIDEBAR_INFO;

public class UpdateShowSidebarPrefOnABoardTest {
    public String BOARD_ID;
    @BeforeTest
    public void createNewBoard(){
        CreateABoardTest createABoardTest = new CreateABoardTest();
        createABoardTest.createABoard();
        BOARD_ID = createABoardTest.ID_BOARD;
    }
    @Test
    public void updateEmailPositionPrefOnABoard() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        JsonPath jsonResponse = given()
                .contentType(ContentType.JSON)
                .body(SIDEBAR_INFO)
        .when()
                .put("/1/boards/{id}/myPrefs/showSidebar", BOARD_ID)
        .then()
                .log().all()
                .extract().jsonPath();

        Assert.assertEquals(jsonResponse.get("showSidebar"), true);

    }
    @AfterTest
    public void deleteBoard(){
        DeleteABoardTest deleteABoardTest = new DeleteABoardTest();
        deleteABoardTest.BOARD_ID = BOARD_ID;
        deleteABoardTest.deleteABoardTest();
    }
}
