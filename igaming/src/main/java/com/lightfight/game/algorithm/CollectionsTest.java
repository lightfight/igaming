package com.lightfight.game.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class CollectionsTest {

    @Test
    public void binarySearch() {

        Comparator<EndlessCfg> comparator = new Comparator<EndlessCfg>() {

            @Override
            public int compare(EndlessCfg left, EndlessCfg right) {
                return left.getWave() - right.getWave();
            }
        };

        List<EndlessCfg> list = new ArrayList<>();
        list.add(new EndlessCfg(0, 1));
        list.add(new EndlessCfg(1, 5));
        list.add(new EndlessCfg(2, 8));
        list.add(new EndlessCfg(3, 10));
        list.add(new EndlessCfg(4, 15));

        EndlessCfg cfg = new EndlessCfg(10, 23);
        int index = Collections.binarySearch(list, cfg, comparator);

        System.out.println(index);
    }

    public class EndlessCfg {

        private int id;
        private int wave;

        public EndlessCfg(int id, int wave) {
            super();
            this.id = id;
            this.wave = wave;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getWave() {
            return wave;
        }

        public void setWave(int wave) {
            this.wave = wave;
        }

    }

    @Test
    public void testSortRank() {


        List<MasterQualifyRankRow> rows = new ArrayList<>();

        MasterQualifyRankRow row;

        // 1.结合redis存储的score和跨服的snap进行封装行数据
        row = new MasterQualifyRankRow("100", "apple", 1, 200, 20L);
        rows.add(row);

        row = new MasterQualifyRankRow("120", "apple", 1, 200, 20L);
        rows.add(row);

        // 2.对行进行排序
        Collections.sort(rows, new Comparator<MasterQualifyRankRow>() {
            @Override
            public int compare(MasterQualifyRankRow left, MasterQualifyRankRow right) {
                int c = left.score - right.score;

                if (c == 0) {
                    c = left.signUpTime - right.signUpTime > 0 ? 1 : - 1;
                }

                if (c == 0) {
                    c = left.uuid.equals(right.uuid) ? 1 : -1;
                }

                return -c;
            }
        });

        // 3.显示数据
        for (MasterQualifyRankRow item : rows) {
            System.out.println(item);
        }
    }

    public class MasterQualifyRankRow {
        public final String uuid;
        public final String name;
        public final int currArea;
        public final int score;
        public final long signUpTime;

        public MasterQualifyRankRow(String uuid, String name, int currArea, int score, long signUpTime) {
            this.uuid = uuid;
            this.name = name;
            this.currArea = currArea;
            this.score = score;
            this.signUpTime = signUpTime;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("score = ").append(score).append(", ");
            builder.append("signUpTime = ").append(signUpTime).append(", ");
            builder.append("pid = ").append(uuid).append(", ");

            return builder.toString();
        }
    }
}
