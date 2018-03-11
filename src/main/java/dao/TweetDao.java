package dao;

import domain.HashTag;
import domain.KwetterException;
import domain.Profile;
import domain.Tweet;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TweetDao {

    @PersistenceContext
    EntityManager em;

    @Inject
    ProfileDao profileDao;

    public Tweet postTweet(String token, Tweet tweet) throws KwetterException {

        Profile profile = profileDao.getProfileByToken(token);
        profile.getTweets().add(tweet);
        tweet.setProfile(profile);

        em.persist(tweet);
        profileDao.updateProfile(profile);

        for(HashTag tag : tweet.getHashTags()) {
            tag.getTweets().add(tweet);

            em.merge(tag);
        }

        return tweet;
    }

    public Tweet getTweetById(String id) throws KwetterException {
        try{
            TypedQuery<Tweet> query = em.createQuery("SELECT t FROM Tweet t WHERE t.id = :id", Tweet.class);
            return query.setParameter("id", id).getSingleResult();
        } catch (Exception ex){
            throw new KwetterException(id + " was not found!");
        }
    }

    public Tweet updateTweet(Tweet tweet){
        em.merge(tweet);
        return tweet;
    }

    public List<Tweet> search(String keywords) throws KwetterException {
        try{
            TypedQuery<Tweet> query = em.createQuery("SELECT t FROM Tweet t WHERE t.text = :keywords", Tweet.class);
            List<Tweet> tweets = query.setParameter("keywords", "%" + keywords + "%").getResultList();

            return tweets;
        } catch (Exception ex){
            throw new KwetterException(keywords + " was not found!");
        }
    }

}
