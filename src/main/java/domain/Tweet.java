package domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Tweet extends Base implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column
    private String text;

    @Column
    private boolean visible;

    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @ManyToOne
    private Profile profile;

    @OneToMany
    private List<Reaction> reactions;

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
        this.visible = true;
        this.creationDate = creationDate;
        this.profile = null;
        this.reactions = new ArrayList<>();
        this.mentions = new ArrayList<>();
        this.tweets = new ArrayList<>();
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

    public boolean getVisible() { return this.visible; }

    public void setVisible(boolean visible) { this.visible = visible; }

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

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
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
