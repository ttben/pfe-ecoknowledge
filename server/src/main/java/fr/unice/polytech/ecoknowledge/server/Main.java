package fr.unice.polytech.ecoknowledge.server;

import fr.unice.polytech.ecoknowledge.server.config.PortReacher;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by sebastien on 09/11/15.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        int port = PortReacher.getPort();

        if(System.getenv("PORT") != null) {
            System.out.println("\nPORT environment variable is set to " + System.getenv("PORT") + ". Try to start server on it ...");
            port = Integer.parseInt(System.getenv("PORT"));
        }

        Server jettyServer = new Server(port);
        jettyServer.setHandler(context);

        ServletHolder servlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        servlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.

        ChallengeService s = new ChallengeService();

        servlet.setInitParameter(
                "jersey.config.server.provider.classnames",

                MyExampleService.class.getCanonicalName() + ","
                        +  ChallengeService.class.getCanonicalName() +","
                        +  TestService.class.getCanonicalName());

        System.out.println("// ------- Server starting on port " + port + " ------- //");

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}
