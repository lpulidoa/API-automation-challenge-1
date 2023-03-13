import entities.lists.NewList;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import requests.Lists;
import requests.Movies;
import utils.JSONHelper;

import java.util.Map;

import static org.apache.http.HttpStatus.*;


public class Runner extends Hooks {
    @Test
    @Feature("Lists")
    public void POST_create_list_successful(){

        Map<String,Object> payload = new Lists(sessionId).createNewList(true);
        String listId = payload.get("list_id").toString();

        Assert.assertEquals(payload.get("http_code"), SC_CREATED ,"The response was not successful");
        Assert.assertTrue(payload.get("success").equals(true),"The response was not successful");
        Assert.assertEquals(payload.get("status_message"),"The item/record was created successfully.",
                "The list was not created correctly");

        new Lists(sessionId).deleteList(listId);
    }

    @Test
    @Feature("Lists")
    public void POST_create_list_unsuccessful(){

        Map<String,Object> payload = new Lists(invalidSessionId).createNewList(false);

        Assert.assertEquals(payload.get("http_code"), SC_UNAUTHORIZED ,"The request should not be authorized");
        Assert.assertNotEquals(payload.get("success"), true, "The response should have been unsuccessful");
        Assert.assertEquals(payload.get("status_message"),"Authentication failed: You do not have permissions to access the service.",
                "The response is incorrect");

    }

    @Test
    @Feature("Lists")
    public void POST_add_movie_to_list_successful(){

        String listId = new Lists(sessionId).createNewList(true).get("list_id").toString();
        Map<String,Object> payload = new Lists(sessionId).addMovieToList(listId);

        Assert.assertEquals(payload.get("http_code"), SC_CREATED ,"The response was not successful");
        Assert.assertTrue(payload.get("success").equals(true),"The response was not successful");
        Assert.assertEquals(payload.get("status_message"),"The item/record was updated successfully.",
                "The movie was not added correctly");

        new Lists(sessionId).deleteList(listId);

    }

    @Test
    @Feature("Lists")
    public void POST_add_movie_to_list_unsuccessful(){

        String listId = new Lists(sessionId).createNewList(false).get("list_id").toString();
        String invalidListId = listId + "12345";
        Map<String,Object> payload = new Lists(sessionId).addMovieToList(invalidListId);

        Assert.assertEquals(payload.get("http_code"), SC_NOT_FOUND ,"The resource should not have been found");
        Assert.assertFalse(payload.get("success").equals(true),"The response should have been unsuccessful");
        Assert.assertEquals(payload.get("status_message"),"The resource you requested could not be found.",
                "The movie should not have been added to any list");

        new Lists(sessionId).deleteList(listId);
    }

    @Test
    @Feature("Lists")
    public void DEL_delete_list_successful() {

        String listId = new Lists(sessionId).createNewList(true).get("list_id").toString();
        Map<String,Object> payload = new Lists(sessionId).deleteList(listId);

        Assert.assertEquals(payload.get("http_code"), SC_CREATED ,"The response was not successful");
        Assert.assertEquals(payload.get("status_message"),"The item/record was updated successfully.",
                "The list was not deleted correctly");

    }

    @Test
    @Feature("Lists")
    public void DEL_delete_list_unsuccessful() {

        String listId = new Lists(sessionId).createNewList(false).get("list_id").toString();
        String invalidListId = listId + "12345";
        Map<String,Object> payload = new Lists(sessionId).deleteList(invalidListId);

        Assert.assertEquals(payload.get("http_code"), SC_NOT_FOUND ,"The resource should not have been found");
        Assert.assertFalse(payload.get("success").equals(true),"The response should have been unsuccessful");
        Assert.assertEquals(payload.get("status_message"),"The resource you requested could not be found.",
                "No list should have been deleted");

        new Lists(sessionId).deleteList(listId);

    }

    @Test
    @Feature("Lists")
    public void POST_clear_list_successful() {

        String listId = new Lists(sessionId).createNewList(true).get("list_id").toString();
        new Lists(sessionId).addMovieToList(listId);
        Map<String,Object> payload = new Lists(sessionId).clearList(listId);

        Assert.assertEquals(payload.get("http_code"), SC_CREATED ,"The response was not successful");
        Assert.assertTrue(payload.get("success").equals(true),"The response was not successful");
        Assert.assertEquals(payload.get("status_message"),"The item/record was updated successfully.",
                "The list was not cleared correctly");

        new Lists(sessionId).deleteList(listId);

    }

    @Test
    @Feature("Lists")
    public void POST_clear_list_unsuccessful() {

        String listId = new Lists(sessionId).createNewList(false).get("list_id").toString();
        new Lists(sessionId).addMovieToList(listId);
        Map<String,Object> payload = new Lists(invalidSessionId).clearList(listId);

        Assert.assertEquals(payload.get("http_code"), SC_UNAUTHORIZED ,"The request should not be authorized");
        Assert.assertFalse(payload.get("success").equals(true),"The response should have been unsuccessful");
        Assert.assertEquals(payload.get("status_message"),"Authentication failed: You do not have permissions to access the service.",
                "The request should not have cleared any list");

        new Lists(sessionId).deleteList(listId);

    }

    @Test
    @Feature("Lists")
    public void GET_list_details_successful() {

        String listId = new Lists(sessionId).createNewList(true).get("list_id").toString();
        new Lists(sessionId).addMovieToList(listId);
        Map<String,Object> payload = new Lists(sessionId).getListDetails(listId);

        Assert.assertEquals(payload.get("http_code"), SC_OK ,"The response was not successful");
        Assert.assertEquals(payload.get("id"), listId,"The wrong list details were received");
        Assert.assertEquals(payload.get("item_count"), 1, "The list does not contain expected items");
        Assert.assertEquals(payload.get("name"),
                JSONHelper.fromJsonToObject("lists_data.json", "createListBody", NewList.class).getName(),
                "The wrong name was received");

        new Lists(sessionId).deleteList(listId);

    }

    @Test
    @Feature("Lists")
    public void GET_list_details_unsuccessful() {

        String listId = new Lists(sessionId).createNewList(false).get("list_id").toString();
        new Lists(sessionId).addMovieToList(listId);
        String invalidListId = listId + "12345";
        Map<String,Object> payload = new Lists(sessionId).getListDetails(invalidListId);

        Assert.assertEquals(payload.get("http_code"), SC_NOT_FOUND ,"The resource should not have been found");
        Assert.assertFalse(payload.get("success").equals(true),"The response should have been unsuccessful");
        Assert.assertEquals(payload.get("status_message"),"The resource you requested could not be found.",
                "The details of a list should not have been received");

        new Lists(sessionId).deleteList(listId);

    }

    @Test
    @Feature("Movies")
    public void GET_movie_details_successful() {

        String movieId = "18";
        Map<String,Object> payload = new Movies(sessionId).getMovieDetails(movieId);

        Assert.assertEquals(payload.get("http_code"), SC_OK ,"The response was not successful");
        Assert.assertEquals(payload.get("id"), Integer.valueOf(movieId),"The wrong movie details were received");
        Assert.assertEquals(payload.get("status"), "Released", "The movie status is not correct");
        Assert.assertEquals(payload.get("title"), "The Fifth Element", "The movie title is not correct");

    }

    @Test
    @Feature("Movies")
    public void POST_rate_movie_successful() {

        String movieId = "18";
        Map<String,Object> payload = new Movies(sessionId).rateMovie(movieId);

        Assert.assertEquals(payload.get("http_code"), SC_CREATED ,"The response was not successful");
        Assert.assertTrue(payload.get("success").equals(true),"The response was not successful");
        Assert.assertEquals(payload.get("status_message"),"The item/record was updated successfully.",
                "The movie was not rated correctly");

    }


}
