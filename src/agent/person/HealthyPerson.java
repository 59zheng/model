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
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class HealthyPerson extends Point {
	public static ContinuousSpace<Object> space;
	public static Grid<Object> grid;
	public static City city;
	private int age;//年龄
	private MoveTarget moveTarget;
	private double temperature;
	private boolean isWearMask;//是否佩戴口罩
	private boolean immuneFlag;
	private boolean isVaccination;//是否接种疫苗
	List<Cell> cellTeam = COVID_19.cellTeam;// 太原市边界
	List<Cell> TYMcellTeam = COVID_19.TYMcellTeam;// 太原面
	Random random = new Random();// 随机数
	private String regional; //地域
	/**
	 * 人群流动意愿影响系数：正态分布方差sigma
	 */
	double sig = 1;
	/**
	 * 正态分布N(mu,sigma)随机位移目标位置
	 */
	int targetXU;// x方向的均值mu
	int targetYU;// y方向的均值mu
	double targetSig = 50;// 方差sigma
	public HealthyPerson(ContinuousSpace<Object> space, Grid<Object> grid, City city, int x, int y,int age,double temperature,String regional,boolean immuneFlag) {
		super(x, y);
		this.space = space;
		this.grid = grid;
		this.city = city;
		this.age = age;
		this.temperature = temperature;
		this.regional=regional;
		this.immuneFlag=immuneFlag;
		/**
		 * 对市民的初始位置进行N(x,100)的正态分布随机
		 */
		//int XU = (int)MathUtil.stdGaussian(1000, x);
		//int YU = (int)MathUtil.stdGaussian(1000, y);
		int XU = random.nextInt(TYMcellTeam.size());
		try {
			targetXU = TYMcellTeam.get(XU).getX();
			targetYU = TYMcellTeam.get(XU).getY();
			//System.out.print("添加健康着"+TYMcellTeam.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
		
	}

	/**
	 * 流动意愿标准化
	 * <p>
	 * 根据标准正态分布生成随机人口流动意愿
	 * <p>
	 * 流动意愿标准化后判断是在0的左边还是右边从而决定是否流动。
	 * <p>
	 * 设X随机变量为服从正态分布，sigma是影响分布形态的系数，从而影响整体人群流动意愿分布 u值决定正态分布的中轴是让更多人群偏向希望流动或者希望懒惰。
	 * <p>
	 * value的推导： StdX = (X-u)/sigma X = sigma * StdX + u
	 *
	 * @return
	 */
	public boolean wantMove() {
		return MathUtil.stdGaussian(1, SystemParameter.u) > 0;
	}


	/**
	 * 不同状态下的单个人实例运动行为
	 */
	private void action() {

		if (!wantMove()) {
			return;
		}
		/**
		 * 存在流动意愿的，将进行流动，流动位移仍然遵循标准正态分布
		 */
		if (moveTarget == null || moveTarget.isArrived()) {
			/**
			 * 在想要移动并且没有目标时，将自身移动目标设置为随机生成的符合正态分布的目标点
			 */
			// 产生N(a,b)的数：Math.sqrt(b)*random.nextGaussian()+a
			//double targetX = MathUtil.stdGaussian(targetSig, targetXU);
			//double targetY = MathUtil.stdGaussian(targetSig, targetYU);
			int XU = random.nextInt(TYMcellTeam.size());
			try {
				int targetX =TYMcellTeam.get(XU).getX();
				int targetY =TYMcellTeam.get(XU).getY();
				//根据地图信息随机产生移动点
				moveTarget = new MoveTarget(targetX,targetY);
			} catch (Exception e) {
				//System.out.print("IndexOutOfBoundsException");
			}
			
			

		}

		/**
		 * 计算运动位移
		 */
		int dX = moveTarget.getX() - getX();
		int dY = moveTarget.getY() - getY();

		double length = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));// 与目标点的距离

		if (length < 1) {
			/**
			 * 判断是否到达目标点
			 */
			moveTarget.setArrived(true);
			return;
		}
		/**
		 * x轴dX为位移量，符号为沿x轴前进方向, 即udX为X方向表示量
		 */
		int udX = (int) (dX / length);
		if (udX == 0 && dX != 0) {
			if (dX > 0) {
				udX = 1;
			} else {
				udX = -1;
			}
		}
		

		/**
		 * y轴dY为位移量，符号为沿x轴前进方向，即udY为Y方向表示量
		 */
		int udY = (int) (dY / length);
		// FIXED: 修正一处错误
		if (udY == 0 && dY != 0) {
			if (dY > 0) {
				udY = 1;
			} else {
				udY = -1;
			}
		}
		
		/**
		 * 横向运动边界
		 */
		
		int xMax = cellTeam.stream().max(Comparator.comparing(Cell::getX)).get().getX();
		int xMin = cellTeam.stream().min(Comparator.comparing(Cell::getX)).get().getX();
		int yMax = cellTeam.stream().max(Comparator.comparing(Cell::getY)).get().getY();
		int yMin = cellTeam.stream().min(Comparator.comparing(Cell::getY)).get().getY();
			if (getX() >xMax|| getX() <xMin) {
				moveTarget = null;
				if (udX > 0) {
					udX = -udX;
				}
			}
			/**
			 * 纵向运动边界
			 */
			if (getY() > yMax|| getY() <yMin) {
				moveTarget = null;
				if (udY > 0) {
					udY = -udY;
				}
			}
				
		
		moveTo(udX, udY);
		grid.moveTo(this, this.getX(), this.getY());
	}

	/**
	 * 对各种状态的人进行不同的处理，更新发布市民健康状态
	 
	 *注解含义start从第几天开始 interval隔几天执行一次，priorty执行优先级
	 *
	 */
	
	@ScheduledMethod(start = 1, interval = 1)
	public void update() {
		//System.out.println("健康者移动");
		//init();
		/**
		 * 处理未健康者的移动问题
		 */
		action();
	}

	public void init() {
		/**
		 * 健康者数据统计
		 */
		DataStorage.dataStorage.setNORMAL_Num(DataStorage.dataStorage.getNORMAL_Num() + 1);
		//System.out.println("健康者数据统计=================="+DataStorage.dataStorage.getNORMAL_Num());
		
		//防控措施
		prevention();
/**
 * 健康者分年龄段统计
*/
		ageStatistics();
		regionalStatistics();
		
		
	}
	/**
	 * 防控措施
	 */
	public void  prevention() {
		//是否佩戴口罩
				boolean wearMask = SystemFunction.isWearMask();
				this.setWearMask(wearMask);
				//是否接种疫苗
				boolean vaccination = SystemFunction.isVaccination();
				this.setVaccination(vaccination);
				//更新数据
				if (wearMask) {
					DataStorage.dataStorage.setWEAR_MASK_Num(DataStorage.dataStorage.getWEAR_MASK_Num() + 1);
				}
				if (vaccination) {
					DataStorage.dataStorage.setVACCINATION_Num(DataStorage.dataStorage.getVACCINATION_Num() + 1);
				}
				if (vaccination&&wearMask) {
					DataStorage.dataStorage.setVACCINATION_MASK_Num(DataStorage.dataStorage.getVACCINATION_MASK_Num() + 1);
				
				}
	}
	/**
	 * 健康者分年龄段统计
	 */
	public void ageStatistics() {
		if (this.age >= 0 && this.age < 5) {
			DataStorage.dataStorage.setAge_0_4_HealthyPerson_Num(DataStorage.dataStorage.getAge_0_4_HealthyPerson_Num() + 1);
		}else if(this.age >= 5 && this.age < 10){
			DataStorage.dataStorage.setAge_5_9_HealthyPerson_Num(DataStorage.dataStorage.getAge_5_9_HealthyPerson_Num() + 1);
		}else if(this.age >= 10 && this.age < 15){
			DataStorage.dataStorage.setAge_10_14_HealthyPerson_Num(DataStorage.dataStorage.getAge_10_14_HealthyPerson_Num() + 1);
		}else if(this.age >= 15 && this.age < 20){
			DataStorage.dataStorage.setAge_15_19_HealthyPerson_Num(DataStorage.dataStorage.getAge_15_19_HealthyPerson_Num() + 1);
		}else if(this.age >= 20 && this.age < 25){
			DataStorage.dataStorage.setAge_20_24_HealthyPerson_Num(DataStorage.dataStorage.getAge_20_24_HealthyPerson_Num() + 1);
		}else if(this.age >= 25 && this.age < 30){
			DataStorage.dataStorage.setAge_25_29_HealthyPerson_Num(DataStorage.dataStorage.getAge_25_29_HealthyPerson_Num() + 1);
		}else if(this.age >= 30 && this.age < 35){
			DataStorage.dataStorage.setAge_30_34_HealthyPerson_Num(DataStorage.dataStorage.getAge_30_34_HealthyPerson_Num() + 1);
		}else if(this.age >= 35 && this.age < 40){
			DataStorage.dataStorage.setAge_35_39_HealthyPerson_Num(DataStorage.dataStorage.getAge_35_39_HealthyPerson_Num() + 1);
		}else if(this.age >= 40 && this.age < 45){
			DataStorage.dataStorage.setAge_40_44_HealthyPerson_Num(DataStorage.dataStorage.getAge_40_44_HealthyPerson_Num() + 1);
		}else if(this.age >= 45 && this.age < 50){
			DataStorage.dataStorage.setAge_45_49_HealthyPerson_Num(DataStorage.dataStorage.getAge_45_49_HealthyPerson_Num() + 1);
		}else if(this.age >= 50 && this.age < 55){
			DataStorage.dataStorage.setAge_50_54_HealthyPerson_Num(DataStorage.dataStorage.getAge_50_54_HealthyPerson_Num() + 1);
		}else if(this.age >= 55 && this.age < 60){
			DataStorage.dataStorage.setAge_55_59_HealthyPerson_Num(DataStorage.dataStorage.getAge_55_59_HealthyPerson_Num() + 1);
		}else if(this.age >= 60 && this.age < 65){
			DataStorage.dataStorage.setAge_60_64_HealthyPerson_Num(DataStorage.dataStorage.getAge_60_64_HealthyPerson_Num() + 1);
		}else if(this.age >= 65 && this.age < 70){
			DataStorage.dataStorage.setAge_65_69_HealthyPerson_Num(DataStorage.dataStorage.getAge_65_69_HealthyPerson_Num() + 1);
		}else if(this.age >= 70 && this.age < 75){
			DataStorage.dataStorage.setAge_70_74_HealthyPerson_Num(DataStorage.dataStorage.getAge_70_74_HealthyPerson_Num() + 1);
		}else if(this.age >= 75 && this.age < 80){
			DataStorage.dataStorage.setAge_75_79_HealthyPerson_Num(DataStorage.dataStorage.getAge_75_79_HealthyPerson_Num() + 1);
		}else if(this.age >= 80 && this.age < 85){
			DataStorage.dataStorage.setAge_80_84_HealthyPerson_Num(DataStorage.dataStorage.getAge_80_84_HealthyPerson_Num() + 1);
		}else if(this.age >= 85 && this.age < 90){
			DataStorage.dataStorage.setAge_85_89_HealthyPerson_Num(DataStorage.dataStorage.getAge_85_89_HealthyPerson_Num() + 1);
		}else if(this.age >= 90 && this.age < 95){
			DataStorage.dataStorage.setAge_90_94_HealthyPerson_Num(DataStorage.dataStorage.getAge_90_94_HealthyPerson_Num() + 1);
		}else {
			DataStorage.dataStorage.setAge_95_100_HealthyPerson_Num(DataStorage.dataStorage.getAge_95_100_HealthyPerson_Num() + 1);
		}
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
	public boolean immuneFlag() {
		return immuneFlag;
	}

	public void setVaccination(boolean isVaccination) {
		this.isVaccination = isVaccination;
	}
	public String getRegional() {
		return regional;
	}
	public void regionalStatistics() 
	{
		switch (this.regional) {
		case "XD":
			DataStorage.dataStorage.setXDHealthy(DataStorage.dataStorage.getXDHealthy() + 1);
			break;
		case "JCP":
			DataStorage.dataStorage.setJCPHealthy(DataStorage.dataStorage.getJCPHealthy() + 1);
			break;
		case "wbl":
			DataStorage.dataStorage.setWblQuarantined(DataStorage.dataStorage.getWblQuarantined() + 1);
			break;
		case "jy":
			DataStorage.dataStorage.setJyQuarantined(DataStorage.dataStorage.getJyQuarantined() + 1);
			break;
		case "XHL":
			DataStorage.dataStorage.setXHLQuarantined(DataStorage.dataStorage.getXHLQuarantined() + 1);
			break;
		case "YZ":
			DataStorage.dataStorage.setYZQuarantined(DataStorage.dataStorage.getYZQuarantined() + 1);
			break;
		default:
			
			break;
		}
	}
}
