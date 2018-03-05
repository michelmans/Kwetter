package dao;

import domain.HashTag;
import domain.KwetterException;
import domain.Profile;
import domain.Tweet;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Michel on 28-2-2018.
 */

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

}
