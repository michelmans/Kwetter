package util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {

    public HashUtil(){}

    public String hashPassword(String password, String salt, String algorithm, String encoding){
        try{
            if(salt == null) return null;

            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(salt.getBytes(encoding));
            byte[] bytes = md.digest(password.getBytes(encoding));

            return String.format("%064x", new BigInteger(1, bytes));
        } catch(NoSuchAlgorithmException | UnsupportedEncodingException ex){
            return null;
        }
    }

    public String generateSalt() throws NoSuchAlgorithmException{
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[32];
        sr.nextBytes(salt);

        return String.format("%064x", new BigInteger(1, salt));
    }

}
