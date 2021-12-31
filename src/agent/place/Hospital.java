package agent.place;

import COVID_19.SystemParameter;
import agent.utils.Point;
import data.source.DataStorage;
import repast.simphony.engine.schedule.ScheduledMethod;

/**
 * 医院
 */
public class Hospital extends Point {

	private static Hospital instance;
	private int centerX;
	private int centerY;
	private int bedNum;

	
	
	public Hospital(int centerX, int centerY, int bedNum) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.bedNum = bedNum;
	}

	public static Hospital getInstance() {
		if (instance == null) {
			instance = new Hospital(SystemParameter.HOSPITAL_X, SystemParameter.HOSPITAL_Y, SystemParameter.BED_COUNT);
		}
		return instance;
	}
	
	//@ScheduledMethod(start = 1, interval = 1, priority = 9)
	public void inint() {
//		System.out.println("健康者update");
		 //System.out.println("NORMAL_Num"+DataStorage.getInstance().getNORMAL_Num());
		//System.out.println("数据清除");
		 //System.out.println("HOSPITAL类,update(),优先级9");
		/**
		 * 医院数据统计
		 */
		DataStorage.getInstance().setHospital_Num(DataStorage.getInstance().getHospital_Num() + 1);
		
		
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

	public int getBedNum() {
		return bedNum;
	}

	public void setBedNum(int bedNum) {
		this.bedNum = bedNum;
	}
}
