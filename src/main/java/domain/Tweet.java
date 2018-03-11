package domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Tweet implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column
    private String text;

    @Column
    private boolean visable;

    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @ManyToOne
    private Profile profile;

    @OneToMany
    private List<Emote> emotes;

    @ManyToMany
    private List<Profile> mentions;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Tweet> tweets;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<HashTag> hashTags;

    @ManyToOne
    @JsonBackReference
    private Tweet parent;

    public Tweet(){}

    public Tweet(String text, Date creationDate){
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.visable = true;
        this.creationDate = creationDate;
        this.profile = null;
        this.emotes = new ArrayList<>();
        this.mentions = new ArrayList<>();
        this.tweets = new ArrayList<>();
        this.hashTags = new ArrayList<>();
    }

    public Tweet(String text, Date creationDate, Tweet tweet){
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.visable = true;
        this.creationDate = creationDate;
        this.profile = null;
        this.emotes = new ArrayList<>();
        this.mentions = new ArrayList<>();
        this.tweets = new ArrayList<>();
        this.tweets.add(tweet);
        this.hashTags = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getVisable() { return this.visable; }

    public void setVisable(boolean visable) { this.visable = visable; }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Emote> getEmotes() {
        return emotes;
    }

    public void setEmotes(List<Emote> emotes) {
        this.emotes = emotes;
    }

    public List<Profile> getMentions() {
        return mentions;
    }

    public void setMentions(List<Profile> mentions) {
        this.mentions = mentions;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public List<HashTag> getHashTags() {
        return hashTags;
    }

    public void setHashTags(List<HashTag> hashTags) {
        this.hashTags = hashTags;
    }

    public Tweet getParent() {
        return parent;
    }

    public void setParent(Tweet parent) {
        this.parent = parent;
    }
}
