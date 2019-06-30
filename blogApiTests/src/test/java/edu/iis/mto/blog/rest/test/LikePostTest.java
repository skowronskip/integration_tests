package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

public class LikePostTest extends FunctionalTests {

    private static final String LIKE_API = "/blog/user/{id}/like/{postId}";

    @Test
    public void likePostByUnconfirmedUserShouldReturnForbidden() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_FORBIDDEN)
                   .when()
                   .post(LIKE_API, 2, 1);
    }

    @Test
    public void likePostByConfirmedUserShouldReturnOK() {
        RestAssured.given()
                  .accept(ContentType.JSON)
                  .header("Content-Type", "application/json;charset=UTF-8")
                  .expect()
                  .log()
                  .all()
                  .statusCode(HttpStatus.SC_OK)
                  .when()
                  .post(LIKE_API, 3, 1);
    }

    @Test
    public void likePostByAuthorPostShouldReturnBadRequest() {
        RestAssured.given()
                  .accept(ContentType.JSON)
                  .header("Content-Type", "application/json;charset=UTF-8")
                  .expect()
                  .log()
                  .all()
                  .statusCode(HttpStatus.SC_BAD_REQUEST)
                  .when()
                  .post(LIKE_API, 1, 1);
    }

    @Test
    public void likePostByTwiceShouldNotReturnOK() {
        RestAssured.given()
                  .accept(ContentType.JSON)
                  .header("Content-Type", "application/json;charset=UTF-8")
                  .expect()
                  .log()
                  .all()
                  .statusCode(HttpStatus.SC_OK)
                  .when()
                  .post(LIKE_API, 3, 1);

        RestAssured.given()
                  .accept(ContentType.JSON)
                  .header("Content-Type", "application/json;charset=UTF-8")
                  .expect()
                  .log()
                  .all()
                  .statusCode(HttpStatus.SC_OK)
                  .when()
                  .post(LIKE_API, 3, 1);
    }
}
