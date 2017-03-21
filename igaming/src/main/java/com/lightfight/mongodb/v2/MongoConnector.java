package com.lightfight.mongodb.v2;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.ArrayList;

/**
 * 描述: mongo连接器</BR>
 * <p>
 * Created by caidl on 2017/3/21/0021
 */
public class MongoConnector {

    static boolean isVerify = false;

    /**
     * ++++++++++++++++++++++++++++[开始]获取Datastore
     * Transparently map your Java entities to MongoDB documents and back.
     * http://mongodb.github.io/morphia
     * 快速上手:http://mongodb.github.io/morphia/1.3/getting-started/quick-tour/
     * https://github.com/mongodb/morphia/blob/master/morphia/src/examples/java/org/mongodb/morphia/example/QuickTour.java
     */
    public static Datastore getDatastore(String host, int port, String username, String password, String dbname) {

        MongoClient client = createClient(host,port,username,password, dbname);

        Morphia morphia = new Morphia();
//      morphia.mapPackage(basePackage); // 用来映射的包路径
        Datastore datastore = morphia.createDatastore(client, dbname);

        datastore.ensureIndexes();

        return datastore ;
    }

    public static Datastore getDatastore(String dbname) {
        String host = "localhost";
        int port = 27017;
        String username = "game";
        String password = "game";
        return getDatastore(host, port, username, password, dbname) ;
    }

    /**
     * ++++++++++++++++++++++++++++[结束]获取Datastore
     */

    /**
     * 统一的创建MongoClient
     * @param host
     * @param port
     * @param username
     * @param password
     * @param dbname
     * @return
     */
    private static MongoClient createClient(String host, int port, String username, String password, String dbname){

        // 链接服务器
        ServerAddress server = new ServerAddress(host, port);

        // 构建参数
        MongoClientOptions.Builder optionBuilder = new MongoClientOptions.Builder();
        optionBuilder.socketKeepAlive(true);
        optionBuilder.connectionsPerHost(30);
        optionBuilder.maxWaitTime(120000);
        optionBuilder.readPreference(ReadPreference.primary());
        optionBuilder.threadsAllowedToBlockForConnectionMultiplier(50);
        optionBuilder.readPreference(ReadPreference.primary());

        MongoClient client;
        if(isVerify) { // 验证模式
            // 构建凭证
            MongoCredential credentials = MongoCredential.createCredential(username, dbname, password.toCharArray());
            ArrayList credentialsList = new ArrayList();
            credentialsList.add(credentials);
            client = new MongoClient(server, credentialsList, optionBuilder.build());
        } else { // 非验证模式
            client = new MongoClient(server, optionBuilder.build());
        }

        return client;
    }

    /**
     * ++++++++++++++++++++++++++++[开始]获取Database
     */

    public static MongoDatabase getDB(String host, int port, String username, String password, String dbname) {

        MongoClient client = createClient(host,port,username,password, dbname);
        MongoDatabase db = client.getDatabase(dbname);
        return db ;
    }

    public static MongoDatabase getDB(String dbname) {
        String host = "localhost";
        int port = 27017;
        String username = "game";
        String password = "game";
        return getDB(host, port, username, password, dbname) ;
    }

    public static MongoCollection getCollection(String dbname, String collectionName) {
        MongoDatabase db = getDB(dbname);
        return db.getCollection(collectionName) ;
    }

    /**
     * ++++++++++++++++++++++++++++[结束]获取Database
     */

}
