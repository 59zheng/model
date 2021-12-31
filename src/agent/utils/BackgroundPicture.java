package agent.utils;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class BackgroundPicture {

	// ========================== Constructors ===========================
	/**
	 * 构造函数
	 * 
	 * @param x     x坐标
	 * @param y     y坐标
	 * @param space 空间
	 * @param grid  栅格
	 */
	public BackgroundPicture(int x, int y, Context<Object> context, ContinuousSpace<Object> space, Grid<Object> grid) {
		this.x = x;
		this.y = y;
		this.context = context;
		this.space = space;
		this.grid = grid;
	}

	/**
	 * x坐标
	 */
	int x;
	/**
	 * y坐标
	 */
	int y;
	/**
	 * context环境
	 */
	Context<Object> context;
	/**
	 * space空间
	 */
	ContinuousSpace<Object> space;
	/**
	 * grid栅格
	 */
	Grid<Object> grid;

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
}
