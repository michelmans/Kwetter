package dao;

import domain.Reaction;
import domain.KwetterException;
import domain.Profile;
import domain.Tweet;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ReactionDao {

    @PersistenceContext
    EntityManager em;

    public Reaction addReaction(Reaction reaction){
        em.persist(reaction);
        return reaction;
    }



}
