package domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Reaction implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;

    @ManyToOne
    private Profile profile;

    public Reaction(){}

    public Reaction(ReactionType reactionType, Profile profile){
        this.id = UUID.randomUUID().toString();
        this.reactionType = reactionType;
        this.profile = profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReactionType getReactionType() {
        return reactionType;
    }

    public void setReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public enum ReactionType {
        LIKE,
        BOOS,
        SAD,
        AWESOME,
        ROFL
    }

}
