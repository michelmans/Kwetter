package domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

public class Base implements Serializable {

    private static final long serialVersionUID = 1l;

    @Transient
    @JsonProperty("_links")
    private List<LinkModel> links;

    public List<LinkModel> getLinks(){ return this.links; }

    public void setLinks(List<LinkModel> links) { this.links = links;}

    public void updateLinks(LinkModel linkModel) { this.links.add(linkModel); }

}
