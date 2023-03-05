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
import static trelloAPI.Globals.SIDEBAR_BOARD_INFO;

public class UpdateShowSidebarBoardActionsPrefOnABoardTest {
    public String BOARD_ID;

    @BeforeTest
    public void createNewBoard() {
        BOARD_ID = TestRestClient.createNewBoard(BOARD_NAME).get("id");
    }



    @Test
    public void updateShowSidebarBoard() {
        Specifications.installSpec(Specifications.requestSpec(),Specifications.responseSpecOK200());
        JsonPath jsonResponse= given()
                .contentType(ContentType.JSON)
                .body(SIDEBAR_BOARD_INFO)
                .when()
                .put("/1/boards/{Id}/myPrefs/showSidebarBoardActions",BOARD_ID)
                .then()
                .log().all()
                .extract().jsonPath();

        Assert.assertTrue(jsonResponse.get("showSidebarMembers"));

    }


    @AfterTest
    public void deleteBoard() {

            TestRestClient.deleteBoard(BOARD_ID);
        }
    }


