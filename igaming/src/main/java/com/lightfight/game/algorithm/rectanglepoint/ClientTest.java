package com.lightfight.game.algorithm.rectanglepoint;

import java.util.ArrayList;
import java.util.List;

public class ClientTest {

	/** 选框的边长 **/
	static int sideLen = 8;

	/** 目标点,在此次举例中为A点 **/
	static Point targetPoint = new Point(18, 17);
	
	static Point maxPoint = null;

	/** 战场上的点 **/
	static List<Point> ground = new ArrayList<>();
	static {
		// 按照X坐标从小到大一次添加
		ground.add(targetPoint); // A点
		ground.add(new Point(3, 10)); // G点,邪恶了的举手
		ground.add(new Point(6, 23)); // F点
		ground.add(new Point(12, 19)); // B点
		ground.add(new Point(21, 15)); // C点
		ground.add(new Point(24, 23)); // D点
		ground.add(new Point(25, 12)); // E点
		ground.add(new Point(26, 4)); // I点
		ground.add(new Point(29, 16)); // H点
		ground.add(new Point(18, 12)); // H点
	}

	/** 用于变换方向 **/
	static int[] directions = { -1, 1 };

	public static void main(String[] args) {

		List<Point> points = filtrate();
		combineSquare(points);

		System.out.println("maxPoint: " + maxPoint);
	}

	/**
	 * 过滤出和目标点可能在同一个选框中的点集合
	 * 
	 * @return
	 */
	public static List<Point> filtrate() {

		List<Point> points = new ArrayList<>();
		for (Point item : ground) {
			if (!item.isSame(targetPoint) && item.isSquare(targetPoint, sideLen)) {
				points.add(item);
			}
		}
		return points;
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
	public static void combineSquare(List<Point> points) {

		int maxPointCount = 0;

		int itemCount = 0;
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
					itemCount = getSquarePointCount(points, fromX, fromY, directions[i], unitY); // X做正负变
					maxPointCount = getMaxCount(maxPointCount, item, itemCount);
				}
			} else if (spaceY == 0) {
				// 计算X单元偏量
				int unitX = Math.abs(spaceX) / spaceX;
				for (int i = 0; i < directions.length; i++) {
					itemCount = getSquarePointCount(points, fromX, fromY, unitX, directions[i]); // Y做正负变
					maxPointCount = getMaxCount(maxPointCount, item, itemCount);
				}
			} else {

				// 计算单元偏量
				int unitX = Math.abs(spaceX) / spaceX;
				int unitY = Math.abs(spaceY) / spaceY;

				// 计算正方形中的点数
				itemCount = getSquarePointCount(points, fromX, fromY, unitX, unitY);

				maxPointCount = getMaxCount(maxPointCount, item, itemCount);
			}
		}
	}

	private static int getMaxCount(int maxPointCount, Point item, int itemCount) {

		if (itemCount >= maxPointCount) { // 记录下最大的
			maxPointCount = itemCount;
			maxPoint = item;
		}
		return maxPointCount;
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
	private static int getSquarePointCount(List<Point> points, int fromX, int fromY, int unitX, int unitY) {

		int count = 1; // 至少有1点,这一点就是A目标点

		// 计算终点
		int toX = fromX - unitX * sideLen;
		int toY = fromY - unitY * sideLen;

		System.out.println("fromX = " + fromX + ", fromY = " + fromY);
		System.out.println("toX = " + toX + ", toY = " + toY);

		for (Point point : points) {
			if (point.isInSquare(fromX, fromY, toX, toY)) {
				count++;
			}
		}
		System.out.println("count = " + count);
		return count;
	}
}
