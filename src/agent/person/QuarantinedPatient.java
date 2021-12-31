package agent.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import COVID_19.COVID_19;
import COVID_19.MathUtil;
import COVID_19.SystemFunction;
import COVID_19.SystemParameter;
import agent.place.City;
import agent.place.Hospital;
import agent.utils.Cell;
import agent.utils.Point;
import data.source.DataStorage;
import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;

/**
 * @author suya 隔离者
 */
public class QuarantinedPatient extends Point {
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private City city;
	private int age;// 年龄
	private double temperature;
	private boolean isWearMask;// 是否佩戴口罩
	private boolean isVaccination;// 是否接种疫苗
	List<Cell> cellTeam = COVID_19.cellTeam;// 太原市边界
	List<Cell> TYMcellTeam = COVID_19.TYMcellTeam;// 太原面
	Random random = new Random();// 随机数
	private boolean isMildDisease;// 是否是轻症
	private boolean isHospitalized;// 是否住院，false为没住院，true为住院
	private int quarantinedMoment = 0;// 隔离时刻
	private int dieMoment = 0;// 死亡时刻，初始为0，-1为不会死亡，其他值为死亡时间
	private int hospitalizationMoment = 0;// 住院时刻
	private int cureTime = 3;// 治愈所需时间
	private int worldTime;// 当前天数
	private boolean NAT = false;// 核酸检测是否是阳性
	private String regional; //地域

	/**
	 * 正态分布N(mu,sigma)随机位移目标位置
	 */
	double targetXU;// x方向的均值mu
	double targetYU;// y方向的均值mu
	/**
	 * 方差sigma
	 */
	double targetSig = 50;

	public QuarantinedPatient(ContinuousSpace<Object> space, Grid<Object> grid, City city, int x, int y, int age,
			int quarantinedMoment, double temperature,String regional) {
		super(x, y);
		this.space = space;
		this.grid = grid;
		this.city = city;
		this.age = age;
		this.temperature = temperature;
		this.quarantinedMoment = quarantinedMoment;
		this.isHospitalized = false;
		this.regional=regional;
		/**
		 * 对市民的初始位置进行N(x,100)的正态分布随机
		 */
		int XU = random.nextInt(TYMcellTeam.size());
		try {
			targetXU = TYMcellTeam.get(XU).getX();
			targetYU = TYMcellTeam.get(XU).getY();
		} catch (Exception e) {
		}
		/**
		 * 初始化时候就计算是否死亡，和死亡时间 只要打疫苗就不会死亡
		 */
		if (this.isVaccination == false) {
			destiny();
		}
		// 初始化的时候设置时间
		worldTime = SystemParameter.worldTime;

	}

	/**
	 * 对各种状态的人进行不同的处理，更新发布市民健康状态
	 */
	@ScheduledMethod(start = 1, interval = 3, priority = 3)
	public void update() {
		//System.out.println("隔离者核酸检测");
		// 核酸检测每3天做一次
		NAT();
		
	}
	
	
	

	/*
	 * 
	 * 核酸检测每3天做一次 //确定随机发病的概率morbidity();
	 */
	private void NAT() {
		int count = 0;
		if (SystemParameter.worldTime - worldTime == 1) {
			worldTime = SystemParameter.worldTime;
			//System.out.println("worldTime" + worldTime);
			if ((worldTime - this.quarantinedMoment) < 14) {
				// 时间间隔
//				int time = worldTime - this.quarantinedMoment - count * 3;
//				if (time == 0 && NAT == false) {
					morbidity();
//				} else if (time >= 3 && NAT == false) {
//					morbidity();
//					count++;
//				}
			} else {
				System.out.println("隔离变正常");
				// 超过14天变为健康者
				Context<Object> context = ContextUtils.getContext(this);
				context.remove(this);
				HealthyPerson healthyPerson = new HealthyPerson(space, grid, City.getInstance(), this.getX(),
						this.getY(), this.getAge(),this.getTemperature(),this.regional,false);
				context.add(healthyPerson);
				grid.moveTo(healthyPerson, healthyPerson.getX(), healthyPerson.getY());
				// * 隔离者-1
				DataStorage.dataStorage.setFREEZE_Num(DataStorage.dataStorage.getFREEZE_Num() - 1);
//				健康着+1
				DataStorage.dataStorage.setNORMAL_Num(DataStorage.dataStorage.getNORMAL_Num() + 1);
			}

		}
	}

	public void init() {
		/**
		 * 隔离者数据统计
		 */
		DataStorage.dataStorage.setFREEZE_Num(DataStorage.dataStorage.getFREEZE_Num() + 1);
		//System.out.println("隔离者者数据统计==================" + DataStorage.dataStorage.getFREEZE_Num());
		/**
		 * 隔离者分年龄段统计
		 */
		ageStatistics();
		regionalStatistics();
		
	}

	/**
	 * 处理隔离者发病的问题
	 */
	public void morbidity() {
		double morbidityRate = 0;// 发病概率morbidit
		double mildDisease = 0;// 轻症概率mildDisease
		// 随机数
		double randomNum = 0;

			// 发病概率morbidity 以及轻症概率mildDisease
			if (this.isVaccination == true && this.isWearMask == true) {
				morbidityRate = 0.33 * 0.5;
				mildDisease = 0.77 * 0.5;
				randomNum =random.nextDouble();
			} else if (this.isVaccination == true) {
				morbidityRate = 0.33;
				mildDisease = 0.77;
				randomNum =random.nextDouble();
			} else if (this.isWearMask == true) {
				morbidityRate = 0.5;
				mildDisease = 0.5;
				randomNum = random.nextDouble();
			}
//			无防护
			else{
				randomNum = random.nextDouble();
				mildDisease = 0.2;
				morbidityRate=1;	
			}

		// 发病
			if (SystemParameter.worldTime - worldTime >= 3) {
		if (randomNum < morbidityRate) {
			NAT = true;
			// 变为确诊者
			Context<Object> context = ContextUtils.getContext(this);
			context.remove(this);
			DiagnosisPatient diagnosisPatient = new DiagnosisPatient(space, grid, City.getInstance(), this.getX(),
					this.getY(), this.getAge(), SystemParameter.worldTime,this.getTemperature(),this.regional);
			
			/**
			 * 隔离者-1
			 */
			DataStorage.dataStorage.setFREEZE_Num(DataStorage.dataStorage.getFREEZE_Num() - 1);
			// 确定轻症还是重症
			if (randomNum < mildDisease) {
				diagnosisPatient.setMildDisease(true);
			}
			//是否住院


			if (!isHospitalized) {
				if (SystemParameter.worldTime - quarantinedMoment >=SystemParameter.clinicalTime) {
            		if (DataStorage.dataStorage.getUnuseBedNum() > 0) {
            			// 就诊了 if
                  	  if(MathUtil.randomSelect(SystemParameter.outPatientRate)) { 
					this.isHospitalized = true;
					hospitalizationMoment = SystemParameter.worldTime;
					//System.out.println("隔离者isHospitalized"+isHospitalized);
					diagnosisPatient.setHospitalized(isHospitalized);
					diagnosisPatient.setHospitalizationMoment(hospitalizationMoment);
					/**
					 * 使用床位数+1
					 */
					DataStorage.dataStorage.setUseBedNum(DataStorage.dataStorage.getUseBedNum() + 1);
					/**
					 * 未使用床位数-1
					 */
					DataStorage.dataStorage.setUnuseBedNum(DataStorage.dataStorage.getUnuseBedNum() - 1);
					context.add(diagnosisPatient);
					grid.moveTo(diagnosisPatient, diagnosisPatient.getX(), diagnosisPatient.getY());
					diagnosisPatient.init();
				}
            		}
				}
			}
			context.add(diagnosisPatient);
			grid.moveTo(diagnosisPatient, diagnosisPatient.getX(), diagnosisPatient.getY());
			diagnosisPatient.init();
			
		}

	
			}
	}


	/**
	 * 隔离者分年龄段统计
	 */
	public void ageStatistics() {
		if (this.age >= 0 && this.age < 5) {
			DataStorage.dataStorage
					.setAge_0_4_QuarantinedPatient_Num(DataStorage.dataStorage.getAge_0_4_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 5 && this.age < 10) {
			DataStorage.dataStorage
					.setAge_5_9_QuarantinedPatient_Num(DataStorage.dataStorage.getAge_5_9_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 10 && this.age < 15) {
			DataStorage.dataStorage.setAge_10_14_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_10_14_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 15 && this.age < 20) {
			DataStorage.dataStorage.setAge_15_19_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_15_19_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 20 && this.age < 25) {
			DataStorage.dataStorage.setAge_20_24_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_20_24_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 25 && this.age < 30) {
			DataStorage.dataStorage.setAge_25_29_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_25_29_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 30 && this.age < 35) {
			DataStorage.dataStorage.setAge_30_34_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_30_34_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 35 && this.age < 40) {
			DataStorage.dataStorage.setAge_35_39_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_35_39_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 40 && this.age < 45) {
			DataStorage.dataStorage.setAge_40_44_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_40_44_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 45 && this.age < 50) {
			DataStorage.dataStorage.setAge_45_49_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_45_49_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 50 && this.age < 55) {
			DataStorage.dataStorage.setAge_50_54_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_50_54_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 55 && this.age < 60) {
			DataStorage.dataStorage.setAge_55_59_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_55_59_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 60 && this.age < 65) {
			DataStorage.dataStorage.setAge_60_64_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_60_64_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 65 && this.age < 70) {
			DataStorage.dataStorage.setAge_65_69_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_65_69_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 70 && this.age < 75) {
			DataStorage.dataStorage.setAge_70_74_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_70_74_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 75 && this.age < 80) {
			DataStorage.dataStorage.setAge_75_79_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_75_79_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 80 && this.age < 85) {
			DataStorage.dataStorage.setAge_80_84_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_80_84_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 85 && this.age < 90) {
			DataStorage.dataStorage.setAge_85_89_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_85_89_QuarantinedPatient_Num() + 1);
		} else if (this.age >= 90 && this.age < 95) {
			DataStorage.dataStorage.setAge_90_94_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_90_94_QuarantinedPatient_Num() + 1);
		} else {
			DataStorage.dataStorage.setAge_95_100_QuarantinedPatient_Num(
					DataStorage.dataStorage.getAge_95_100_QuarantinedPatient_Num() + 1);
		}
	}

	/**
	 * 判断是否会死亡并计算死亡时间和治愈时间
	 */
	public void destiny() {
		/**
		 * 幸运数字，[1,10000]随机数
		 */
		int destiny = new Random().nextInt(10000) + 1;
		if (destiny >= 1 && destiny <= (int) (SystemParameter.FATALITY_RATE * 10000)) {

			/**
			 * 如果幸运数字落在死亡区间
			 */
			int dieTime = (int) MathUtil.stdGaussian(SystemParameter.DIE_VARIANCE, SystemParameter.DIE_TIME);
			/**
			 * 发病后确定死亡时刻
			 */
			dieMoment = SystemParameter.worldTime + dieTime;
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

	public void setVaccination(boolean isVaccination) {
		this.isVaccination = isVaccination;
	}
	public void regionalStatistics() 
	{
		switch (this.regional) {
		case "XD":
			DataStorage.dataStorage.setXDQuarantined(DataStorage.dataStorage.getXDQuarantined() + 1);
			break;
		case "JCP":
			DataStorage.dataStorage.setJCPQuarantined(DataStorage.dataStorage.getJCPQuarantined() + 1);
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
