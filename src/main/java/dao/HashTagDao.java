package dao;

import domain.HashTag;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;

@Stateless
public class HashTagDao implements Serializable{

    @PersistenceContext
    EntityManager em;

    public HashTag convertToHashtag(String textHashTag){
        HashTag hashTag = getHashTag(textHashTag);

        if(hashTag == null){
            hashTag = new HashTag(textHashTag);
            em.persist(hashTag);
        }

        return hashTag;
    }

    public HashTag getHashTag(String text){
        try{
            TypedQuery<HashTag> query = em.createQuery("SELECT h FROM HashTag h WHERE h.text = :text", HashTag.class);
            return query.setParameter("text", text).getSingleResult();
        } catch(Exception ex){
            return null;
        }
    }


}
