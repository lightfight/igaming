package com.lightfight.game.mix;

import java.io.File;

/**
 * 描述: </BR>
 * http://www.blogjava.net/andyliao/archive/2009/11/03/300890.html
 * <p>
 * Created by caidl on 2017/6/14/0014
 */
public class ShowTree {

    static void displayDir(File dir, String prefix) {
        System.out.println(prefix + dir.getName());

        if (dir.isFile()) {
            return;
        }

        prefix = prefix.replace("├─", "│");
        prefix = prefix.replace("└─", " ");

        File files[] = dir.listFiles();

        for (int i = 0; i < files.length; i++) {
            if (i == files.length -1 ) {
                displayDir(files[i], prefix + "└─");
            } else {
                displayDir(files[i], prefix + "├─");
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String targetDirName = "E:\\ddt2_svn\\united_channels\\test";

        displayDir(new File(targetDirName), "");
    }

}
