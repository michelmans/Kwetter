package service;

import dao.EmoteDao;
import domain.Emote;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EmoteService {

    @Inject
    EmoteDao emoteDao;

    public Emote likeTweet(String token, String tweetId){
        return null;
    }

}
