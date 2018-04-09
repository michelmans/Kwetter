package util;

import dao.GroupDao;
import domain.Group;
import domain.KwetterException;
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

    @Inject
    GroupDao groupDao;

    @PostConstruct
    public void init() {
        try {
            groupDao.addGroup(new Group("admins"));
            groupDao.addGroup(new Group("members"));

            profileService.registerLimboProfile("Bassie", "kek1", "Ik ben bassie hoi", "bassie.com");
            profileService.promoteProfile("Bassie");
        } catch (KwetterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
