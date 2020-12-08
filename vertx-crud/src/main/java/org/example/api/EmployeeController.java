package org.example.api;

import io.vertx.core.*;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.example.model.Employee;
import org.example.service.EmployeeServiceHandler;

import static io.vertx.core.Vertx.vertx;

public class EmployeeController extends AbstractVerticle {
    private static Vertx vertx = null;
    public static void main(String[] args) {
         vertx = Vertx.vertx();
        vertx.deployVerticle(new EmployeeController());
    }

    private EmployeeServiceHandler serviceHandler = new EmployeeServiceHandler();
    //Route router = Router.router(Vertx.vertx()).route().method(HttpMethod.GET).path("/hello").handler(this::hello);
    Router route = Router.router(vertx);

    public EmployeeController() {
        handle();
    }

    public void handle() {
        route.route().handler(BodyHandler.create());
        route.get("/hello").handler(this::hello);
        route.post("/hellos").handler(this::postEmployee);
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(route).listen(9090, event -> {
            if (event.succeeded()) {
                System.out.println("success");
            } else {
                System.out.println("failed");
            }
        });
    }

    public void hello(RoutingContext context) {
        System.out.println("inside hello");
        context.response().end("hello");
    }

    public void postEmployee(RoutingContext context) {
        System.out.println("============");
        JsonObject employeeJsonObject = context.getBodyAsJson();
        Employee employee = employeeJsonObject.mapTo(Employee.class);

        serviceHandler.insertEmplData(employee, handler -> {
            if (handler.succeeded())
                System.out.println(handler.result());
            else
                System.out.println(handler.cause());
            throw new RuntimeException(handler.cause());
        });
//        context.response().setStatusCode(200).putHeader("content-type",
//                "application/json; charset=utf-8").end(Json.encodePrettily(employee));
    }
}
