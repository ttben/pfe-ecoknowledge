package fr.unice.polytech.ecoknowledge.integration;

import org.junit.Test;

import java.io.File;

/**
 * Created by Sébastien on 22/02/2016.
 */
public class Integration {

    @Test
    public void sendMail(){

        String integrationPath = System.getenv("INTEGRATION_HOME");
        System.out.println(integrationPath);
        String resultPath = integrationPath + "\\src\\script\\result\\success.result";
        System.out.println(resultPath);
        File resultFile = new File(resultPath);

        /*
        Mail.sendMail("[SUCCESS] pfe-ecoknowledge Integration",
                "En même temps il fait rien ...",
                "petillon.sebastien@gmail.com,benjamin.benni06@gmail.com");
				*/
    }

}
