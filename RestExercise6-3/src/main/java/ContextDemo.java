import io.javalin.apibuilder.EndpointGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.ArrayList;
import java.util.List;

public class ContextDemo {

    public static void main(String[] args) {
        startServer(7070);
    }

    public static void startServer(int port) {
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        applicationConfig.initiateServer().startServer(port).setRoute((getMainResource()));
    }

    private static EndpointGroup getMainResource() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", 25));
        persons.add(new Person("Jane", 30));
        persons.add(new Person("Doe", 35));
        Controller controller = new Controller();
        return () -> {
            path("/contextdemo", () -> {
                get("/test", ctx -> ctx.status(200));
                get("/hello/{name}", ctx -> {
                    ctx.result("Hello, " + ctx.pathParam("name"));
                });
                get("/headers", controller.getAllHeaders());
                get("/queryParam", ctx -> {
                    String name = ctx.queryParam("name");
                    ctx.result(name);
                });
                get("/responseHeaders", Controller.getCustomHeader());
                get("/responseStatus ", Controller.getStatusCode418());
                post("/post", ctx -> {
                    ctx.status(200);
                });
            });
            path("/persons", () -> {
                post("/create/{name}{age}", ctx -> {
                    String name = ctx.pathParam("name");
                    Integer age = ctx.pathParamAsClass("age", Integer.class).get();
                    Person person = new Person(name, age);
                    ctx.json(person);
                });
                get("/{name}", ctx -> {
                    String name = ctx.pathParam("name");
                    ctx.json(persons.stream().filter(person -> person.getName().equalsIgnoreCase(name)).findFirst().orElse(null));
                });
                put("/update/{name}{age}", ctx -> {
                    String name = ctx.pathParam("name");
                    Integer age = ctx.pathParamAsClass("age", Integer.class).get();
                    Person person = new Person(name, age);
                    ctx.json(person);
                });
            });
        };
    }

    public static void stopServer() {
        ApplicationConfig.getInstance().stopServer();
    }
}
