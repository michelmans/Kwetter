package service;

import dao.ProfileDao;
import domain.KwetterException;
import domain.Profile;
import util.HashUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by Michel on 28-2-2018.
 */
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

        checkGivenInformation(username, password, bio, website);

        HashUtil hashUtil = new HashUtil();

        String salt = hashUtil.generateSalt();
        String hashedPassword = hashUtil.hashPassword(password, salt, "SHA-256", "UTF-8");
        String token = hashUtil.generateSalt();

        Profile profile = new Profile(username, website, bio, hashedPassword, salt, token);

        return profileDao.registerProfile(profile);
    }

    public Profile getProfileByToken(String token) throws KwetterException{
        return profileDao.getProfileByToken(token);
    }

    private void checkGivenInformation(String username, String website, String bio, String password) throws KwetterException {
        if(username == null || username.trim().isEmpty())
            throw new KwetterException("Invalid username");
        if(website == null || website.trim().isEmpty())
            throw new KwetterException("Invalid email");
        if(bio == null || bio.trim().isEmpty())
            throw new KwetterException("Invalid bio");
        if(password == null || password.trim().isEmpty())
            throw new KwetterException("Invalid password");
    }

}
