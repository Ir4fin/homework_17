package tests;

import org.json.JSONObject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresTests {

    @DisplayName("Получение данных о несуществующем в БД пользователе ")
    @Test
    void getSingleUserNegative() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @DisplayName("Получение данных о существующем пользователе")
    @Test
    void getSingleUserPositive() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);
    }

    @DisplayName("Успешная регистрация в системе")
    @Test
    void successfulRegistration() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "eve.holt@reqres.in");
        requestBody.put("password", "pistol");

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody.toString())
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @DisplayName("Регистрация без пароля - проверка ошибки")
    @Test
    void tryToRegistrationWithoutPassword() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "eve.holt@reqres.in");


        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody.toString())
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400);
    }

    @DisplayName("Регистрация без email - проверка ошибки")
    @Test
    void tryToRegistrationWithoutEmail() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("password", "pistol");


        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody.toString())
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400);
    }

    @DisplayName("Удаление юзера")
    @Test
    void deleteSuccessful() {
        given()
                .log().uri()
                .log().body()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }


}