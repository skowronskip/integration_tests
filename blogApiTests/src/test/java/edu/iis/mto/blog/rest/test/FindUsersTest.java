package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

public class FindUsersTest extends FunctionalTests {

    private static final String USER_API = "/blog/user/find";

    @Test
    public void getShouldReturnUsers() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .param("searchString", "John")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .and()
                   .body("size", is(1))
                   .when()
                   .get(USER_API);
    }

    @Test
    public void getShouldNotReturnRemovedUsers() {
        RestAssured.given()
                  .accept(ContentType.JSON)
                  .header("Content-Type", "application/json;charset=UTF-8")
                  .param("searchString", "Removed")
                  .expect()
                  .log()
                  .all()
                  .statusCode(HttpStatus.SC_OK)
                  .and()
                  .body("size", is(0))
                  .when()
                  .get(USER_API);
    }
}
