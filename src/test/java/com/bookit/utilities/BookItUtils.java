package com.bookit.utilities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookItUtils {

    public static String generateToken(String email, String password){
        Response response = given().
                accept(ContentType.JSON)
                .queryParams("email", email)
                .queryParams("password", password)
                .when()
                .get(ConfigurationReader.getProperty("apiUrl")+ "/sign");

        String token = response.path("accessToken");

        String finalToken = "Bearer " + token;

        return finalToken;
    }
}
