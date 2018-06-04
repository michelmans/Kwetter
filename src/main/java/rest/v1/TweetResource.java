package rest.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.KwetterException;
import domain.DataWrapper;
import service.ReactionService;
import service.TweetService;
import util.LinkBuilder;
import util.RestUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/v1/tweets")
@Stateless
public class TweetResource {

    @Inject
    TweetService tweetService;

    @Inject
    ReactionService reactionService;

    @GET
    @Produces(value = "application/json")
    public Response getAllTweets(){
        return Response.ok(RestUtil.encode(new DataWrapper(LinkBuilder.generateTweetsLinks(tweetService.getAllVisibleTweets()), DataWrapper.Status.SUCCESS))).build();
    }

    @GET
    @Produces(value = "application/json")
    @Path("/personalized/{id}")
    public Response getPersonalizedTweets(@PathParam("id") String id){
        try {
            return Response.ok(RestUtil.encode(new DataWrapper(LinkBuilder.generateTweetsLinks(tweetService.getPersonalizedTweets(id)), DataWrapper.Status.SUCCESS))).build();
        } catch (KwetterException e) {
            return Response.ok(RestUtil.encode(new DataWrapper("Something went wrong with getting the personalized data!", DataWrapper.Status.ERROR))).build();
        }
    }

    @GET
    @Produces(value = "application/json")
    @Path("/profile/{id}")
    public Response getProfileTweets(@PathParam("id") String id){
        try {
            return Response.ok(RestUtil.encode(new DataWrapper(LinkBuilder.generateTweetsLinks(tweetService.getProfileTweets(id)), DataWrapper.Status.SUCCESS))).build();
        } catch (KwetterException e) {
            return Response.ok(RestUtil.encode(new DataWrapper("Something went wrong with getting the profile tweets!", DataWrapper.Status.ERROR))).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(value = "application/json")
    public Response getTweetById(@PathParam("id") String id){

        try{
            DataWrapper message = new DataWrapper(LinkBuilder.generateTweetLinks(tweetService.getTweetById(id)), DataWrapper.Status.SUCCESS);
            return Response.ok(RestUtil.encode(message)).build();
        } catch (KwetterException ex) {
            DataWrapper message = new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR);
            return Response.serverError().entity(RestUtil.encode(message)).build();
        }

    }

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("post")
    @Produces(value = "application/json")
    public Response postTweet(@HeaderParam("token") String token,
                            @FormParam("tweet") String tweet){

        try{
            DataWrapper message = new DataWrapper(LinkBuilder.generateTweetLinks(tweetService.postTweet(token, tweet)), DataWrapper.Status.SUCCESS);
            return Response.ok(RestUtil.encode(message)).build();
        } catch ( KwetterException ex) {
            DataWrapper message = new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR);
            return Response.serverError().entity(RestUtil.encode(message)).build();
        }
    }

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("comment")
    @Produces(value = "application/json")
    public Response postCommentOnTweet(@HeaderParam("token") String token,
                                     @FormParam("tweet") String tweet,
                                     @FormParam("parent") String parent){

        try{
            DataWrapper message = new DataWrapper(LinkBuilder.generateTweetLinks(tweetService.postComment(token, tweet, parent)), DataWrapper.Status.SUCCESS);
            return Response.ok(RestUtil.encode(message)).build();
        } catch ( KwetterException ex) {
            DataWrapper message = new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR);
            return Response.serverError().entity(RestUtil.encode(message)).build();
        }
    }

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("react")
    @Produces(value = "application/json")
    public Response reactOnTweet(@HeaderParam("token") String token,
                                     @FormParam("reactionType") String reactionType,
                                     @FormParam("tweet") String tweet){

        try{
            DataWrapper message = new DataWrapper(reactionService.addReaction(token, reactionType, tweet), DataWrapper.Status.SUCCESS);
            return Response.ok(RestUtil.encode(message)).build();
        } catch ( KwetterException ex) {
            DataWrapper message = new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR);
            return Response.serverError().entity(RestUtil.encode(message)).build();
        }
    }

    @GET
    @Path("search/{keywords}")
    @Produces(value = "application/json")
    public Response postCommentOnTweet(@PathParam("keywords") String keywords){

        try{
            DataWrapper message = new DataWrapper(LinkBuilder.generateTweetsLinks(tweetService.search(keywords)), DataWrapper.Status.SUCCESS);
            return Response.ok(RestUtil.encode(message)).build();
        } catch ( KwetterException ex) {
            DataWrapper message = new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR);
            return Response.serverError().entity(RestUtil.encode(message)).build();
        }
    }

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("moderate")
    @Produces(value = "application/json")
    public Response moderateTweet(@FormParam("tweet") String tweet){
        try{
            DataWrapper message = new DataWrapper(LinkBuilder.generateTweetLinks(tweetService.moderateTweet(tweet)), DataWrapper.Status.SUCCESS);
            return Response.ok(RestUtil.encode(message)).build();
        } catch ( KwetterException ex) {
            DataWrapper message = new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR);
            return Response.serverError().entity(RestUtil.encode(message)).build();
        }
    }

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("unmoderate")
    @Produces(value = "application/json")
    public Response unmoderateTweet(@FormParam("tweet") String tweet){
        try{
            DataWrapper message = new DataWrapper(LinkBuilder.generateTweetLinks(tweetService.unmoderateTweet(tweet)), DataWrapper.Status.SUCCESS);
            return Response.ok(RestUtil.encode(message)).build();
        } catch ( KwetterException ex) {
            DataWrapper message = new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR);
            return Response.serverError().entity(RestUtil.encode(message)).build();
        }
    }

}
