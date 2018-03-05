package boundary.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.KwetterException;
import domain.DataWrapper;
import service.TweetService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;

/**
 * Created by Michel on 2-3-2018.
 */

@Path("tweet")
@Stateless
public class TweetResource {

    @Inject
    TweetService tweetService;

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("post")
    @Produces(value = "application/json")
    public String postTweet(@HeaderParam("token") String token,
                            @FormParam("tweet") String tweet){

        try{
            DataWrapper message = new DataWrapper(tweetService.postTweet(token, tweet), DataWrapper.Status.SUCCESS);
            return new ObjectMapper().writeValueAsString(message);
        } catch ( JsonProcessingException | KwetterException ex) {
            try {
                return new ObjectMapper().writeValueAsString(new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR));
            } catch (JsonProcessingException exc){
                return "Something went horrebly wrong!";
            }
        }


    }

}
