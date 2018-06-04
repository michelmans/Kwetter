package rest.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import domain.KwetterException;
import domain.DataWrapper;
import io.jsonwebtoken.Jwts;
import org.joda.time.DateTime;
import service.ProfileService;
import util.KeyUtil;
import util.LinkBuilder;
import util.RestUtil;

import javax.crypto.KeyGenerator;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Path("/v1/profiles")
@Stateless
public class ProfileResource {

    @Inject
    ProfileService profileService;

    @GET
    @Path("{username}")
    @Produces(value = "application/json")
    public Response getProfileByUsername(@PathParam("username") String username) {
        try {
            DataWrapper message = new DataWrapper(LinkBuilder.generateProfileLinks(profileService.getProfileByUsername(username)), DataWrapper.Status.SUCCESS);
            return Response.ok(RestUtil.encode(message)).build();
        } catch (KwetterException ex) {
            DataWrapper message = new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR);
            return Response.ok(RestUtil.encode(message)).build();
        }
    }

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("/register")
    @Produces(value = "application/json")
    public Response registerProfile(@FormParam("username") String username,
                                  @FormParam("password") String password,
                                  @FormParam("bio") String bio,
                                  @FormParam("website") String website) {
        try {
            DataWrapper message = new DataWrapper(profileService.registerProfile(username, password, bio, website), DataWrapper.Status.SUCCESS);
            return Response.ok(RestUtil.encode(message)).build();
        } catch (NoSuchAlgorithmException | KwetterException ex) {
            DataWrapper message = new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR);
            return Response.ok(RestUtil.encode(message)).build();
        }
    }

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("/login")
    @Produces(value = "application/json")
    public Response loginProfile(@FormParam("username") String username,
                               @FormParam("password") String password) {
        try {
            DataWrapper message = new DataWrapper(profileService.login(username, password), DataWrapper.Status.SUCCESS);
            return Response.ok(RestUtil.encode(message)).build();
        } catch (NoSuchAlgorithmException | KwetterException ex) {
            DataWrapper message = new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR);
            return Response.ok(RestUtil.encode(message)).build();
        }
    }

    @GET
    @Path("/token/{token}")
    @Produces(value = "application/json")
    public Response getProfileByToken(@PathParam("token") String token){
        try{
            DataWrapper message = new DataWrapper(LinkBuilder.generateProfileLinks(profileService.getProfileByToken(token)), DataWrapper.Status.SUCCESS);
            return Response.ok(RestUtil.encode(message)).build();
        } catch (KwetterException ex){
            DataWrapper message = new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR);
            return Response.ok(RestUtil.encode(message)).build();
        }
    }

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("/follow")
    @Produces(value = "application/json")
    public Response followProfile(@HeaderParam("token") String follower,
                               @FormParam("following") String following) {
        try {
            DataWrapper message = new DataWrapper(LinkBuilder.generateProfileLinks(profileService.followProfile(follower, following)), DataWrapper.Status.SUCCESS);
            return Response.ok(RestUtil.encode(message)).build();
        } catch (KwetterException ex) {
            DataWrapper message = new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR);
            return Response.ok(RestUtil.encode(message)).build();
        }

    }

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("/unfollow")
    @Produces(value = "application/json")
    public Response unfollowProfile(@HeaderParam("token") String follower,
                                @FormParam("following") String following) {
        try {
            DataWrapper message = new DataWrapper(LinkBuilder.generateProfileLinks(profileService.unfollowProfile(follower, following)), DataWrapper.Status.SUCCESS);
            return Response.ok(RestUtil.encode(message)).build();
        } catch (KwetterException ex) {
            DataWrapper message = new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR);
            return Response.ok(RestUtil.encode(message)).build();
        }
    }

    @GET
    @Path("following/{id}")
    @Produces(value="application/json")
    public Response profileFollowing(@PathParam("id") String id){
        try{
            DataWrapper message = new DataWrapper(LinkBuilder.generateProfilesLinks(profileService.profileFollowing(id)), DataWrapper.Status.SUCCESS);
            return Response.ok(RestUtil.encode(message)).build();
        } catch (KwetterException ex) {
            DataWrapper message = new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR);
            return Response.ok(RestUtil.encode(message)).build();
        }
    }

    @GET
    @Path("followers/{id}")
    @Produces(value="application/json")
    public Response profileFollowers(@PathParam("id") String id){
        try{
            DataWrapper message = new DataWrapper(LinkBuilder.generateProfilesLinks(profileService.profileFollowers(id)), DataWrapper.Status.SUCCESS);
            return Response.ok(RestUtil.encode(message)).build();
        } catch (KwetterException ex) {
            DataWrapper message = new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR);
            return Response.ok(RestUtil.encode(message)).build();
        }
    }

}
