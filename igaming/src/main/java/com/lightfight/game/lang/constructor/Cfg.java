package com.lightfight.game.lang.constructor;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/3/12/0012
 */
public class Cfg {

    /**
     * 配置档的类型应该为final的,是不允许被修改的,而且只能在有参构造方法中赋值
     */
    public final int cfgID;

    public Cfg(int cfgID) {
        this.cfgID = cfgID;
    }
}
