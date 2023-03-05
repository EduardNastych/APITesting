package trelloAPI.PUT;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import trelloAPI.Specifications;
import trelloAPI.TestRestClient;

import static io.restassured.RestAssured.given;
import static trelloAPI.Globals.BOARD_NAME;
import static trelloAPI.Globals.SIDEBAR_MEMBER_INFO;

public class UpdateShowSidebarMembersPrefOnABoardTest {
    public String BOARD_ID;
    @Test
    public void updateShowSidebarMembersPrefOnABoard() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        BOARD_ID = TestRestClient.createNewBoard(BOARD_NAME).get("id");
        JsonPath jsonResponse = given()
                .contentType(ContentType.JSON)
                .body(SIDEBAR_MEMBER_INFO)
        .when()
                .put("/1/boards/{id}/myPrefs/showSidebarMembers", BOARD_ID)
        .then()
                .log().all()
                .extract().jsonPath();

        Assert.assertEquals(jsonResponse.get("showSidebarMembers"), false);

    }
    @AfterTest
    public void deleteBoard(){
        TestRestClient.deleteBoard(BOARD_ID);
    }
}
