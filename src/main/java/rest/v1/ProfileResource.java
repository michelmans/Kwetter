package rest.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.KwetterException;
import domain.DataWrapper;
import service.ProfileService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
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
            DataWrapper message = new DataWrapper(profileService.getProfileByUsername(username), DataWrapper.Status.SUCCESS);
            return Response.ok(new ObjectMapper().writeValueAsString(message)).build();
        } catch (KwetterException | JsonProcessingException ex) {
            try {
                return Response.ok(new ObjectMapper().writeValueAsString(new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR))).build();
            } catch (JsonProcessingException exc) {
                return Response.serverError().build();
            }
        }
    }

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("/register")
    @Produces(value = "application/json")
    public String registerProfile(@FormParam("username") String username,
                                  @FormParam("password") String password,
                                  @FormParam("bio") String bio,
                                  @FormParam("website") String website) {
        try {
            DataWrapper message = new DataWrapper(profileService.registerProfile(username, password, bio, website), DataWrapper.Status.SUCCESS);
            return new ObjectMapper().writeValueAsString(message);
        } catch (NoSuchAlgorithmException | JsonProcessingException | KwetterException ex) {
            try {
                return new ObjectMapper().writeValueAsString(new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR));
            } catch (JsonProcessingException ex1) {
                return "Something went horrebly wrong!";
            }
        }
    }

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("/login")
    @Produces(value = "application/json")
    public String loginProfile(@FormParam("username") String username,
                               @FormParam("password") String password) {
        try {
            DataWrapper message = new DataWrapper(profileService.login(username, password), DataWrapper.Status.SUCCESS);
            return new ObjectMapper().writeValueAsString(message);
        } catch (NoSuchAlgorithmException | JsonProcessingException | KwetterException ex) {
            try {
                return new ObjectMapper().writeValueAsString(new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR));
            } catch (JsonProcessingException ex1) {
                return "Something went horrebly wrong!";
            }
        }
    }

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("/follow")
    @Produces(value = "application/json")
    public String followProfile(@HeaderParam("token") String follower,
                               @FormParam("following") String following) {
        try {
            DataWrapper message = new DataWrapper(profileService.followProfile(follower, following), DataWrapper.Status.SUCCESS);
            return new ObjectMapper().writeValueAsString(message);
        } catch (JsonProcessingException | KwetterException ex) {
            try {
                return new ObjectMapper().writeValueAsString(new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR));
            } catch (JsonProcessingException ex1) {
                return "Something went horrebly wrong!";
            }
        }

    }

    @POST
    @Consumes(value = "application/x-www-form-urlencoded")
    @Path("/unfollow")
    @Produces(value = "application/json")
    public String unfollowProfile(@HeaderParam("token") String follower,
                                @FormParam("following") String following) {
        try {
            DataWrapper message = new DataWrapper(profileService.unfollowProfile(follower, following), DataWrapper.Status.SUCCESS);
            return new ObjectMapper().writeValueAsString(message);
        } catch (JsonProcessingException | KwetterException ex) {
            try {
                return new ObjectMapper().writeValueAsString(new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR));
            } catch (JsonProcessingException ex1) {
                return "Something went horrebly wrong!";
            }
        }
    }

    @GET
    @Path("following/{id}")
    @Produces(value="application/json")
    public String profileFollowing(@PathParam("id") String id){
        try{
            DataWrapper message = new DataWrapper(profileService.profileFollowing(id), DataWrapper.Status.SUCCESS);
            return new ObjectMapper().writeValueAsString(message);
        } catch (JsonProcessingException | KwetterException ex) {
            try {
                return new ObjectMapper().writeValueAsString(new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR));
            } catch (JsonProcessingException ex1) {
                return "Something went horrebly wrong!";
            }
        }
    }

    @GET
    @Path("followers/{id}")
    @Produces(value="application/json")
    public String profileFollowers(@PathParam("id") String id){
        try{
            DataWrapper message = new DataWrapper(profileService.profileFollowers(id), DataWrapper.Status.SUCCESS);
            return new ObjectMapper().writeValueAsString(message);
        } catch (JsonProcessingException | KwetterException ex) {
            try {
                return new ObjectMapper().writeValueAsString(new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR));
            } catch (JsonProcessingException ex1) {
                return "Something went horrebly wrong!";
            }
        }
    }

}
