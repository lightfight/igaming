package com.lightfight.game.project.master;

/**
 * 描述: </BR>
 * <p>
 */
interface C {

    /**
     * 淘汰掉了的物品奖励
     */
    int ZERO = 0;

    /**
     * 偶数为左边
     */
    int LEFT_SIDE = 0;

    /**
     * 奇数为右边
     */
    int RIGHT_SIDE = 1;

    /**
     * 在某一步胜利或者晋级
     */
    boolean STEP_WIN = true;

    /**
     * 在某一步失败或者淘汰
     */
    boolean STEP_LOSE = false;

    /*

    ---------------------------------
    房间状态	    |	A玩家	|	B玩家
    ---------------------------------
    未开始[-1]	|			|
    ---------------------------------
    备战中[0]	    |			|
    ---------------------------------
    房间中[1]	    |	√		|
    ---------------------------------
    准备好[2]	    |			|   √
    ---------------------------------
    战斗中[3]	    |			|
    ---------------------------------
    已结束[4]  	|			|
    ---------------------------------

    1.双方都为-1才是未开始
    2.双方都为3才是战斗中
    3.某一方为4就是已结束
    4.其它都是备战中

     */

    int ROOM_STATUS_NOT_START = -1; // 未开始
    int ROOM_STATUS_PREPARING = 0; // 备战中
    int ROOM_STATUS_IN = 1; // 房间中
    int ROOM_STATUS_READY = 2; // 准备好
    int ROOM_STATUS_BATTLE = 3; // 战斗中
    int ROOM_STATUS_FINISH = 4; // 已结束

    /**
     * 钻石的mid
     */
    int DIAMOND_MID = 297001;
}
