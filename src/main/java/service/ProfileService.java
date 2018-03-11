package service;

import dao.ProfileDao;
import domain.KwetterException;
import domain.Profile;
import domain.ProfileType;
import util.HashUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Stateless
public class ProfileService {

    @Inject
    ProfileDao profileDao;

    public Profile getProfileByUsername(String username) throws KwetterException {
        return profileDao.getProfileByUsername(username);
    }

    public Profile getProfileById(String id) throws KwetterException {
        return profileDao.getProfileById(id);
    }

    public Profile registerLimboProfile(String username, String password, String bio, String website) throws KwetterException, NoSuchAlgorithmException {

        HashUtil hashUtil = new HashUtil();

        String salt = hashUtil.generateSalt();
        String hashedPassword = hashUtil.hashPassword(password, salt, "SHA-256", "UTF-8");
        String token = "TEMPTOKEN";

        Profile profile = new Profile(username, website, bio, hashedPassword, salt, token);

        return profileDao.registerProfile(profile);
    }

    public Profile registerProfile(String username, String password, String bio, String website) throws KwetterException, NoSuchAlgorithmException {

        checkArguments(new String[] {username, password, bio, website});

        HashUtil hashUtil = new HashUtil();

        String salt = hashUtil.generateSalt();
        String hashedPassword = hashUtil.hashPassword(password, salt, "SHA-256", "UTF-8");
        String token = hashUtil.generateSalt();

        Profile profile = new Profile(username, website, bio, hashedPassword, salt, token);

        return profileDao.registerProfile(profile);
    }

    public String login(String username, String password) throws KwetterException, NoSuchAlgorithmException{
        checkArguments(new String[] {username, password});

        Profile profile = profileDao.getProfileByUsername(username);


        HashUtil hashUtil = new HashUtil();

        if(!hashUtil.hashPassword(password, profile.getSalt(), "SHA-256","UTF-8")
                .equals(profile.getHashedPassword())) throw new KwetterException("Username or password incorrect");

        profile.setToken(hashUtil.generateSalt());
        profileDao.updateProfile(profile);

        return profile.getToken();
    }

    public Profile upgradeProfile(String username, ProfileType profileType) throws KwetterException {

        Profile profile = profileDao.getProfileByUsername(username);
        profile.setProfileType(profileType);

        return profileDao.updateProfile(profile);
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
