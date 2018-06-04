package socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import domain.Profile;
import domain.Tweet;
import util.HashUtil;
import util.LinkBuilder;
import util.RestUtil;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.*;

@ServerEndpoint("/websockettimeline")
public class TimeLineEndPoint {

    private static final Map<String, Session> peers = Collections.synchronizedMap(new HashMap<String, Session>());

    public static void sendMsg(Session peer, Tweet tweet) {
        peer.getAsyncRemote().sendObject(RestUtil.encode(tweet));
    }

    public void sendToAll(Tweet tweet) {

        peers.forEach((s, session) -> {
            sendMsg(session, tweet);
        });

    }

    public static void sendToAllFollowers(Tweet tweet){

        getAllFollowerSessions(tweet.getProfile()).forEach(session -> {
            sendMsg(session, tweet);
        });

    }

    private static List<Session> getAllFollowerSessions(Profile profile) {
        List<Session> sessions = new ArrayList<>();

        profile.getFollowers().stream()
                .map(follower -> peers.get(follower.getId()))
                .filter(session -> session != null)
                .forEachOrdered(session -> sessions.add(session));

        return sessions;
    }

    @OnOpen
    public void onOpen(Session peer){
        peers.put(peer.getRequestParameterMap().get("id").get(0), peer);
        System.out.println("New connection :" + peer.getRequestParameterMap().get("id").get(0));
    }

    @OnClose
    public void onClose(Session peer){
        System.out.println("A connection has been closed");
    }

    @OnMessage
    public void onMessage(Session client, String message){
        System.out.println("Message received: " + message +
                "Received from: " + client.getRequestParameterMap().get("token").get(0));
    }

    @OnError
    public void onError(Throwable t){
        System.out.println("Error: " + t.getMessage());
    }

}
