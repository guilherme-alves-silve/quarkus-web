package br.com.guilhermealvessilve.resource;

import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.quarkus.vertx.web.RouteFilter;
import io.quarkus.vertx.web.RoutingExchange;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.logging.Logger;

@ApplicationScoped
@RouteBase(path = "vertx")
public class GreetingResource {

    private static final Logger LOGGER = Logger.getLogger(GreetingResource.class.getSimpleName());

    public void init(@Observes Router router) {
        router.get("/vertx/hey").handler(context -> {
           context.response().end("hey");
        });
    }

    @Route(path = "/hello", methods = HttpMethod.GET)
    @Route(path = "/greeting", methods = HttpMethod.GET)
    public void getHello(RoutingContext context) {
        context.response()
                .end("hello");
    }

    @Route(path = "/test", methods = HttpMethod.GET)
    public void getTest(RoutingContext context) {
        context.response()
                .end("test");
    }

    @Route(path = "hi", methods = HttpMethod.GET)
    public void getHi(RoutingExchange exchange) {
        exchange.ok("hi");
    }

    @RouteFilter
    public void filter(RoutingContext context) {
        context.response()
                .headers()
                .add("test", "test");
        context.next();
    }
}