package com.lightfight.game.lang.constructor;

import java.util.List;

/**
 * 描述: 游戏一般支持int和string的[单个,一维,二维]数据就可以了</BR>
 * <p>
 * Created by caidl on 2017/3/14/0014
 */
public class CfgTypes {

    public boolean cfgBoolean;
    public boolean[] cfgBooleans;
    public boolean[][] cfgBooleanss;

    public int cfgInt;
    public int[] cfgInts;
    public int[][] cfgIntss;

    public long cfgLong;
    public long[] cfgLongs;
    public long[][] cfgLongss;

    public Integer cfgInteger;
    public Integer[] cfgIntegers;
    public Integer[][] cfgIntegerss;

    public String cfgStr;
    public String[] cfgStrs;
    public String[][] cfgStrss;

    public List<Integer> cfgList;

    /**
     cfgBoolean =  boolean
     cfgBooleans =  class [Z
     cfgBooleanss =  class [[Z
     cfgInt =  int
     cfgInts =  class [I
     cfgIntss =  class [[I
     cfgLong =  long
     cfgLongs =  class [J
     cfgLongss =  class [[J
     cfgInteger =  class java.lang.Integer
     cfgIntegers =  class [Ljava.lang.Integer;
     cfgIntegerss =  class [[Ljava.lang.Integer;
     cfgStr =  class java.lang.String
     cfgStrs =  class [Ljava.lang.String;
     cfgStrss =  class [[Ljava.lang.String;
     cfgList =  interface java.util.List
     */
}
