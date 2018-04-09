package controllers;

import domain.KwetterException;
import domain.Profile;
import service.ProfileService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ProfileController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ProfileService profileService;

    private String username;
    private String password;
    private Profile profile;
    private String token;

    private List<Profile> profiles;

    public ProfileController() {}

    @PostConstruct
    public void init() {
        refreshProfiles();
    }

    public void refreshProfiles(){
        profiles = profileService.getAllProfiles();
    }

    public void updateGroup(String username, String state) throws KwetterException {
        if(state.equals("Promote")){
            profileService.promoteProfile(username);
            refreshProfiles();
            return;
        }
        profileService.demoteProfile(username);
        refreshProfiles();
    }

    public String getState(String username) throws KwetterException {
        Profile profile = profileService.getProfileByUsername(username);
        if(profile.isInGroup("admins")) return "Demote";
        return "Promote";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }
}
