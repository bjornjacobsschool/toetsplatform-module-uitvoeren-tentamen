package nl.han.toetsplatform.module.uitvoeren_tentamen.util;

import net.jpountz.xxhash.StreamingXXHash32;
import net.jpountz.xxhash.XXHashFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    public static final String DOWNLOADS = "downloads";
    public static final String DOWNLOADED_TENTAMENS = "downloads" + File.separator + "tentamens";
    private static final String TOETSAPPLICATIE = "toetsapplicatie";
    private static final String GOOGLE_URL = "http://www.google.com";
    public static Logger logger = Logger.getLogger("toetsplatform-module-uitvoeren-tentamen");

    public static File getFolder(String folder) {
        String temp = System.getProperty("java.io.tmpdir");
        String pathname;

        // Check if tmp folder path already includes file separator or not
        if (!temp.substring(temp.length() - 1).equals(File.separator)) {
            pathname = temp + File.separator + TOETSAPPLICATIE + File.separator + folder + File.separator;
        } else {
            pathname = temp + TOETSAPPLICATIE + File.separator + folder + File.separator;
        }

        File downloadsFolder = new File(pathname);

        if (!downloadsFolder.exists()) {
            if (!downloadsFolder.mkdirs()) {
                Utils.logger.log(Level.SEVERE, pathname + " could not be created");
            }
        }

        return downloadsFolder;
    }

    public static boolean checkInternetConnection(String url) {
        try {
            return checkInternetConnection(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean checkInternetConnection() {
        try {
            return checkInternetConnection(new URL(GOOGLE_URL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean checkInternetConnection(URL url) {
        try {
            try {
                //URL url = new URL(GOOGLE_URL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.connect();
                if (con.getResponseCode() == 200) {
                    return true;
                }
            } catch (Exception e) {
                Utils.logger.log(Level.SEVERE, e.getMessage());
                return false;
            }
        } catch (Exception e) {
            Utils.logger.log(Level.SEVERE, e.getMessage());
            return false;
        }

        return false;
    }

    public static String generateHash(String input) throws IOException {
        XXHashFactory factory = XXHashFactory.fastestInstance();

        byte[] data = input.getBytes("UTF-8");
        ByteArrayInputStream in = new ByteArrayInputStream(data);

        int seed = 0x9747b28c; // used to initialize the hash value, use whatever
        // value you want, but always the same
        StreamingXXHash32 hash32 = factory.newStreamingHash32(seed);
        byte[] buf = new byte[8192]; // for real-world usage, use a larger buffer, like 8192 bytes
        for (;;) {
            int read = in.read(buf);
            if (read == -1) {
                break;
            }
            hash32.update(buf, 0, read);
        }
        int hash = hash32.getValue();

        return String.valueOf(hash);
    }
}
