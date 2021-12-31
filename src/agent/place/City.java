package agent.place;

import COVID_19.SystemParameter;

/**
 * 城市描述对象（单例模式实现）
 */
public class City {
	private int centerX;
	private int centerY;

	private static City instance;

	public static City getInstance() {
		if (instance == null) {
			instance = new City(SystemParameter.CITY_X, SystemParameter.CITY_Y);
		}
		return instance;
	}

	public City(int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
	}

	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}
}
