package utils;

import com.fasterxml.jackson.databind.*;

import java.io.File;
import java.io.IOException;

public class JSONHelper {
    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return defaultObjectMapper;
    }

    public static <A> A fromJsonToObject(String name, String endPoint, Class<A> clazz) {
        try {
            JsonNode root = objectMapper.readTree(new File("src/test/java/testdata/"+name));
            JsonNode body = root.path(endPoint);
            return objectMapper.treeToValue(body,clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}