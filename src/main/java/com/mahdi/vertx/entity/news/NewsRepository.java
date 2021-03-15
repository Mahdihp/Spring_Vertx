package com.mahdi.vertx.entity.news;

import com.mahdi.vertx.entity.users.Users;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mahdihp
 * Date: 3/14/2021 Time: 11:17
 * Github: http://github.com/mahdihp
 * Email: mahdihp.devsc@gmial.com
 */

@Repository
public interface NewsRepository extends ReactiveMongoRepository<News,String> {
}
