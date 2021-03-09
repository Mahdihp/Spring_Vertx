package com.mahdi.vertx;

import com.mahdi.vertx.entity.UsersRepository;
import com.mahdi.vertx.entity.UsersService;
import com.mahdi.vertx.entity.UsersVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class VertxApplication {
    private static final Logger logger = LoggerFactory.getLogger(VertxApplication.class);

    @Autowired
    private UsersVerticle usersVerticle;

    public static void main(String[] args) {
        SpringApplication.run(VertxApplication.class, args);
    }

    @PostConstruct
    public void deployVerticle() {
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(usersVerticle);
        int instances = 1;
        JsonObject myconfig = new JsonObject();
        DeploymentOptions options = new DeploymentOptions().setConfig(myconfig).setInstances(instances).setHa(true);

        vertx.deployVerticle(usersVerticle, options, stringAsyncResult -> {
            if (stringAsyncResult.succeeded()) {
                logger.info("UsersVerticle.class DeploymentID: " + stringAsyncResult.result());
            }else{
                logger.info("UsersVerticle.class Not Deployment: " + stringAsyncResult.cause().getMessage());
            }
        });
    }
}
