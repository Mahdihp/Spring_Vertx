package com.mahdi.vertx.entity.news;

import com.mahdi.vertx.BaseVerticle;
import com.mahdi.vertx.util.Constance;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.springframework.stereotype.Component;

/**
 * Created by mahdihp
 * Date: 3/14/2021 Time: 11:18
 * Github: http://github.com/mahdihp
 * Email: mahdihp.devsc@gmial.com
 */

@Component
public class NewsVerticle extends BaseVerticle {
    private final Router router;
    private final NewsService newsService;
    private final Future<JsonObject> jsonObjectFuture;
    public NewsVerticle(Router router, NewsService newsService, Future<JsonObject> jsonObjectFuture) {
        this.router = router;
        this.newsService = newsService;
        this.jsonObjectFuture = jsonObjectFuture;
    }


    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);
        Router newsRouter = getRouter();
        newsRouter.mountSubRouter(Constance.API_VER,newsRouter);
        settingAndStartServer(newsRouter);
    }

    private Router getRouter() {
        router.get(Constance.API_NEWS).handler(newsService::getPing);
        return router;
    }

    private void settingAndStartServer(Router router) {
        jsonObjectFuture.onComplete(new Handler<AsyncResult<JsonObject>>() {
            @Override
            public void handle(AsyncResult<JsonObject> event) {
                JsonObject datasourceConfig = event.result().getJsonObject("discovery");
                String host = datasourceConfig.getString("host");
                int port = datasourceConfig.getInteger("port");
                createHttpServer(router, host, port, NewsVerticle.class.getSimpleName());
            }
        });
    }


}
