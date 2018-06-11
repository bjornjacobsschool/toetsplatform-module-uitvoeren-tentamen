package nl.han.toetsplatform.module.uitvoeren_tentamen.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESCipher {

    public static String decrypt(String secret, String encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted.getBytes("UTF-8"))));
    }
}
