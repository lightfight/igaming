package com.lightfight.mongodb.v1.vo;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.io.Serializable;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/3/12/0012
 */
@Entity(value = "test_rune" , noClassnameStored = true)
public class Rune implements Serializable{

    @Id
    private ObjectId id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("id").append(" = ").append(id).append(", ");
        builder.append("name").append(" = ").append(name);
        return builder.toString();
    }
}
