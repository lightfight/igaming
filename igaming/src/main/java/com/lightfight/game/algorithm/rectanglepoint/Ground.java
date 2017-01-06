package com.lightfight.game.algorithm.rectanglepoint;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 场地
 *
 */
public class Ground {

	/** 场地上所有的点 **/
	private List<Point> points;
	
	/** 目标点 **/
	private Point targetPoint;
	
	/** 一个框中最多的点数 **/
	private int maxPointCount = 1;
	
	/** 点数最多的正方形 **/
	private Square maxSquare;
	
	/**
	 * 获取最大点数的正方形
	 * 
	 * @return
	 */
	public Square getMaxSquare() {
		
		List<Point> list = filtrate();
		combineSquare(list);
		
		return maxSquare;
	}
	
	public int getMaxPointCount() {
		return maxPointCount;
	}

	public Ground(List<Point> points, Point targetPoint) {
		this.points = points;
		this.targetPoint = targetPoint;
	}
	
	/**
	 * 过滤出和目标点可能在同一个选框中的点集合
	 * 
	 * @return
	 */
	private List<Point> filtrate() {

		List<Point> list = new ArrayList<>();
		for (Point item : points) {
			if (!item.isSame(targetPoint) && item.isSquare(targetPoint, BattleConstants.SQUARE_SIDE_LEN)) {
				list.add(item);
			}
		}
		return list;
	}
	
	/**
	 * 
	 * 组合正方形<BR>
	 * 
	 * 对每一个过滤出来的点作正方形,然后计算其中的点数<BR>
	 * 点M(Xm,Ym)与点N(Xn,Yn)的相对位置一共有9种<BR>
	 * 在X轴上,只会存在Xm<Xn,Xm=Xn,Xm>Xn这三种情况;<BR>
	 * 在Y轴上,只会存在Ym<Yn,Ym=Yn,Ym>Yn这三种情况;<BR>
	 * 不会存在同时Xm=Xn和Ym=Yn这种情况,所以可能存在的情况为3*3-1=8种;<BR>
	 * 
	 * @param points
	 */
	private void combineSquare(List<Point> points) {

		int[] directions = BattleConstants.DIRECTIONS;

		for (Point item : points) {

			// 作为起始点
			int fromX = item.getX();
			int fromY = item.getY();

			// 起始点和目标点的相对值
			int spaceX = fromX - targetPoint.getX();
			int spaceY = fromY - targetPoint.getY();

			// 计算相对值单元
			if (spaceX == 0) {
				// 计算Y单元偏量
				int unitY = Math.abs(spaceY) / spaceY;
				for (int i = 0; i < directions.length; i++) {
					calSquarePointCount(points, fromX, fromY, directions[i], unitY); // X做正负变
				}
			} else if (spaceY == 0) {
				// 计算X单元偏量
				int unitX = Math.abs(spaceX) / spaceX;
				for (int i = 0; i < directions.length; i++) {
					calSquarePointCount(points, fromX, fromY, unitX, directions[i]); // Y做正负变
				}
			} else {

				// 计算单元偏量
				int unitX = Math.abs(spaceX) / spaceX;
				int unitY = Math.abs(spaceY) / spaceY;

				// 计算正方形中的点数
				calSquarePointCount(points, fromX, fromY, unitX, unitY);
			}
		}
	}

	/**
	 * 获取在某个正方形中的点数
	 * 
	 * @param points
	 * @param fromX
	 * @param fromY
	 * @param unitX
	 * @param unitY
	 * @return
	 */
	private void calSquarePointCount(List<Point> points, int fromX, int fromY, int unitX, int unitY) {

		int count = 1; // 至少有1点,这一点就是A目标点

		// 计算终点
		int toX = fromX - unitX * BattleConstants.SQUARE_SIDE_LEN;
		int toY = fromY - unitY * BattleConstants.SQUARE_SIDE_LEN;

//		System.out.println("fromX = " + fromX + ", fromY = " + fromY);
//		System.out.println("toX = " + toX + ", toY = " + toY);

		for (Point point : points) {
			if (point.isInSquare(fromX, fromY, toX, toY)) {
				count++;
			}
		}
		
		if (count > maxPointCount) {
			maxPointCount = count;
			maxSquare = new Square(fromX, fromY, toX, toY);
		}
	}
	
}
