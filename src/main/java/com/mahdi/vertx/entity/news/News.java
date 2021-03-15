package com.mahdi.vertx.entity.news;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

/**
 * Created by mahdihp
 * Date: 3/14/2021 Time: 11:16
 * Github: http://github.com/mahdihp
 * Email: mahdihp.devsc@gmial.com
 */

@Data
@Document(collection = "News")
public class News {

    @Id
    private String id;
    private String title;
}
