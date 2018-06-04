package dao;

import domain.KwetterException;
import domain.Profile;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ProfileDao {

    @PersistenceContext
    EntityManager em;

    public Profile getProfileByUsername(String username) throws KwetterException{
        try{
            TypedQuery<Profile> query = em.createQuery("SELECT p FROM Profile p WHERE p.username = :username", Profile.class);
            return query.setParameter("username", username).getSingleResult();
        } catch (Exception ex){
            throw new KwetterException(username + " was not found!");
        }
    }

    public Profile getProfileById(String id) throws KwetterException {
        try{
            TypedQuery<Profile> query = em.createQuery("SELECT p FROM Profile p WHERE p.id = :id", Profile.class);
            return query.setParameter("id", id).getSingleResult();
        } catch (Exception ex){
            throw new KwetterException(id + " was not found!");
        }
    }

    public Profile getProfileByToken(String token) throws KwetterException {
        try{
            TypedQuery<Profile> query = em.createQuery("SELECT p FROM Profile p WHERE p.token = :token", Profile.class);
            return query.setParameter("token", token).getSingleResult();
        } catch (Exception ex){
            throw new KwetterException(token + " was not found!!!!!!!!!");
        }
    }

    public List<Profile> getAllProfiles() {
        return em.createQuery("SELECT p FROM Profile p", Profile.class).getResultList();
    }

    public Profile updateProfile(Profile profile){
        em.merge(profile);
        return profile;
    }

    public Profile registerProfile(Profile profile){
        em.persist(profile);
        return profile;
    }

}
