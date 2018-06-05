package nl.han.toetsplatform.module.uitvoeren_tentamen.dao;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {

    public static final String DOWNLOADS = "downloads";
    public static final String DOWNLOADED_TENTAMENS = "downloads" + File.separator + "tentamens";
    private static final String TOETSAPPLICATIE = "toetsapplicatie";

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
                System.out.println(pathname + " could not be created");
            }
        }

        return pathname;
    }

    public static boolean checkInternetConnection() {
        try {
            try {
                URL url = new URL("http://www.google.com");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.connect();
                if (con.getResponseCode() == 200) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }
}
