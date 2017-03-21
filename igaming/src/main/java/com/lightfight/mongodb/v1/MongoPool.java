package com.lightfight.mongodb.v1;

import com.lightfight.mongodb.v1.vo.Rune;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/3/12/0012
 */
public class MongoPool {

    public final String keyName;
    private MongoClient mongo;
    private MongoDatabase db;
    private Morphia morphia;
    private Datastore ds;

    public MongoPool(String host, int port, String dbname, String basePackage){
        keyName = host + port;
        init(host,port,dbname,basePackage);
    }

    public void init(String host, int port, String dbname, String basePackage){

        // 创建参数构建器
        MongoClientOptions.Builder optionBuilder = new MongoClientOptions.Builder();
        optionBuilder.socketKeepAlive(true);
        optionBuilder.connectionsPerHost(30);
        optionBuilder.maxWaitTime(120000);
        optionBuilder.readPreference(ReadPreference.primary());
        optionBuilder.threadsAllowedToBlockForConnectionMultiplier(50);
        optionBuilder.writeConcern(WriteConcern.UNACKNOWLEDGED);
        optionBuilder.readPreference(ReadPreference.primary());

        try {
            ServerAddress e = new ServerAddress(host, port);
//            if(verify) {
//                MongoCredential credentials = MongoCredential.createCredential(auth, name, password.toCharArray());
//                ArrayList credentialsList = new ArrayList();
//                credentialsList.add(credentials);
//                mongo = new MongoClient(e, credentialsList, optionBuilder.build());
//            } else {
//                mongo = new MongoClient(e, optionBuilder.build());
//            }
            mongo = new MongoClient(e, optionBuilder.build());

            db = mongo.getDatabase(dbname);
            morphia = new Morphia();
            morphia.mapPackage(basePackage);
            ds = morphia.createDatastore(mongo, dbname);
            ds.ensureIndexes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(Rune rune){
        ds.save(rune);
    }

    public UpdateOperations<Rune> operations() {
        return ds.createUpdateOperations(Rune.class);
    }

    public Query<Rune> query() {
        return ds.createQuery(Rune.class);
    }

    public boolean up(Rune k, UpdateOperations<Rune> ops) {
        Query query = this.query();
        query.field("_id").equal(k);
        this.up(query, ops);
        return true;
    }

    public boolean up(Query<Rune> query, UpdateOperations<Rune> ops) {
        ds.update(query, ops);
        return true;
    }
}
