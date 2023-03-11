import endpoints.Authentication;
import entities.authentication.NewSession;
import entities.authentication.ValidateTokenLogin;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import requests.Lists;

import java.util.ArrayList;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static utils.TMDBApi.*;

public class Hooks {

    protected String sessionId;
    protected String invalidSessionId;

    private static final Logger log = Logger.getLogger(Hooks.class.getName());

    @BeforeClass
    public void setUp() {
        sessionId = getSessionId();
        invalidSessionId = sessionId + "Invalid";
    }



    public String getSessionId(){

        String requestToken = getStandard(Authentication.CREATE_TOKEN.getPath()).path("request_token").toString();

        ValidateTokenLogin loginCredentials = new ValidateTokenLogin(System.getenv("USERNAME_MDB"),System.getenv("CORRECT_PSSW_MDB"),requestToken);

        Response validation = postWithBodySimple( Authentication.VALIDATE_TOKEN.getPath(), loginCredentials );
        Assert.assertTrue(validation.jsonPath().getBoolean("success"));

        NewSession generatedToken = new NewSession(requestToken);

        String sessionId = postWithBodySimple( Authentication.GET_SESSION_ID.getPath() , generatedToken ).path("session_id").toString();

        log.info("The session id is: " + sessionId);

        return sessionId;
    }

}
