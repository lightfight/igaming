package com.lightfight.game.algorithm.rectanglepoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientTest2 {
	
	static Random r = new Random();
	
	public static void main(String[] args) {
		
		int count = 20;
		
		// 初始化数据
		List<Point> points = initRandomGroud(count);
		Point targetPoint = points.get(r.nextInt(points.size())); // 随机获取一个点为目标点
		
		// 构建处理对象
		Ground ground = new Ground(points, targetPoint);
		
		Square square = ground.getMaxSquare();
		int maxPointCount = ground.getMaxPointCount();
		
		// TODO 打印出一个图形出来
		
		System.out.println("square = " + square);
		System.out.println("targetPoint = " + targetPoint);
		System.out.println("maxPointCount = " + maxPointCount);
	}
	
	/**
	 * 
	 * 随机初始化战斗场地
	 * 
	 * @param count
	 * @return
	 */
	private static List<Point> initRandomGroud(int count){
		List<Point> points = new ArrayList<>();
		
		List<Integer> singlePoints = new ArrayList<>();
		int sideLen = BattleConstants.GROUND_SIDE_LEN;
		for (int row = 0; row < sideLen; row++) {
			for (int col = 0; col < sideLen; col++) {
				singlePoints.add(row * sideLen + col);
			}
		}
		
		for (int i = 0; i < count; i++) {
			int singlePoint = singlePoints.remove(r.nextInt(singlePoints.size()));
			Point point = new Point(singlePoint / sideLen, singlePoint % sideLen);
			System.out.println(point);
			points.add(point);
		}
		
		return points;
	}

}
