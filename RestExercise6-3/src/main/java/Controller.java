import java.util.Map;

import io.javalin.http.Handler;
import jakarta.servlet.http.HttpServletResponse;

public class Controller {
    public Handler getAllHeaders() {
        return ctx -> {
             Map<String, String> headers = ctx.headerMap();
                ctx.json(headers);   
        };
    }

    public static Handler getCustomHeader() {
        return ctx -> {
            HttpServletResponse response = ctx.res();  
            response.addHeader("X-My-Header", "Hello World");
        };
      }

    public static Handler getStatusCode418() {
        return ctx -> {
            HttpServletResponse response = ctx.res();
            response.setStatus(418);
        };
    }
}
