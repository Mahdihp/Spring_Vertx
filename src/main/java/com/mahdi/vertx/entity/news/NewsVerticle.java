package com.mahdi.vertx.entity.news;

import com.mahdi.vertx.BaseVerticle;
import com.mahdi.vertx.util.Constance;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AsyncResult;
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

    public NewsVerticle(Router router, NewsService newsService) {
        this.router = router;
        this.newsService = newsService;
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
                createHttpServer(router, host, port, NewsVerticle.class.getSimpleName());
            }
        });
    }


}
