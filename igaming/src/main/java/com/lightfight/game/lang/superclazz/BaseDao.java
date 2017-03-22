package com.lightfight.game.lang.superclazz;

import java.lang.reflect.ParameterizedType;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/3/21/0021
 */
public abstract class BaseDao<T> {

    private Class<T> persistentClass;

    public BaseDao(){
        ParameterizedType type = (ParameterizedType)getClass().getGenericSuperclass();
        persistentClass = (Class<T>) type.getActualTypeArguments()[0]; // 因为我们知道这里只有一个参数

        System.out.println(persistentClass.getName());
    }

}
