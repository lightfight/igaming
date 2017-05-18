package com.lightfight.consistenthash.v2;

import java.io.Serializable;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/5/17/0017
 */
class ShardInfo implements Serializable{

    private static final long serialVersionUID = 1001376699201088877L;

    public final int id;
    public final String name;
    public final String ip;
    public final int port;

    public ShardInfo(int id, String name, String ip, int port) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public String toString() {
        return "ShardInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
