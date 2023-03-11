package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ReqSpecs {

    private final static String baseUri = "https://api.themoviedb.org/3";

    public static RequestSpecification requestWithBody(String basePath , Object body){
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addQueryParam("api_key",System.getenv("API_KEY_MDB"))
                .setContentType(ContentType.JSON)
                .setBasePath(basePath)
                .setBody(body)
                .build();

    }

    public static RequestSpecification requestWithBodyAndSession(String basePath , Object body, String sessionId){
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addQueryParam("api_key",System.getenv("API_KEY_MDB"))
                .addQueryParam("session_id", sessionId)
                .setContentType(ContentType.JSON)
                .setBasePath(basePath)
                .setBody(body)
                .build();

    }

    public static RequestSpecification standardRequest(String basePath){
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addQueryParam("api_key",System.getenv("API_KEY_MDB"))
                .setBasePath(basePath)
                .build();
    }

    public static RequestSpecification requestWithQuery(String basePath, String queryName, String queryValue){
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addQueryParam("api_key",System.getenv("API_KEY_MDB"))
                .addQueryParam(queryName,queryValue)
                .setBasePath(basePath)
                .build();

    }

    public static RequestSpecification requestWithSessionQuery(String basePath, String sessionId,
                                                         String queryName, String queryValue){
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addQueryParam("api_key",System.getenv("API_KEY_MDB"))
                .addQueryParam("session_id",sessionId)
                .addQueryParam(queryName,queryValue)
                .setBasePath(basePath)
                .build();

    }

}
