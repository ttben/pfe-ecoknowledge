package fr.unice.polytech.ecoknowledge.server.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sebastien on 10/11/15.
 */
public class PortReacher {

    public static int getPort() {
        System.out.println("Retrieving server properties ...");

        Properties prop = new Properties();
        InputStream input = null;
        int res = 0;

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        input = classLoader.getResourceAsStream("server.properties");

        if(input == null){
            System.err.println("\n\t+! server.properties resource not found !+");
            return -1;
        }

        try {
            prop.load(input);
            res = Integer.parseInt(prop.getProperty("port"));
        } catch (IOException e) {
            System.err.println("\n\t+! Malformed server configs !");
        }
        
        return res;
    }
}
