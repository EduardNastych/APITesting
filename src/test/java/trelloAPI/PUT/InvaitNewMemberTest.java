package trelloAPI.PUT;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import trelloAPI.DELETE.DeleteABoardTest;
import trelloAPI.Specifications;

import static io.restassured.RestAssured.given;
import static trelloAPI.Globals.BOARD_NAME;
import static trelloAPI.Globals.newMemberEmail;

public class InvaitNewMemberTest {
    public String BOARD_ID;

    @BeforeTest
    public void createNewBoard() {

        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        JsonPath jsonResponse = given()
                .contentType(ContentType.JSON)
                .body(BOARD_NAME)
                .when()
                .post("/1/boards/")
                .then().log().all()
                .extract().jsonPath();

        BOARD_ID= jsonResponse.get("id");

    }



    @Test
    public void inviteNewMemberToBoard() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        JsonPath jsonResponse = given()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .queryParam("email", newMemberEmail)
                .when()
                .put("/1/boards/" + BOARD_ID + "/members")
                .then().log().all()
                .statusCode(200)
                .extract().jsonPath();

        Assert.assertEquals(jsonResponse.get("members[0].fullName"), "Eduard Nastych");
        Assert.assertEquals(jsonResponse.get("id"), BOARD_ID);


    }

    @AfterTest
    public void deleteBoard() {
        DeleteABoardTest deleteABoardTest = new DeleteABoardTest();
        deleteABoardTest.BOARD_ID = BOARD_ID;
        deleteABoardTest.deleteABoardTest();
    }
}
