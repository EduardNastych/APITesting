package trelloAPI.GET;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import trelloAPI.Globals;
import trelloAPI.Specifications;
import trelloAPI.TestRestClient;


import static io.restassured.RestAssured.given;
import static trelloAPI.Globals.BOARD_NAME;

public class GetABoardTest {
    public String BOARD_ID;
    @Test
    public void getABoard() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        BOARD_ID = TestRestClient.createNewBoard(BOARD_NAME).get("id");
        JsonPath jsonResponse = given()
        .when()
                .get("/1/boards/{id}", BOARD_ID)
        .then()
                .log().all()
                .extract().jsonPath();

        Assert.assertEquals(jsonResponse.get("name"), Globals.NAME_OF_BOARD);
    }
    @AfterTest
    public void deleteBoard(){
        TestRestClient.deleteBoard(BOARD_ID);
    }
}
