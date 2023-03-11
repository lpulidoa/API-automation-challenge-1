package endpoints;

public enum ListsEndpoints {
    CREATE_LIST("/list"),
    ADD_MOVIE("/list/{list_id}/add_item"),
    LIST("/list/{list_id}"),
    CLEAR_LIST("/list/{list_id}/clear");


    private String path;

    ListsEndpoints(String reqPath) {
        this.path = reqPath;
    }

    public String getPath() {
        return path;
    }
}
