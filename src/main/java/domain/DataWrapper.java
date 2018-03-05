package domain;

import java.io.Serializable;

/**
 * Created by Michel on 28-2-2018.
 */
public class DataWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object data;
    private Status status;

    public DataWrapper(Object data, Status status){
        this.data = data;
        this.status = status;
    }

    public Object getResponse(){
        return this.data;
    }

    public void setResponse(Object response){
        this.data = response;
    }

    public Status getStatus(){
        return this.status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public enum Status{
        SUCCESS,
        ERROR
    }



}
