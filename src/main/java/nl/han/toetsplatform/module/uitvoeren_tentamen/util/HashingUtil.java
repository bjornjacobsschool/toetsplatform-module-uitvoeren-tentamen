package nl.han.toetsplatform.module.uitvoeren_tentamen.util;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

public class HashingUtil {


    public static String generateHash(String input) {
        return sha1(input);
    }

    private static String sha1(String input) {
        String sha1 = null;
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
            sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
        }

        return sha1;
    }
}
