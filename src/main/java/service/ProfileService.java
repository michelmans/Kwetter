package service;

import dao.GroupDao;
import dao.ProfileDao;
import domain.Group;
import domain.KwetterException;
import domain.Profile;
import domain.Tweet;
import util.HashUtil;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProfileService {

    @Inject
    ProfileDao profileDao;

    @Inject
    GroupDao groupDao;

    public Profile getProfileByUsername(String username) throws KwetterException {
        return profileDao.getProfileByUsername(username);
    }

    public Profile getProfileById(String id) throws KwetterException {
        return profileDao.getProfileById(id);
    }

    public List<Profile> getAllProfiles() {
        return profileDao.getAllProfiles();
    }

    public Profile registerLimboProfile(String username, String password, String bio, String website) throws KwetterException, NoSuchAlgorithmException {

        HashUtil hashUtil = new HashUtil();

        String hashedPassword = hashUtil.hashPassword(password, "SHA-256", "UTF-8");
        String token = "TEMPTOKEN";

        Profile profile = new Profile(username, website, bio, hashedPassword, token);
        Group group = groupDao.getGroupByName("members");

        group.getProfiles().add(profile);
        groupDao.update(group);
        profile.getGroups().add(group);

        return profileDao.registerProfile(profile);
    }

    public Profile registerProfile(String username, String password, String bio, String website) throws KwetterException, NoSuchAlgorithmException {

        checkArguments(new String[] {username, password, bio, website});

        HashUtil hashUtil = new HashUtil();

        String hashedPassword = hashUtil.hashPassword(password, "SHA-256", "UTF-8");
        String token = hashUtil.generateSalt();

        Profile profile = new Profile(username, website, bio, hashedPassword, token);
        Group group = groupDao.getGroupByName("members");

        group.getProfiles().add(profile);
        groupDao.update(group);
        profile.getGroups().add(group);

        return profileDao.registerProfile(profile);
    }

    public String login(String username, String password) throws KwetterException, NoSuchAlgorithmException{
        checkArguments(new String[] {username, password});

        Profile profile = profileDao.getProfileByUsername(username);


        HashUtil hashUtil = new HashUtil();

        if(!hashUtil.hashPassword(password, "SHA-256","UTF-8")
                .equals(profile.getHashedPassword())) throw new KwetterException("Username or password incorrect");

        profile.setToken(hashUtil.generateSalt());
        profileDao.updateProfile(profile);

        return profile.getToken();
    }

    public Profile promoteProfile(String username) throws KwetterException {

        Profile profile = profileDao.getProfileByUsername(username);
        Group group = groupDao.getGroupByName("admins");

        group.getProfiles().add(profile);
        profile.getGroups().add(group);
        groupDao.update(group);

        return profileDao.updateProfile(profile);
    }

    public Profile demoteProfile(String username) throws KwetterException {
        Profile profile = profileDao.getProfileByUsername(username);
        Group group = groupDao.getGroupByName("admins");

        group.getProfiles().remove(profile);
        profile.getGroups().remove(group);
        groupDao.update(group);

        return profileDao.updateProfile(profile);
    }

    public List<Tweet> getTimelineTweets(String token, int maximumTweets) throws KwetterException {
        Profile profile = profileDao.getProfileByToken(token);
        List<Tweet> tweets = new ArrayList<>();

        tweets.addAll(profile.getTweets(maximumTweets));

        for(Profile p : profile.getFollowing()){
            tweets.addAll(p.getTweets(maximumTweets));
        }

        return tweets;

    }

    public Profile getProfileByToken(String token) throws KwetterException{
        return profileDao.getProfileByToken(token);
    }

    public Profile followProfile(String token, String followingId) throws KwetterException {
        Profile profile = profileDao.getProfileByToken(token);
        Profile following = profileDao.getProfileById(followingId);

        profile.followUser(following);
        following.beFollowed(profile);
        profileDao.updateProfile(following);

        return profileDao.updateProfile(profile);

    }

    public Profile unfollowProfile(String token, String unfollowingId) throws KwetterException {
        Profile profile = profileDao.getProfileByToken(token);
        Profile following = profileDao.getProfileById(unfollowingId);

        profile.unfollowUser(following);
        following.beUnfollowed(profile);

        profileDao.updateProfile(following);
        return profileDao.updateProfile(profile);
    }

    public List<Profile> profileFollowing(String id) throws KwetterException {
        Profile profile = profileDao.getProfileById(id);

        return profile.getFollowing();
    }

    public List<Profile> profileFollowers(String id) throws KwetterException {
        Profile profile = profileDao.getProfileById(id);
        return profile.getFollowers();
    }

    private void checkArguments(String... args) throws KwetterException {

        for(String arg : args){
            if(arg == null || arg.trim().isEmpty()){
                throw new KwetterException("Argument not set");
            }
        }
    }

}
