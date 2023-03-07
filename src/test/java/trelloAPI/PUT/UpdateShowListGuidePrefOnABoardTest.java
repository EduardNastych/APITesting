package trelloAPI.PUT;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import trelloAPI.Specifications;
import trelloAPI.TestRestClient;

import static io.restassured.RestAssured.given;
import static trelloAPI.Globals.BOARD_NAME;
import static trelloAPI.Globals.LIST_INFO;

public class UpdateShowListGuidePrefOnABoardTest {
    public String BOARD_ID;

    @BeforeTest
    public void createNewBoard() { BOARD_ID = TestRestClient.createNewBoard(BOARD_NAME).get("id"); }


    @Test
    public void updateShowList() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        JsonPath jsonResponse = given()
                .body(LIST_INFO)
                .when()
                .put("/1/boards/{Id}/myPrefs/showListGuide", BOARD_ID)
                .then()
                .log().all()
                .extract().jsonPath();

        Assert.assertEquals(jsonResponse.get("showSidebarMembers"), true);
    }


    @AfterTest
    public void deleteBoard() {
        TestRestClient.deleteBoard(BOARD_ID);
    }
}
