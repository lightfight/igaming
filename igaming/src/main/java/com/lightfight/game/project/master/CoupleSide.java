package com.lightfight.game.project.master;

/**
 * 描述: 配对的一边 </BR>
 * <p>
 */
public class CoupleSide {

    private String uuid;
    private String name;
    private int area;
    private int winCount;

    private int roomId; // 房间ID

    private int roomStatus;

    public CoupleSide() {
    }

    public CoupleSide(String uuid, String name, int area, int winCount, int roomId, int roomStatus) {
        this.uuid = uuid;
        this.name = name;
        this.area = area;
        this.winCount = winCount;
        this.roomId = roomId;
        this.roomStatus = roomStatus;
    }

    /**
     * 进入下一个房间</BR>
     * @param roomId
     * @return
     */
    public void nextRoom(int roomId){
        this.roomId = roomId;
        roomStatus = C.ROOM_STATUS_NOT_START;
        winCount = 0;
    }

    /**
     * 能否拖入战斗</BR>
     * 在房间中或者准备中,就可以拖入战斗
     * @return
     */
    public boolean dragBattle(){

        return roomStatus == C.ROOM_STATUS_IN
                || roomStatus == C.ROOM_STATUS_READY;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
    }
}
