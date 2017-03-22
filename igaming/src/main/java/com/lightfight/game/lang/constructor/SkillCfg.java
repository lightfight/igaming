package com.lightfight.game.lang.constructor;

import java.lang.reflect.Field;

/**
 * 描述: </BR>
 * <p>
 * Created by caidl on 2017/3/12/0012
 */
public class SkillCfg extends Cfg{

    public final int type;

    public final String name;

    public SkillCfg(int cfgID, int type, String name) {
        super(cfgID);
        this.type = type;
        this.name = name;
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
