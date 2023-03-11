package requests;

import endpoints.ListsEndpoints;
import entities.lists.MovieToList;
import entities.lists.NewList;
import io.restassured.common.mapper.TypeRef;
import utils.JSONHelper;
import utils.TMDBApi;

import java.util.Map;
import java.util.logging.Logger;


public class Lists {

    private String sessionId;
    private static final Logger log = Logger.getLogger(Lists.class.getName());

    public Lists(String sessionId){
        this.sessionId = sessionId;
    }

    public Map<String,Object> createNewList(Boolean successful) {
        log.info("Creating new list.");
        NewList body;
        if(successful)
            body = JSONHelper.fromJsonToObject("lists_data.json", "createListBody", NewList.class);
        else
            body = JSONHelper.fromJsonToObject("lists_data.json", "createExtraListBody", NewList.class);
        return TMDBApi.postWithBody(ListsEndpoints.CREATE_LIST.getPath(), body, sessionId )
                .body().as(new TypeRef<Map<String,Object>>() {});

    }

    public Map<String,Object> addMovieToList(String listId) {
        log.info("Adding movie list.");
        MovieToList body = JSONHelper.fromJsonToObject("lists_data.json","addMovieBody", MovieToList.class);
        return TMDBApi.postWithBodyPathParam(ListsEndpoints.ADD_MOVIE.getPath(), body, sessionId,
                        "list_id", listId)
                .body().as(new TypeRef<Map<String,Object>>() {});

    }

    public Map<String,Object> deleteList(String listId) {
        log.info("Deleting list.");
        return TMDBApi.deleteStandard(ListsEndpoints.LIST.getPath(), "session_id", sessionId,
                "list_id",listId)
                .body().as(new TypeRef<Map<String,Object>>() {});
    }

    public Map<String,Object> clearList(String listId) {
        log.info("Clearing list.");
        return TMDBApi.postWithQuerysPathParam(ListsEndpoints.CLEAR_LIST.getPath(), sessionId,
                "confirm","true","list_id", listId)
                .body().as(new TypeRef<Map<String,Object>>() {});
    }

    public Map<String,Object> getListDetails(String listId) {
        log.info("Getting list details.");
        return TMDBApi.getWithPathParams(ListsEndpoints.LIST.getPath(),"list_id", listId)
                .body().as(new TypeRef<Map<String,Object>>() {});
    }
}
