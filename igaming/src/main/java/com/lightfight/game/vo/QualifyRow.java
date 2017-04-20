package com.lightfight.game.vo;

import java.util.Comparator;

public class QualifyRow {

    private String uuid;
    private String name;
    private int area;
    private long signUpTime;
    private int score;
    private int upDown;

    public QualifyRow(){}

    public QualifyRow(String uuid, String name, int area, int score, long signUpTime) {
        this.uuid = uuid;
        this.name = name;
        this.area = area;
        this.score = score;
        this.signUpTime = signUpTime;
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

    public long getSignUpTime() {
        return signUpTime;
    }

    public void setSignUpTime(long signUpTime) {
        this.signUpTime = signUpTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUpDown() {
        return upDown;
    }

    public void setUpDown(int upDown) {
        this.upDown = upDown;
    }

    // 进行排序的对象
    public static final Comparator<QualifyRow> C = new Comparator<QualifyRow>() {

        @Override
        public int compare(QualifyRow left, QualifyRow right) {
            int c = left.getScore() - right.getScore();

            if (c == 0) {

                long space = left.getSignUpTime() - right.getSignUpTime();
                c = space == 0 ? 0 : (space > 0 ? -1 : 1);
            }

            if (c == 0) {
                c = left.getUuid().compareTo(right.getUuid()) > 0 ? -1 : 1;
            }

            return -c; // 大的在前面
        }
    };

    @Override
    public String toString() {
        return "QualifyRow{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", area=" + area +
                ", signUpTime=" + signUpTime +
                ", score=" + score +
                ", upDown=" + upDown +
                '}';
    }
}
