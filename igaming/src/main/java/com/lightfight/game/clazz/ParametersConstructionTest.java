package com.lightfight.game.clazz;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/3/12/0012
 */
public class ParametersConstructionTest {


    @Test
    public void testParameters() throws Exception{
        newInstanceWithParameters(SkillCfg.class);

    }

    /**
     * 通过传入构造方法参数创建对象</BR>
     * @param clazz
     * @return void
     * @author caidl
     * @date 2017/3/12/0012
     */
    private <T> void  newInstanceWithParameters(Class<T> clazz) throws Exception{
        Constructor<?>[] arr = clazz.getConstructors();
        for (Constructor<?> constructor : arr){
            Type[] types = constructor.getGenericParameterTypes();
            System.out.println("----构造方法参数的类型----");
            for (Type type : types){
                System.out.println(type.toString());
            }

            System.out.println("----设置进构造方法的值----");
            T t = (T)constructor.newInstance(100, 20); // 解析csv配置档的正确姿势,配置档的数据是不可以被修改的
            System.out.println(t);
        }
    }
}
