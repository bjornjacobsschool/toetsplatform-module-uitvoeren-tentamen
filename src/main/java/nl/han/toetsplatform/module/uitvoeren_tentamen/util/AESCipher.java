package nl.han.toetsplatform.module.uitvoeren_tentamen.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESCipher {

    private AESCipher() {}

    public static String decrypt(String secret, String encrypted) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted.getBytes("UTF-8"))));
    }
}
