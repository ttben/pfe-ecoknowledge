package business.server;

import business.server.config.PortReacher;
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

        Server jettyServer = new Server(port);
        jettyServer.setHandler(context);

        ServletHolder servlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        servlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.

        ChallengeService s = new ChallengeService("BITE");

        servlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                MyExampleService.class.getCanonicalName() + ","  + ChallengeService.class.getCanonicalName());


        System.out.println("// ------- Server starting on port " + port + " ------- //");

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}
