package com.lightfight.mongodb.v1;

import com.lightfight.mongodb.v1.vo.Rune;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.query.Query;

import java.util.List;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/3/12/0012
 */
public class ClientTest {

    @Before
    public void init(){
        MongoManager.init();
    }

    @Test
    public void testInsert(){

        MongoPool pool = MongoManager.getPool();

        Rune rune = new Rune();
        rune.setName("git");

        pool.save(rune);
    }
    @Test
    public void testQueryList(){

        MongoPool pool = MongoManager.getPool();

        Query query = pool.query();
        query.field("name").equal("git");

        List<Rune> list = (List<Rune>)query.asList();
        for (Rune rune : list) {
            System.out.println(rune);
        }
    }

    @Test
    public void testQueryOne(){

        MongoPool pool = MongoManager.getPool();

        Query query = pool.query();
        query.field("name").equal("git");

        Rune rune = (Rune)query.get();
        System.out.println(rune);
    }
}
