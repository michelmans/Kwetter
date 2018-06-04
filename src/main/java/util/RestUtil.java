package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestUtil {

    public static String encode(Object o){
        try{
            return new ObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException ex){
            return "Converting to JSON has failed";
        }

    }

}
