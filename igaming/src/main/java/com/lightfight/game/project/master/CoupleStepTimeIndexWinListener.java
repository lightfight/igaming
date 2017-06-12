package com.lightfight.game.project.master;

import org.junit.Test;

/**
 * 描述: </BR>
 * <p>
 */
public class CoupleStepTimeIndexWinListener {

    private int[] status = {0,1,2};

    @Test
    public void testStatus(){
        int num = 1;
        for (int leftStatus = 0; leftStatus < status.length; leftStatus++) {
            for (int rightStatus = 0; rightStatus < status.length; rightStatus++) {

                System.out.println("##NO." + num++);
                accept(leftStatus, rightStatus);
            }
        }
    }

    private void accept(int leftStatus, int rightStatus){

        int currStep = 5;
        int timeIndex = 2;

        CoupleSide leftSide = new CoupleSide("050005", "a", 5, 1, 100, leftStatus);
        CoupleSide rightSide = new CoupleSide("060006", "b", 6, 1, 100, rightStatus);

        System.out.printf("leftSide = %s, leftStatus = %s, rightSide = %s, rightStatus = %s\n", leftSide.getUuid(), leftStatus, rightSide.getUuid(), rightStatus);

        if (leftSide.getWinCount() + rightSide.getWinCount() == timeIndex) { // 如果两者没有决定出胜负

            // 只要不是[33]都在战斗中,就要决定胜负
            if (!(leftSide.getRoomStatus() == C.ROOM_STATUS_BATTLE
                    && rightSide.getRoomStatus() == C.ROOM_STATUS_BATTLE)){ // 如果两者不是同时在战斗状态中

                // 如果是[00][11][22][12,21]的情况,那么比较预选赛第二轮的积分
                // 下面的if语句可以优化为位运算
                if ((leftSide.getRoomStatus() == C.ROOM_STATUS_PREPARING && rightSide.getRoomStatus() == C.ROOM_STATUS_PREPARING)
                        ||(leftSide.getRoomStatus() == C.ROOM_STATUS_IN && rightSide.getRoomStatus() == C.ROOM_STATUS_IN)
                        ||(leftSide.getRoomStatus() == C.ROOM_STATUS_READY && rightSide.getRoomStatus() == C.ROOM_STATUS_READY)
                        ||(leftSide.getRoomStatus() == C.ROOM_STATUS_IN && rightSide.getRoomStatus() == C.ROOM_STATUS_READY)
                        ||(leftSide.getRoomStatus() == C.ROOM_STATUS_READY && rightSide.getRoomStatus() == C.ROOM_STATUS_IN)) {

                    boolean isLeftWin = true;

                    CoupleSide winSide = isLeftWin ? leftSide : rightSide;
                    CoupleSide loseSide = isLeftWin ? rightSide : leftSide;

                    System.out.printf("step = %s, winUuid = %s 胜, loseUuid = %s 败\n", currStep, winSide.getUuid(), loseSide.getUuid());

                } else { // 如果是[01,10][02,20],那么非0的胜利
                    checkPreWin(leftSide, rightSide, currStep, timeIndex);
                    checkPreWin(rightSide, leftSide, currStep, timeIndex);
                }
            }

        }
    }

    /**
     * 检查提前胜利</BR>
     * @param a
     * @param b
     * @return
     */
    private static void checkPreWin(CoupleSide a, CoupleSide b, int step, int timeIndex){
        if (b.getRoomStatus() == C.ROOM_STATUS_PREPARING){ // 如果B为[0]
            if (a.getRoomStatus() == C.ROOM_STATUS_IN
                    || a.getRoomStatus() == C.ROOM_STATUS_READY){ // 如果A为[1,2]
                System.out.printf("step = %s, A = %s 不战而胜 B = %s\n", step, a.getUuid(), b.getUuid());
            }
        }
    }
}
