package requests;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import java.util.Map;

public class Requests {

    protected String sessionId;
    public Requests(String sessionId) {
        this.sessionId = sessionId;
    }

    protected Map<String,Object> turnIntoMap(Response response) {
        int statusCode = response.statusCode();

        Map<String, Object> responseMap = response.body().as(new TypeRef<Map<String,Object>>() {});
        responseMap.put("http_code", statusCode);

        return responseMap;
    }
}
