package com.lightfight.mongodb.v2;

import com.mongodb.client.MongoCollection;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/3/21/0021
 */
public class ClientTest {

    String dbName = "logic-db-refactor";

    /**
     * datastore是一种带映射关系的,需要一个类来映射,可以查看它的方法[增删改查]方法都是带class的
     */
    @Test
    public void getDatastore(){

        Datastore datastore = MongoConnector.getDatastore(dbName);


    }

    /**
     * database是一种可以获取原生document的,不需要一个类来映射
     */
    @Test
    public void getDocumnet(){

        MongoCollection blessC = MongoConnector.getCollection(dbName, "player_train");

        System.out.println(blessC.count());

    }
}
