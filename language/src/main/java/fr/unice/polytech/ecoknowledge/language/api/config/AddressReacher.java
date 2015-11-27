package fr.unice.polytech.ecoknowledge.language.api.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sebastien on 10/11/15.
 */
public class AddressReacher {

    public static String getAddress() {
        System.out.println("\n/----- Looking for address -----/");
        Properties prop = new Properties();
        InputStream input = null;
        String res = null;
        try {
            input = new FileInputStream("src/main/resources/server.properties");
        }catch (Throwable t){
            System.err.println("\t---> Can't find server configs.");
        }
        try {
            prop.load(input);
            res = prop.getProperty("address");
        } catch (IOException e) {
            System.err.println("\t---> Malformed server configs.");
        }
        return res;
    }
}
