import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class ApiTest {

    String requestURL = "https://petstore.swagger.io/v2/pet/";

    @Test
    public void PetPostAndGet() {
        long id =
                given().
                        body("{\n" +
                                "  \"category\": {\n" +
                                "    \"id\": 1,\n" +
                                "    \"name\": \"cat\"\n" +
                                "  },\n" +
                                "  \"name\": \"Tomas\",\n" +
                                "  \"photoUrls\": [\n" +
                                "    \"onliner.by\"\n" +
                                "  ],\n" +
                                "  \"tags\": [\n" +
                                "    {\n" +
                                "      \"id\": 10,\n" +
                                "      \"name\": \"scottish fold\"\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"status\": \"available\"\n" +
                                "}").
                        header("Content-Type", "application/json").
                        log().all().
                when().
                        post(requestURL).
                then().
                        log().all().
                        statusCode(200).
                        body("category.id", equalTo(1),
                                "category.name", equalTo("cat"),
                                "name", equalTo("Tomas"),
                                "photoUrls", hasItems("onliner.by"),
                                "tags.id", hasItems(10),
                                "tags.name", hasItems("scottish fold"),
                                "status", equalTo("available")).
                        extract().body().jsonPath().getLong("id");
                when().
                        get(requestURL + id).
                then().
                        log().all().
                        statusCode(200).
                        body("category.id", equalTo(1),
                        "category.name", equalTo("cat"),
                                "name", equalTo("Tomas"),
                                "photoUrls", hasItems("onliner.by"),
                                "tags.id", hasItems(10),
                                "tags.name", hasItems("scottish fold"),
                                "status", equalTo("available"));
    }
}

