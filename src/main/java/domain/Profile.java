package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Profile extends Base implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(unique = true)
    private String username;

    @Column
    private String website;

    @Column
    private String bio;

    @Column
    private String hexColor;

    @ManyToMany
    @JoinTable(
            name="FOLLOWER",
            joinColumns=@JoinColumn(name="PROFILE", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="FOLLOWING", referencedColumnName="id"))
    @JsonIgnore
    private List<Profile> following;

    @ManyToMany(mappedBy="following")
    @JsonIgnore
    private List<Profile> followers;

    @ManyToMany(mappedBy = "profiles")
    private List<Group> groups;

    @Column
    @JsonIgnore
    private String token;

    @Column
    @JsonIgnore
    private String hashedPassword;

    @ManyToMany(mappedBy = "mentions")
    @JsonIgnore
    private List<Tweet> mentionedTweets;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Tweet> tweets;

    public Profile(){}

    public Profile(String username, String website, String bio, String hashedPassword, String token){
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.website = website;
        this.bio = bio;
        this.hexColor = "#fff";
        this.following = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.hashedPassword = hashedPassword;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    public List<Profile> getFollowing() {
        return following;
    }

    public void setFollowing(List<Profile> following) {
        this.following = following;
    }

    public List<Profile> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Profile> followers) {
        this.followers = followers;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void addGroup(Group group){
        this.groups.add(group);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public List<Tweet> getMentionedTweets() {
        return mentionedTweets;
    }

    public void setMentionedTweets(List<Tweet> mentionedTweets) {
        this.mentionedTweets = mentionedTweets;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public boolean followUser(Profile following){
        if(!this.following.contains(following)){
            return this.following.add(following);
        }
        return false;
    }

    public boolean beFollowed(Profile follower){
        if(!this.followers.contains(follower)){
            return this.followers.add(follower);
        }
        return false;
    }

    public boolean unfollowUser(Profile following){
        if(this.following.contains(following)){
            return this.following.remove(following);
        }
        return false;
    }

    public boolean beUnfollowed(Profile follower){
        if(this.followers.contains(follower)){
            return this.followers.remove(follower);
        }
        return false;
    }

    public boolean isInGroup(String groupname){
        for(Group group : groups){
            if(group.getGroupName().equals(groupname))
                return true;
        }

        return false;
    }

    public String additionalGroups(){
        if(this.groups.size() == 0) return "no groups found";
        String groupsString = "";
        for(Group group : this.groups){
            groupsString += group.getGroupName() + ", ";
        }

        if(groupsString.endsWith(", ")) groupsString = groupsString.substring(0, groupsString.length() - 2);

        return groupsString;
    }


    public List<Tweet> getTweets(int maximum) {
        if(tweets.size() < maximum) return this.tweets;

        List<Tweet> found = new ArrayList<>();

        for(int i = 0; i < maximum; i++) found.add(this.tweets.get(i));
        return found;
    }
}
