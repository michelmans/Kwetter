package boundary.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.DataWrapper;
import domain.KwetterException;
import service.EmoteService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;

@Path("emote")
@Stateless
public class EmoteResource
{

    @Inject
    EmoteService emoteService;

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("like")
    @Produces(value = "application/json")
    public String postTweet(@HeaderParam("token") String token,
                            @FormParam("tweet") String tweet){

        try{
            DataWrapper message = new DataWrapper(emoteService.likeTweet(token, tweet), DataWrapper.Status.SUCCESS);
            return new ObjectMapper().writeValueAsString(message);
        } catch ( JsonProcessingException ex) {
            try {
                return new ObjectMapper().writeValueAsString(new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR));
            } catch (JsonProcessingException exc){
                return "Something went horrebly wrong!";
            }
        }
    }
}
