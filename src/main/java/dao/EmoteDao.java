package dao;

import domain.Emote;
import domain.KwetterException;
import domain.Profile;
import domain.Tweet;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EmoteDao {

    @PersistenceContext
    EntityManager em;

    @Inject
    ProfileDao profileDao;

    @Inject
    TweetDao tweetDao;

    public Emote likeTweet(String token, String tweetId) throws KwetterException {
        Profile profile = profileDao.getProfileByToken(token);
        Tweet tweet = tweetDao.getTweetById(tweetId);

        return null;
    }

}
