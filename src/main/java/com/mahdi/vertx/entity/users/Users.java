package com.mahdi.vertx.entity.users;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by mahdihp
 * Date: 3/8/2021 Time: 09:14
 * Github: http://github.com/mahdihp
 * Email: mahdihp.devsc@gmial.com
 */
@Data
@Document(collection = "Users")
public class Users {

    @Id
    private String id;
    private String username;
}
