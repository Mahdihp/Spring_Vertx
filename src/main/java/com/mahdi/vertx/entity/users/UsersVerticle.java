package com.mahdi.vertx.entity.users;

import com.mahdi.vertx.BaseVerticle;
import com.mahdi.vertx.util.Constance;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.*;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
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
    private final Router router;
    private final UsersService usersService;
    private final Future<JsonObject> jsonObjectFuture;

    public UsersVerticle(Router router, UsersService usersService, Future<JsonObject> jsonObjectFuture) {
        this.router = router;
        this.usersService = usersService;
        this.jsonObjectFuture = jsonObjectFuture;
    }


    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);
        Router newsRouter = getRouter();
        newsRouter.mountSubRouter(Constance.API_VER, newsRouter);
        settingAndStartServer(newsRouter);
    }

    private Router getRouter() {
        router.get(Constance.API_USERS).handler(usersService::getPing);
        return router;
    }

    private void settingAndStartServer(Router router) {
        jsonObjectFuture.onComplete(new Handler<AsyncResult<JsonObject>>() {
            @Override
            public void handle(AsyncResult<JsonObject> event) {
                JsonObject datasourceConfig = event.result().getJsonObject("discovery");
                String host = datasourceConfig.getString("host");
                int port = datasourceConfig.getInteger("port");
                createHttpServer(router, host, port, UsersVerticle.class.getSimpleName());
            }
        });
    }
}
