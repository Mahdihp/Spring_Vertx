package com.mahdi.vertx.entity;

import com.mahdi.vertx.BaseVerticle;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by mahdihp
 * Date: 3/8/2021 Time: 09:23
 * Github: http://github.com/mahdihp
 * Email: mahdihp.devsc@gmial.com
 */

@Component
public class UsersVerticle extends BaseVerticle {
    private static final Logger logger = LoggerFactory.getLogger(UsersVerticle.class);

    private final UsersService usersService;

    public UsersVerticle(UsersService usersService) {
        this.usersService = usersService;
        logger.info("UsersVerticle start");

    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);
        Router router = getRouter();
        settingAndStartServer(router);
    }

    private Router getRouter() {
        Router router = Router.router(vertx);
        router.get("/ping").handler(usersService::getPing);
        return router;
    }

    private void settingAndStartServer(Router router) {
        JsonObject jsonObject = new JsonObject();
        ConfigStoreOptions file = new ConfigStoreOptions().setType("file").setConfig(new JsonObject().put("path", "application.json"));
        ConfigRetriever retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions().addStore(file));
        retriever.getConfig(new Handler<AsyncResult<JsonObject>>() {
            @Override
            public void handle(AsyncResult<JsonObject> conf) {
                JsonObject datasourceConfig = conf.result().getJsonObject("discovery");
                jsonObject.put("host", datasourceConfig.getString("host"));
                jsonObject.put("port", datasourceConfig.getInteger("port"));

                String host = jsonObject.getString("host");
                int port = jsonObject.getInteger("port");
                createHttpServer(router, host, port);
            }
        });
    }
}
