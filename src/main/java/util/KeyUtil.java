package util;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class KeyUtil {

    public static Key generateKey(){
        String secret = "JAVA EE JWT TOKEN GENERATION KEY!!";
        Key key = new SecretKeySpec(secret.getBytes(), 0, secret.getBytes().length, "DES");
        return key;
    }

}
