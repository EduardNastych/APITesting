package trelloAPI.POST;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import trelloAPI.Specifications;
import trelloAPI.TestRestClient;

import static io.restassured.RestAssured.given;
import static trelloAPI.Globals.BOARD_NAME;
import static trelloAPI.Globals.LIST_NAME;

public class CreateAListOnBoardTest {
    public String NAME_LIST;
    public String BOARD_ID;

   @Test
    public void createListOnBoard() {
       Specifications.installSpec(Specifications.requestSpec(), Specifications.responseSpecOK200());
       BOARD_ID = TestRestClient.createNewBoard(BOARD_NAME).get("id");
       JsonPath jsonResponse = given()
               .contentType(ContentType.JSON)
               .body(LIST_NAME)
               .when()
               .post("/1/boards/{id}/lists", BOARD_ID)
               .then().log().all()
               .extract().jsonPath();
       NAME_LIST = jsonResponse.get("name");

       Assert.assertEquals(jsonResponse.get("name"), NAME_LIST);
   }

    @AfterTest
    public void deleteBoard(){
        TestRestClient.deleteBoard(BOARD_ID);
    }
}
