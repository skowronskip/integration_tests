package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;

public class FindPostsTest extends FunctionalTests {

    private static final String FIND_API = "/blog/user/{id}/post";

    @Test
    public void getPostOfExistingUserShouldReturnOk() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .when()
                   .get(FIND_API, 1);
    }
}
