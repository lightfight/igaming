package com.lightfight.game.algorithm.rectanglepoint;

public class Point {

	private int x;
	private int y;

	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * 是否和目标点targetPoint在一个边长为sideLen的正方形内<BR>
	 * 
	 * @param targetPoint
	 * @param sideLen
	 * @return
	 */
	public boolean isSquare(Point targetPoint, int sideLen){
		
		int absX = Math.abs(x - targetPoint.getX());
		int absY = Math.abs(x - targetPoint.getY());
		// 如果X和Y相差的绝对值都小于等于边长,那么这两个点一定可以在同一个正方形内
		return absX <= sideLen && absY <= sideLen;
	}
	
	/**
	 * 是否在正方形内
	 * 
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param toY
	 * @return
	 */
	public boolean isInSquare(int fromX, int fromY, int toX, int toY){
		
		boolean isX = x >= Math.min(fromX, toX) && x <= Math.max(fromX, toX); // x大于等于最小,x小于等于最大
		boolean isY = y >= Math.min(fromY, toY) && y <= Math.max(fromY, toY); // y大于等于最小,y小于等于最大
		
		return isX && isY;
	}
	
	/**
	 * 是否是同一点
	 * 
	 * @param point
	 * @return
	 */
	public boolean isSame(Point point){
		return x == point.getX() && y == point.getY();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Point point = (Point)obj;
		return isSame(point);
	}

	@Override
	public String toString() {
		return "[x = " + x + ", y = " + y + "]";
	}

}
