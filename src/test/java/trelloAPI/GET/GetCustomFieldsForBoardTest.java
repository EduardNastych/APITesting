package trelloAPI.GET;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import trelloAPI.Specifications;
import trelloAPI.TestRestClient;

import static io.restassured.RestAssured.given;
import static trelloAPI.Globals.BOARD_NAME;
import static trelloAPI.Globals.EMPTY;

public class GetCustomFieldsForBoardTest {
    public String BOARD_ID;

    @BeforeTest
    public void createNewBoard(){
        BOARD_ID = TestRestClient.createNewBoard(BOARD_NAME).get("id");
    }

    @Test
    public void getAFieldOnABoard() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        JsonPath jsonResponse = given()
                .header("Accept", "application/json")
                .when()
                .get("/1/boards/{id}/customFields",BOARD_ID)
                .then()
                .log().all()
                .extract().jsonPath();

        Assert.assertEquals(jsonResponse.get().toString(), EMPTY);
    }
    @AfterTest
    public void deleteBoard(){
        TestRestClient.deleteBoard(BOARD_ID);
    }
}
