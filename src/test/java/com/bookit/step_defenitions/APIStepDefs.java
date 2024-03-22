package com.bookit.step_defenitions;

import com.bookit.utilities.BookItUtils;
import com.bookit.utilities.ConfigurationReader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class APIStepDefs {

    String token;
    String emailGlobal;
    String passwordGlobal;
    public static String apiName;
    public static String apiRole;
    public static String apiTeam;
    public static String apiBatch;
    public static String apiCampus;

    @When("User logs in BookIT API using {string} and {string}")
    public void user_logs_in_BookIT_API_using_and(String email, String password) {
        token = BookItUtils.generateToken(email, password);
        emailGlobal = email;
        passwordGlobal = password;
    }
    @Then("User gets API information")
    public void user_gets_API_information() {

        Response response = given().accept(ContentType.JSON)
                .and().header("Authorization",token)
                .when()
                .get(ConfigurationReader.getProperty("apiUrl") + "/api/students/me");

        System.out.println(response.statusCode());
        JsonPath jsonPath = response.jsonPath();
        apiName = jsonPath.getString("firstName") + " " + jsonPath.getString("lastName");
        apiRole = jsonPath.getString("role");

        String [] otherQuieries = BookItUtils.getMyInfo(emailGlobal,passwordGlobal);

        apiTeam = otherQuieries[0];
        apiBatch = otherQuieries[1];
        apiCampus = otherQuieries[2];
        System.out.println(apiCampus);
    }
}
