package com.mahdi.vertx;

import com.mahdi.vertx.entity.news.NewsVerticle;
import com.mahdi.vertx.entity.users.UsersVerticle;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class VertxApplication {
    private static final Logger logger = LoggerFactory.getLogger(VertxApplication.class);

    @Autowired
    private UsersVerticle usersVerticle;
    @Autowired
    private NewsVerticle newsVerticle;
    @Autowired
    private Vertx vertx;

    @Bean
    public Vertx getVertx() {
        final Vertx vertx = Vertx.vertx();
        return vertx;
    }

    @Bean
    public Router getRouter(Vertx vertx) {
        return Router.router(vertx);
    }

    @Bean
    public Future<JsonObject> getJsonObject(Vertx vertx) {

        ConfigStoreOptions file = new ConfigStoreOptions().setType("file").setConfig(new JsonObject().put("path", "application.json"));
        ConfigRetriever retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions().addStore(file));
        return retriever.getConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(VertxApplication.class, args);
    }

    @PostConstruct
    public void deployVerticle() {
//        final Vertx vertx = Vertx.vertx();
        int instances = 1;
        JsonObject myconfig = new JsonObject();
        DeploymentOptions options = new DeploymentOptions().setConfig(myconfig).setInstances(instances).setHa(true);

        vertx.deployVerticle(usersVerticle, options, result -> {
            if (result.succeeded()) {
                logger.info(usersVerticle.getClass().getSimpleName() + " DeploymentID: " + result.result());
            } else {
                logger.info(usersVerticle.getClass().getSimpleName() + " Not Deployment: " + result.cause().getMessage());
            }
        });
        vertx.deployVerticle(newsVerticle, options, result -> {
            if (result.succeeded()) {
                logger.info(newsVerticle.getClass().getSimpleName() + " DeploymentID: " + result.result());
            } else {
                logger.info(newsVerticle.getClass().getSimpleName() + " Not Deployment: " + result.cause().getMessage());
            }
        });

    }
}
