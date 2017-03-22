package com.lightfight.game.lang.constructor;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/3/12/0012
 */
public class ParametersConstructionTest {

    @Test
    public void newInstanceWithParameters() throws Exception{
        newInstanceWithParameters(SkillCfg.class);
    }

    @Test
    public void showConstructorParameters() throws Exception{
        showConstructorParameters(SkillCfg.class);
    }

    @Test
    public void showFieldType() throws Exception{
        showFieldType(CfgTypes.class);

        /**
         * output:
             boolean
             int
             class [I
             class [[I
             class java.lang.String
             class [Ljava.lang.String;
             class [[Ljava.lang.String;
             interface java.util.List
         */
    }

    /**
     * 显示属性类型</BR>
     * @param clazz
     * @return void
     * @author caidl
     * @date 2017/3/14/0014 13:05
     */
    private <T> void showFieldType(Class<T> clazz) throws Exception {

        Field[] fields = clazz.getFields();
        for (Field field : fields){
            System.out.println(field.getName() + " =  " + field.getType());
        }
    }

    private <T> void showConstructorParameters(Class<T> clazz) throws Exception {
        Constructor<?>[] arr = clazz.getConstructors();
        for (Constructor<?> constructor : arr) {
            Type[] types = constructor.getGenericParameterTypes();
            for (Type type : types) {
                System.out.println(type);
                System.out.println(type.getClass().getName());
            }
        }
    }

    /**
     * 通过传入构造方法参数创建对象</BR>
     * @param clazz
     * @return void
     * @author caidl
     * @date 2017/3/12/0012
     */
    private <T> void newInstanceWithParameters(Class<T> clazz) throws Exception{
        Constructor<?>[] arr = clazz.getConstructors();
        for (Constructor<?> constructor : arr){
            Type[] types = constructor.getGenericParameterTypes();
            List<Object> parameters = new ArrayList<>();
            System.out.println("----构造方法参数的类型----");
            for (Type type : types){
                System.out.println(type);

//                if (type.equals("int")){
//                    parameters.add(100);
//                } else if (type.equals("int")) {
//                }
            }

            System.out.println("----设置进构造方法的值----");
            T t = (T)constructor.newInstance(100, 20); // 解析csv配置档的正确姿势,配置档的数据是不可以被修改的
            System.out.println(t);
        }
    }
}
