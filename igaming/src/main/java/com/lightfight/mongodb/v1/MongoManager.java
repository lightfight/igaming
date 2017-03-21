package com.lightfight.mongodb.v1;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/3/12/0012
 */
public class MongoManager {

//    private static final Map<String, MongoPool> pools = new HashMap<>();

    private static MongoPool pool;

    private MongoManager(){}

    public static void init(){

        String host = "192.168.2.62";
        int port = 27017;
        String dbname = "logic_db";
        String basePackage = "com.lightfight.mongodb";

        pool = new MongoPool(host, port, dbname, basePackage);
    }


    public static MongoPool getPool(){
        return pool;
    }
}

