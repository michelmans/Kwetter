package util;

import domain.LinkModel;
import domain.Profile;
import domain.Tweet;

import java.util.ArrayList;
import java.util.List;

public class LinkBuilder {

    private static String BASE_URL = "http://localhost:8080/kwetter/api";
    private static String VERSION_1 = "/v1";

    private static String PROFILE_URL = "/profiles/";
    private static String TWEET_URL = "/tweets/";

    public static Profile generateProfileLinks(Profile profile){
        List<LinkModel> links = new ArrayList<>();

        links.add(new LinkModel(BASE_URL + VERSION_1 + PROFILE_URL + profile.getUsername(), "self"));
        links.add(new LinkModel(BASE_URL + VERSION_1 + PROFILE_URL + "followers/" + profile.getId(), "profile.followers"));
        links.add(new LinkModel(BASE_URL + VERSION_1 + PROFILE_URL + "following/" + profile.getId(), "profile.following"));
        links.add(new LinkModel(BASE_URL + VERSION_1 + TWEET_URL + "profile/" + profile.getId(), "profile.tweets"));

        profile.setLinks(links);

        return profile;
    }

    public static Tweet generateTweetLinks(Tweet tweet) {
        List<LinkModel> links = new ArrayList<>();

        links.add(new LinkModel(BASE_URL + VERSION_1 + TWEET_URL + tweet.getId(), "self"));
        links.add(new LinkModel(BASE_URL + VERSION_1 + PROFILE_URL + tweet.getProfile().getUsername(), "tweet.profile"));
        tweet.setLinks(links);

        generateProfileLinks(tweet.getProfile());

        return tweet;
    }

    public static List<Profile> generateProfilesLinks(List<Profile> profiles) {
        profiles.forEach(profile -> {
            generateProfileLinks(profile);
        });
        return profiles;
    }

    public static List<Tweet> generateTweetsLinks(List<Tweet> tweets){
        tweets.forEach(tweet -> {
            generateTweetLinks(tweet);
            generateProfileLinks(tweet.getProfile());
        });
        return tweets;
    }

}
