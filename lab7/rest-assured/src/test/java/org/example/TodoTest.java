package org.example;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class TodoTest {
    @BeforeEach
    public void setup() {
        baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testTodosEndpointReturns200() {
        when()
                .get("/todos")
        .then()
                .statusCode(200);
    }

    @Test
    public void testGetTodoByIdReturnsCorrectTitle() {
        when()
                .get("/todos/4")
        .then()
                .statusCode(200)
                .body("title", equalTo("et porro tempora"));
    }

    @Test
    public void testListAllTodosContainsIds198And199() {
        when()
                .get("/todos")
        .then()
                .statusCode(200)
                .body("id", hasItems(198, 199));
    }

    @Test
    public void testListAllTodosInLessThan2Seconds() {
        when()
                .get("/todos")
        .then()
                .statusCode(200)
                .time(lessThan(2000L));
    }
}
