package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;

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

    @Test
    public void getPostOfRemovedUserShouldReturnBadRequest() {
        RestAssured.given()
                  .accept(ContentType.JSON)
                  .header("Content-Type", "application/json;charset=UTF-8")
                  .expect()
                  .log()
                  .all()
                  .statusCode(HttpStatus.SC_BAD_REQUEST)
                  .when()
                  .get(FIND_API, 4);
    }

    @Test
    public void getPostOfShouldReturnCorrectLikesCount() {
        RestAssured.given()
                  .accept(ContentType.JSON)
                  .header("Content-Type", "application/json;charset=UTF-8")
                  .expect()
                  .log()
                  .all()
                  .statusCode(HttpStatus.SC_OK)
                  .and()
                  .body("likesCount", hasItems(0))
                  .when()
                  .get(FIND_API, 1);
    }
}
