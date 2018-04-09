package controllers;

import domain.KwetterException;
import domain.Profile;
import domain.Tweet;
import service.ProfileService;
import service.TweetService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class TweetController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ProfileService profileService;
    @Inject
    private TweetService tweetService;

    public void updateTweet(String id, String state) throws KwetterException {
        if(state.equals("Moderate")){
            tweetService.moderateTweet(id);
            return;
        }
        tweetService.unmoderateTweet(id);
    }

    public String getState(String id) throws KwetterException {
        Tweet tweet = tweetService.getTweetById(id);
        if(tweet.getVisible()) return "Moderate";
        return "Unmoderate";
    }

    public List<Tweet> getAllTweets() {
        return tweetService.getAllTweets();
    }

    public List<Tweet> getAllVisibleTweets(){
        return tweetService.getAllVisibleTweets();
    }

    public List<Tweet> getTimelineTweets(String token) throws KwetterException {
        return profileService.getTimelineTweets(token, 20);
    }


}
