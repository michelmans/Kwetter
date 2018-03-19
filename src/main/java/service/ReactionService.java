package service;

import dao.ReactionDao;
import dao.ProfileDao;
import dao.TweetDao;
import domain.Reaction;
import domain.KwetterException;
import domain.Profile;
import domain.Tweet;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ReactionService {

    @Inject
    ReactionDao reactionDao;

    @Inject
    ProfileDao profileDao;

    @Inject
    TweetDao tweetDao;

    public Reaction addReaction(String token, String reactionType, String tweetId) throws KwetterException {
        Profile profile = profileDao.getProfileByToken(token);
        Tweet tweet = tweetDao.getTweetById(tweetId);

        Reaction reaction = new Reaction(Reaction.ReactionType.valueOf(reactionType), profile);

        tweet.getReactions().add(reaction);

        reactionDao.addReaction(reaction);
        tweetDao.updateTweet(tweet);

        return reaction;

    }

}
