package com.mahdi.vertx;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;


public abstract class BaseVerticle extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(BaseVerticle.class);

  protected void createHttpServer(Router router, String host, int port,String verticelName) {
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(port, host)
      .onComplete(http -> {
        if (http.succeeded()) {

          logger.info(verticelName+" HTTP Server Started On Port: " + port);
        } else {
          logger.error(verticelName+" Fail HTTP Server: " + port);
        }
      })
    .onFailure(throwable -> logger.error(verticelName+" Fail HTTP Server: " + throwable.getMessage()));
    for (Route route : router.getRoutes()) {
      logger.info(route.getPath());
    }
  }

  protected void enableCorsSupport(Router router) {
    Set<String> allowHeaders = new HashSet<>();
    allowHeaders.add("x-requested-with");
    allowHeaders.add("Access-Control-Allow-Origin");
    allowHeaders.add("origin");
    allowHeaders.add("Content-Type");
    allowHeaders.add("accept");
    // CORS support
    router.route().handler(CorsHandler.create("*")
      .allowedHeaders(allowHeaders)
      .allowedMethod(HttpMethod.GET)
      .allowedMethod(HttpMethod.POST)
      .allowedMethod(HttpMethod.DELETE)
      .allowedMethod(HttpMethod.PATCH)
      .allowedMethod(HttpMethod.PUT)
    );
  }

}
