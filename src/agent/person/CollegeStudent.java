package agent.person;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import COVID_19.COVID_19;
import COVID_19.MathUtil;
import COVID_19.SystemFunction;
import COVID_19.SystemParameter;
import agent.place.City;
import agent.utils.Cell;
import agent.utils.MoveTarget;
import agent.utils.Point;
import data.source.DataStorage;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.relogo.ide.dynamics.NetLogoSystemDynamicsParser.intg_return;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class CollegeStudent extends Point {
	public static ContinuousSpace<Object> space;
	public static Grid<Object> grid;
	public static City city;
	private int age;//年龄
	private MoveTarget moveTarget;
	private double temperature;
	private boolean isWearMask;//是否佩戴口罩
	private boolean isVaccination;//是否接种疫苗
	List<Cell> cellTeam = COVID_19.cellTeam;// 太原市边界
	List<Cell> TYMcellTeam = COVID_19.TYMcellTeam;// 太原面
	Random random = new Random();// 随机数
	private int intoCollegeTime;// 进入高校时刻
	private String regional; //地域

	public CollegeStudent(ContinuousSpace<Object> space, Grid<Object> grid, City city, int x, int y,int age,int intoCollegeTime,double temperature,String regional) {
		super(x, y);
		this.space = space;
		this.grid = grid;
		this.city = city;
		this.age = age;
		this.temperature = temperature;
		this.intoCollegeTime=intoCollegeTime;
		this.regional=regional;
	}


	

	public void init() {
		/**
		 * 进入高校者数据统计
		 */
		DataStorage.dataStorage.setStudent_Num(DataStorage.dataStorage.getStudent_Num() + 1);

		regionalStatistics();
		
		
	}



	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public boolean isWearMask() {
		return isWearMask;
	}

	public void setWearMask(boolean isWearMask) {
		this.isWearMask = isWearMask;
	}

	public boolean isVaccination() {
		return isVaccination;
	}

	public void setVaccination(boolean isVaccination) {
		this.isVaccination = isVaccination;
	}
	
	public void regionalStatistics() 
	{
		switch (this.regional) {
		case "XD":
			DataStorage.dataStorage.setXDCollegeStudent(DataStorage.dataStorage.getXDCollegeStudent() + 1);
			break;
		case "JCP":
			DataStorage.dataStorage.setJCPCollegeStudent(DataStorage.dataStorage.getJCPCollegeStudent() + 1);
			break;
		case "wbl":
			DataStorage.dataStorage.setWblCollegeStudent(DataStorage.dataStorage.getWblCollegeStudent() + 1);
			break;
		case "jy":
			DataStorage.dataStorage.setJyCollegeStudent(DataStorage.dataStorage.getJyCollegeStudent() + 1);
			break;
		case "XHL":
			DataStorage.dataStorage.setXHLCollegeStudent(DataStorage.dataStorage.getXHLCollegeStudent() + 1);
			break;
		case "YZ":
			DataStorage.dataStorage.setYZCollegeStudent(DataStorage.dataStorage.getYZCollegeStudent() + 1);
			break;
		default:
			
			break;
		}
	}
}
