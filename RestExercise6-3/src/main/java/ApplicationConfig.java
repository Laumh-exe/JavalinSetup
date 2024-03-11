import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;

/**
 * The ApplicationConfig class is responsible for configuring and managing the application server.
 */
public class ApplicationConfig {
    ObjectMapper om = new ObjectMapper();
    private Javalin app;
    private static ApplicationConfig instance;

    private ApplicationConfig(){};

    /**
     * Returns the singleton instance of the ApplicationConfig class.
     * @return The singleton instance of the ApplicationConfig class.
     */
    public static ApplicationConfig getInstance(){
        if(instance == null){
            instance = new ApplicationConfig();
        }
        return instance;
    }

    /**
     * Initializes the application server with default configuration settings.
     * @return The instance of the ApplicationConfig class.
     */
    public ApplicationConfig initiateServer() {
        app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
            config.routing.contextPath = "/api";
        });
        return instance;
    }

    /**
     * Starts the application server on the specified port.
     * @param port The port number on which the server should listen.
     * @return The instance of the ApplicationConfig class.
     */
    public ApplicationConfig startServer(int port) {
        app.start(port);
        return instance;
    }

    /**
     * Sets the route for handling incoming requests.
     * @param route The endpoint group that defines the routes.
     * @return The instance of the ApplicationConfig class.
     */
    public ApplicationConfig setRoute(EndpointGroup route) {
        app.routes(route);
        return instance;
    }

    /**
     * Sets the exception handling for the application server.
     * @return The instance of the ApplicationConfig class.
     */
    public ApplicationConfig setExceptionHandling() {
        app.exception(Exception.class, (e, ctx) -> {
            ObjectNode node = om.createObjectNode().put("errorMessage", e.getMessage());
            ctx.status(500).json(node);
        });
        return instance;
    }

    public void stopServer() {
        app.stop();
    }
}
