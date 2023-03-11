package utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static utils.ReqSpecs.*;

public class TMDBApi {

    public static Response postWithBodySimple(String basePath , Object body){
        return given().spec(requestWithBody(basePath,body))
                .when().post()
                .then().extract().response();

    }

    public static Response postWithBody(String basePath , Object body, String sessionId)
    {
        return given().spec(requestWithBodyAndSession(basePath,body,sessionId)).log().all()
                .when().post()
                .then().log().all()
                .extract().response();
    }

    public static Response postWithBodyPathParam(String basePath , Object body, String sessionId,
                                                 String pathName, String pathValue)
    {
        return given().spec(requestWithBodyAndSession(basePath,body,sessionId))
                .pathParam(pathName,pathValue).log().all()
                .when().post()
                .then().log().all()
                .extract().response();
    }

    public static Response getStandard(String basePath){
        return given().spec(standardRequest(basePath))
                .when().get()
                .then().extract().response();

    }

    public static Response getWithPathParams(String basePath, String pathName, String pathValue){
        return given().spec(standardRequest(basePath))
                .pathParam(pathName,pathValue).log().all()
                .when().get()
                .then().log().all()
                .extract().response();
    }

    public static Response deleteStandard(String basePath, String queryName, String queryValue, String pathName,
                                          String pathValue)
    {
        return given().spec(requestWithQuery(basePath,queryName,queryValue)).log().all()
                .pathParam(pathName,pathValue).log().all()
                .when().delete()
                .then().log().all()
                .extract().response();
    }

    public static Response postWithQuerysPathParam(String basePath, String sessionId,
                                                   String queryName, String queryValue, String pathName,
                                                   String pathValue)
    {
        return given().spec(requestWithSessionQuery(basePath, sessionId, queryName, queryValue))
                .pathParam(pathName,pathValue).log().all()
                .when().post()
                .then().log().all()
                .extract().response();
    }

}
