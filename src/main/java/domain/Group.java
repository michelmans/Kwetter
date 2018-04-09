package domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GROUPS")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String groupName;

    @ManyToMany
    @JoinTable(name="PROFILE_GROUP",
            joinColumns = @JoinColumn(name = "groupName", referencedColumnName = "GROUPNAME"),
            inverseJoinColumns = @JoinColumn(name = "username", referencedColumnName = "USERNAME"))
    @JsonBackReference
    private List<Profile> profiles;

    public Group() {}

    public Group(String groupName){
        this.groupName = groupName;
        profiles = new ArrayList<>();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }
}
