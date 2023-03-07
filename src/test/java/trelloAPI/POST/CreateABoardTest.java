package trelloAPI.POST;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import trelloAPI.Specifications;
import trelloAPI.TestRestClient;

import static io.restassured.RestAssured.given;
import static trelloAPI.Globals.*;

public class CreateABoardTest {
    public String ID_BOARD;
    public String NAME;
    @Test
    public void createABoard() {
        Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
        JsonPath jsonResponse = given()
                .body(BOARD_NAME)
        .when()
                .post("/1/boards/")
        .then()
                .log().all()
                .extract().jsonPath();

        ID_BOARD = jsonResponse.get("id");
        NAME = jsonResponse.get("name");
        Assert.assertEquals(jsonResponse.get("name"), NAME_OF_BOARD);
        Assert.assertEquals(jsonResponse.get("idOrganization"), ORGANIZATION_ID);
    }
    @AfterTest
    public void deleteBoard(){
        TestRestClient.deleteBoard(ID_BOARD);
    }
}
