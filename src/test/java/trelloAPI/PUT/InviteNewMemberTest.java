package trelloAPI.PUT;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import trelloAPI.DELETE.DeleteABoardTest;
import trelloAPI.Specifications;
import trelloAPI.TestRestClient;

import static io.restassured.RestAssured.given;
import static trelloAPI.Globals.*;

public class InviteNewMemberTest {

    public String BOARD_ID;

    @BeforeTest
    public void createNewBoard() {
        BOARD_ID = TestRestClient.createNewBoard(BOARD_NAME).get("id");
    }

    @Test
    public void inviteNewMemberToBoard() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        JsonPath jsonResponse = given()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .queryParam("email", NEW_MEMBER_EMAIL)
                .when()
                .put("/1/boards/" + BOARD_ID + "/members")
                .then().log().all()
                .extract().jsonPath();

        Assert.assertEquals(jsonResponse.get("members[0].fullName"), "Eduard Nastych");
        Assert.assertEquals(jsonResponse.get("id"), BOARD_ID);
    }

    @AfterTest
    public void deleteBoard() {
        TestRestClient.deleteBoard(BOARD_ID);
    }

}
