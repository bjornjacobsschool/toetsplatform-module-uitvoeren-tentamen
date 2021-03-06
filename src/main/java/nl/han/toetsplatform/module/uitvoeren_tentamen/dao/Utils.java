package nl.han.toetsplatform.module.uitvoeren_tentamen.dao;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    public static final String DOWNLOADS = "downloads";
    public static final String DOWNLOADED_TENTAMENS = "downloads" + File.separator + "tentamens";
    private static final String TOETSAPPLICATIE = "toetsapplicatie";
    private static final String GOOGLE_URL = "http://www.google.com";
    public static Logger logger = Logger.getLogger("toetsplatform-module-uitvoeren-tentamen");

    public static String getTempFolder() {
        String temp = System.getProperty("java.io.tmpdir");
        String pathname;

        // Check if tmp folder path already includes file separator or not
        if (!temp.substring(temp.length() - 1).equals(File.separator)) {
            pathname = temp + File.separator + TOETSAPPLICATIE + File.separator;
        } else {
            pathname = temp + TOETSAPPLICATIE + File.separator;
        }

        File tempFolder = new File(pathname);

        if (!tempFolder.exists()) {
            if (!tempFolder.mkdirs()) {
                System.out.println(pathname + " could not be created");
            }
        }

        return pathname;
    }

    public static String getFolder(String folder) {
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

        return pathname;
    }

    public static boolean checkInternetConnection() {
        try {
            try {
                URL url = new URL(GOOGLE_URL);
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
}
