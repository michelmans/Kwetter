package util;

import domain.KwetterException;
import domain.ProfileType;
import service.ProfileService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;

@Startup
@Singleton
public class Init {

    @Inject
    ProfileService profileService;

    @PostConstruct
    public void init() {
        try {
            profileService.registerLimboProfile("Bassie", "kek1", "Ik ben bassie hoi", "bassie.com");
            profileService.upgradeProfile("Bassie", ProfileType.ADMIN);
        } catch (KwetterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
