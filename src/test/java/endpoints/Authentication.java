package endpoints;

public enum Authentication {
    CREATE_TOKEN("/authentication/token/new"),
    VALIDATE_TOKEN("/authentication/token/validate_with_login"),
    GET_SESSION_ID("/authentication/session/new");

    private String path;

    Authentication(String reqPath) {
        this.path = reqPath;
    }

    public String getPath() {
        return path;
    }
}
