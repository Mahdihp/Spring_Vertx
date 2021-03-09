package com.mahdi.vertx.entity;

import io.vertx.core.eventbus.Message;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by mahdihp
 * Date: 3/8/2021 Time: 09:21
 * Github: http://github.com/mahdihp
 * Email: mahdihp.devsc@gmial.com
 */
@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    public void getPing(RoutingContext routingContext) {
        usersRepository.findAll().collectList().subscribe(new Consumer<List<Users>>() {
            @Override
            public void accept(List<Users> users) {
                routingContext.response().end(users.toString());

            }
        });
    }

    public void getPing(Message<String> stringMessage) {
        stringMessage.replyAndRequest("wwwwwwwwwwww ");
    }
}
