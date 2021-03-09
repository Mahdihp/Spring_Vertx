package com.mahdi.vertx.entity;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mahdihp
 * Date: 3/8/2021 Time: 09:16
 * Github: http://github.com/mahdihp
 * Email: mahdihp.devsc@gmial.com
 */

@Repository
public interface UsersRepository extends ReactiveMongoRepository<Users,String> {
}
