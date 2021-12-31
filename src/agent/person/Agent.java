package agent.person;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.context.Context;

/**
 * Agent
 * 
 * @author QinShipeng
 *
 */
public class  Agent {

	public Agent() {
	}
	double border;// 福田区边界

	public double getBorder() {
		return border;
	}

	public void setBorder(double border) {
		this.border = border;
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
	 * space空间
	 */
	ContinuousSpace<Object> space;
	/**
	 * grid栅格
	 */
	Grid<Object> grid;

	public ContinuousSpace<Object> getSpace() {
		return space;
	}

	public void setSpace(ContinuousSpace<Object> space) {
		this.space = space;
	}

	public Grid<Object> getGrid() {
		return grid;
	}

	public void setGrid(Grid<Object> grid) {
		this.grid = grid;
	}

	public Context<Object> getContext() {
		return context;
	}

	public void setContext(Context<Object> context) {
		this.context = context;
	}

	/**
	 * context环境
	 */
	Context<Object> context;

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

	public Agent(int x, int y, ContinuousSpace<Object> space, Grid<Object> grid, Context<Object> context) {
		this.x = x;
		this.y = y;
		this.space = space;
		this.grid = grid;
		this.context = context;
	}
}
