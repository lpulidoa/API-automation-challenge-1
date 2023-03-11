package entities.authentication;

public class NewSession {
    private String request_token;

    public NewSession(String request_token) {
        this.request_token = request_token;
    }

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }
}
