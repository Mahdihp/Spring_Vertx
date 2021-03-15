package com.mahdi.vertx;

import com.mahdi.vertx.entity.news.NewsVerticle;
import com.mahdi.vertx.entity.users.UsersVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
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

    @Bean
    public Router getRouter(){
        final Vertx vertx = Vertx.vertx();
        return Router.router(vertx);
    }

    public static void main(String[] args) {
        SpringApplication.run(VertxApplication.class, args);
    }

    @PostConstruct
    public void deployVerticle() {
        final Vertx vertx = Vertx.vertx();
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
