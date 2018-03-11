package service;

import dao.HashTagDao;
import dao.ProfileDao;
import dao.TweetDao;
import domain.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Stateless
public class TweetService {

    @Inject
    TweetDao tweetDao;

    @Inject
    ProfileDao profileDao;

    @Inject
    HashTagDao hashTagDao;

    public Tweet postTweet(String token, String tweet) throws KwetterException {

        checkTweetLength(tweet);

        List<Profile> mentionedProfile = convertMentions(tweet);
        List<HashTag> mentionedHashtag = convertHashtag(tweet);

        Tweet t = new Tweet(tweet, new Date());
        t.setMentions(mentionedProfile);
        t.setHashTags(mentionedHashtag);
        return tweetDao.postTweet(token, t);
    }

    public Tweet postTweetWithParent(String token, String tweet, String parent) throws KwetterException {

        checkTweetLength(tweet);

        List<Profile> mentionedProfile = convertMentions(tweet);
        List<HashTag> mentionedHashtag = convertHashtag(tweet);

        Tweet parentTweet = tweetDao.getTweetById(parent);
        Tweet t = new Tweet(tweet, new Date(), parentTweet);
        t.setMentions(mentionedProfile);
        t.setHashTags(mentionedHashtag);
        return tweetDao.postTweet(token, t);
    }

    public Tweet moderateTweet(String token, String id) throws KwetterException {
        Profile profile = profileDao.getProfileByToken(token);
        if(profile.getProfileType() != ProfileType.NORMAL) {
            Tweet tweet = tweetDao.getTweetById(id);
            tweet.setVisable(false);

            return tweetDao.updateTweet(tweet);
        }
        throw new KwetterException("You are not allowed to moderate tweets");
    }

    public Tweet unmoderateTweet(String token, String id) throws KwetterException {
        Profile profile = profileDao.getProfileByToken(token);
        if(profile.getProfileType() != ProfileType.NORMAL) {
            Tweet tweet = tweetDao.getTweetById(id);
            tweet.setVisable(true);

            return tweetDao.updateTweet(tweet);
        }
        throw new KwetterException("You are not allowed to unmoderate tweets");
    }

    public List<Tweet> search(String keywords) throws KwetterException {
        return tweetDao.search(keywords);
    }

    private List<Profile> convertMentions(String tweet) throws KwetterException {
        List<String> mentions = parseText(tweet, "@");
        List<Profile> profiles = new ArrayList<>();

        for(String profileName : mentions){
            try{
                profiles.add(profileDao.getProfileByUsername(profileName.substring(1)));
            } catch(Exception ex){
                throw new KwetterException(profileName + " is a non existing user");
            }
        }

        return profiles;
    }

    private List<HashTag> convertHashtag(String tweet) throws KwetterException {
        List<String> hashTagsRaw = parseText(tweet, "#");
        List<HashTag> hashTags = new ArrayList<>();

        for(String hashTag : hashTagsRaw){
            try{
                hashTags.add(hashTagDao.convertToHashtag(hashTag));
            } catch(Exception ex){
                throw new KwetterException("Something went wrong with " + hashTag);
            }
        }

        return hashTags;
    }

    private List<String> parseText(String text, String tag) {
        List<String> mentions = new ArrayList<>();
        Pattern pattern = Pattern.compile(tag + "\\w+");
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()){
            mentions.add(matcher.group());
        }

        return mentions;
    }

    private void checkTweetLength(String tweet) throws KwetterException{
        if(tweet.length() > 280){
            throw new KwetterException("This tweet is too long");
        }
    }

}
