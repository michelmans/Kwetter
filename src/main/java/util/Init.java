package util;

import dao.ProfileDao;
import domain.KwetterException;
import domain.Profile;
import service.ProfileService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Michel on 28-2-2018.
 */
@Startup
@Singleton
public class Init {

    @Inject
    ProfileService profileService;

    @PostConstruct
    public void init() {
        try {
            profileService.registerLimboProfile("Bassie", "kek1", "Ik ben bassie hoi", "bassie.com");
        } catch (KwetterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
