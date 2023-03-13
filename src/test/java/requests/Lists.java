package requests;

import endpoints.ListsEndpoints;
import entities.lists.MovieToList;
import entities.lists.NewList;
import io.restassured.response.Response;
import utils.JSONHelper;
import utils.TMDBApi;

import java.util.Map;
import java.util.logging.Logger;


public class Lists extends Requests {
    private static final Logger log = Logger.getLogger(Lists.class.getName());

    public Lists(String sessionId){
        super(sessionId);
    }

    public Map<String,Object> createNewList(Boolean successful) {
        log.info("Creating new list.");
        NewList body;
        if(successful)
            body = JSONHelper.fromJsonToObject("lists_data.json", "createListBody", NewList.class);
        else
            body = JSONHelper.fromJsonToObject("lists_data.json", "createExtraListBody", NewList.class);
        Response response = TMDBApi.postWithBody(ListsEndpoints.CREATE_LIST.getPath(), body, sessionId );
        Map<String, Object> responseMap = turnIntoMap(response);

        return responseMap;
    }


    public Map<String,Object> addMovieToList(String listId) {
        log.info("Adding movie list.");
        MovieToList body = JSONHelper.fromJsonToObject("lists_data.json","addMovieBody", MovieToList.class);
        Response response = TMDBApi.postWithBodyPathParam(ListsEndpoints.ADD_MOVIE.getPath(), body, sessionId,
                "list_id", listId);
        Map<String, Object> responseMap = turnIntoMap(response);

        return responseMap;

    }


    public Map<String,Object> deleteList(String listId) {
        log.info("Deleting list.");
        Response response = TMDBApi.deleteStandard(ListsEndpoints.LIST.getPath(), "session_id", sessionId,
                "list_id",listId);
        Map<String, Object> responseMap = turnIntoMap(response);

        return responseMap;
    }


    public Map<String,Object> clearList(String listId) {
        log.info("Clearing list.");
        Response response = TMDBApi.postWithQuerysPathParam(ListsEndpoints.CLEAR_LIST.getPath(), sessionId,
                "confirm","true","list_id", listId);
        Map<String, Object> responseMap = turnIntoMap(response);

        return responseMap;
    }


    public Map<String,Object> getListDetails(String listId) {
        log.info("Getting list details.");
        Response response = TMDBApi.getWithPathParams(ListsEndpoints.LIST.getPath(),"list_id", listId);
        Map<String, Object> responseMap = turnIntoMap(response);

        return responseMap;
    }
}
