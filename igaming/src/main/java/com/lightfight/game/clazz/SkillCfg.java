package com.lightfight.game.clazz;

import java.lang.reflect.Field;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/3/12/0012
 */
public class SkillCfg extends Cfg{

    public final int type;

    public SkillCfg(int cfgID, int type) {
        super(cfgID);
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder() ;
        for(Field field : getClass().getFields()){
            try {
                builder.append(field.getName() + " = " + field.get(this) + "\n") ;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }
}
