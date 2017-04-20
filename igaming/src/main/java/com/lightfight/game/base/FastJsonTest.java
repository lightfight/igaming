package com.lightfight.game.base;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/4/5/0005
 */
public class FastJsonTest extends TestCase{


    public void testToJsonString(){

        Union union = new Union();
        union.setId(100);
        union.setName("apple");
        String str = JSON.toJSONString(union); // 会调用JavaBeanSerializer获取所有的getter
        System.out.println(str);
    }


    private class Union{

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}


