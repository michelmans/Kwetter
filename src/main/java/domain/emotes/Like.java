package domain.emotes;

import domain.Emote;
import domain.Profile;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by Michel on 28-2-2018.
 */
@Entity
@DiscriminatorValue("LIKE")
public class Like extends Emote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column
    private String url;

    public Like(String url, String name, Profile profile){
        super(url, name, profile);
        this.url = url;
    }

    public Like(){ super(); }

}
