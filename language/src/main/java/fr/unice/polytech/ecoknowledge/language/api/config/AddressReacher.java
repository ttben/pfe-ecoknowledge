package fr.unice.polytech.ecoknowledge.language.api.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        input = classLoader.getResourceAsStream("server.properties");
        if(input == null){
            System.err.println("\n\t/!\\ The 'server.properties' file is not found" +
                    " in the resource folder /!\\\n");
            return null;
        }

        try {
            prop.load(input);
            res = prop.getProperty("address");
            System.out.println("\t---> Address found : '" + res + "'");
        } catch (IOException e) {
            System.err.println("\t---> Malformed server configs.");
        }
        return res;
    }
}
