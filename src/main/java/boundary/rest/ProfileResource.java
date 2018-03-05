package boundary.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.KwetterException;
import domain.DataWrapper;
import service.ProfileService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Michel on 28-2-2018.
 */

@Path("profile")
@Stateless
public class ProfileResource {

    @Inject
    ProfileService profileService;

    @GET
    @Path("/search/username/{username}")
    @Produces(value = "application/json")
    public String getProfileByUsername(@PathParam("username") String username){
        try{
            DataWrapper message = new DataWrapper(profileService.getProfileByUsername(username), DataWrapper.Status.SUCCESS);
            return new ObjectMapper().writeValueAsString(message);
        } catch(KwetterException | JsonProcessingException ex){
            try{
                return new ObjectMapper().writeValueAsString(new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR));
            } catch(JsonProcessingException exc){
                return "Something went really really really wrong.";
            }
        }
    }

    @GET
    @Path("/search/id/{id}")
    @Produces(value = "application/json")
    public String getProfileById(@PathParam("id") String id){
        try{
            DataWrapper message = new DataWrapper(profileService.getProfileById(id), DataWrapper.Status.SUCCESS);
            return new ObjectMapper().writeValueAsString(message);
        } catch(KwetterException | JsonProcessingException ex){
            try{
                return new ObjectMapper().writeValueAsString(new DataWrapper(ex.getMessage(), DataWrapper.Status.ERROR));
            } catch(JsonProcessingException exc){
                return "Something went really really really wrong.";
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
                                  @FormParam("website") String website){
        try{
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

}
