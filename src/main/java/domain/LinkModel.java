package domain;

import java.io.Serializable;

public class LinkModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String href;
    private String rel;

    public LinkModel(String href, String rel){
        this.href = href;
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }
}
