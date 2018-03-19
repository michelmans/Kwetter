package boundary.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.KwetterException;
import domain.DataWrapper;
import service.ReactionService;
import service.TweetService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.ws.rs.*;

@Path("tweet")
@Stateless
public class TweetResource {

    @Inject
    TweetService tweetService;

    @Inject
    ReactionService reactionService;

    @GET
    @Path("{id}")
    @Produces(value = "application/json")
    public String getTweetById(@PathParam("id") String id){

        try{
            DataWrapper message = new DataWrapper(tweetService.getTweetById(id), DataWrapper.Status.SUCCESS);
            return new ObjectMapper().writeValueAsString(message);
        } catch ( JsonProcessingException | KwetterException ex) {
            try {
                return new ObjectMapper().writeValueAsString(new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR));
            } catch (JsonProcessingException exc){
                return "Something went horrebly wrong!";
            }
        }

    }

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

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("comment")
    @Produces(value = "application/json")
    public String postCommentOnTweet(@HeaderParam("token") String token,
                                     @FormParam("tweet") String tweet,
                                     @FormParam("parent") String parent){

        try{
            DataWrapper message = new DataWrapper(tweetService.postComment(token, tweet, parent), DataWrapper.Status.SUCCESS);
            return new ObjectMapper().writeValueAsString(message);
        } catch ( JsonProcessingException | KwetterException ex) {
            try {
                return new ObjectMapper().writeValueAsString(new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR));
            } catch (JsonProcessingException exc){
                return "Something went horrebly wrong!";
            }
        }
    }

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("react")
    @Produces(value = "application/json")
    public String reactOnTweet(@HeaderParam("token") String token,
                                     @FormParam("reactionType") String reactionType,
                                     @FormParam("tweet") String tweet){

        try{
            DataWrapper message = new DataWrapper(reactionService.addReaction(token, reactionType, tweet), DataWrapper.Status.SUCCESS);
            return new ObjectMapper().writeValueAsString(message);
        } catch ( JsonProcessingException | KwetterException ex) {
            try {
                return new ObjectMapper().writeValueAsString(new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR));
            } catch (JsonProcessingException exc){
                return "Something went horrebly wrong!";
            }
        }
    }

    @GET
    @Path("search/{keywords}")
    @Produces(value = "application/json")
    public String postCommentOnTweet(@PathParam("keywords") String keywords){

        try{
            DataWrapper message = new DataWrapper(tweetService.search(keywords), DataWrapper.Status.SUCCESS);
            return new ObjectMapper().writeValueAsString(message);
        } catch ( JsonProcessingException | KwetterException ex) {
            try {
                return new ObjectMapper().writeValueAsString(new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR));
            } catch (JsonProcessingException exc){
                return "Something went horrebly wrong!";
            }
        }
    }

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("moderate")
    @Produces(value = "application/json")
    public String moderateTweet(@HeaderParam("token") String token,
                              @FormParam("tweet") String tweet){
        try{
            DataWrapper message = new DataWrapper(tweetService.moderateTweet(token, tweet), DataWrapper.Status.SUCCESS);
            return new ObjectMapper().writeValueAsString(message);
        } catch ( JsonProcessingException | KwetterException ex) {
            try {
                return new ObjectMapper().writeValueAsString(new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR));
            } catch (JsonProcessingException exc){
                return "Something went horrebly wrong!";
            }
        }
    }

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("unmoderate")
    @Produces(value = "application/json")
    public String unmoderateTweet(@HeaderParam("token") String token,
                                @FormParam("tweet") String tweet){
        try{
            DataWrapper message = new DataWrapper(tweetService.unmoderateTweet(token, tweet), DataWrapper.Status.SUCCESS);
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
