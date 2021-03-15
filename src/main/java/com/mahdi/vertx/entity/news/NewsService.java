package com.mahdi.vertx.entity.news;

import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by mahdihp
 * Date: 3/14/2021 Time: 11:17
 * Github: http://github.com/mahdihp
 * Email: mahdihp.devsc@gmial.com
 */

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public void getPing(RoutingContext routingContext) {
        routingContext.end(LocalDateTime.now().toString());
    }
}
