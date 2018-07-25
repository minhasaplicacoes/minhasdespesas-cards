package br.com.minhasdespesas.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class HelloMicroservice extends AbstractVerticle {

    @Override
    public void start() {
        System.out.println("Ola...s");
        Router router = Router.router(vertx);
        router.get("/").handler(rc -> rc.response().end("hello"));
        router.get("/:name").handler(this::hello);
        vertx.createHttpServer()
          .requestHandler(router::accept)
          .listen(Integer.getInteger("http.port"), System.getProperty("http.address", "0.0.0.0"));
    }

    private void hello(RoutingContext rc) {
    	String message = "Hello";
    	if(rc.pathParam("name")!= null) {
    		message += " " + rc.pathParam("name");
    	}
    	JsonObject json = new JsonObject().put("message", message);
    	rc.response()
    		.putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
    		.end(json.encode());
    }
}
