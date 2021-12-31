package COVID_19;

import java.awt.Color;
import java.util.Random;



import data.source.DataStorage;

public class SystemFunction {
	
	/**
	 * 根据年龄（0-14，15-64，65岁以上）的结构比例生成一个年龄
	 */
	public static int createRandomAge() {
		int age = 0;
		double[] rate = SystemParameter.AgeStructureRate;
		double randomNumber = Math.random();// 产生随机数（0-1）
		// Random random = new Random();// 生成随机数
		if (randomNumber >= 0 && randomNumber < rate[0]) {
			age = (int) (Math.random() * 14);
		} else if (randomNumber >= rate[0] && randomNumber < rate[0] + rate[1]) {
			age = (int) (15 + Math.random() * 49);
		} else if (randomNumber >= rate[0] + rate[1] && randomNumber < rate[0] + rate[1] + rate[2]) {
			age = (int) (65 + Math.random() * 35);
		}
		return age;
	}

	/**
	 * 通过年龄得出感染概率
	 */
	public static double infectionRateByAge(int age) {
		double infectionRate = 0;
		double[] rate = SystemParameter.infectionRateByAge;
		if (age >= 0 && age < 9) {
			infectionRate = rate[0];
		} else if (age >= 10 && age < 19) {
			infectionRate = rate[1];
		} else if (age >= 20 && age < 29) {
			infectionRate = rate[2];
		} else if (age >= 30 && age < 39) {
			infectionRate = rate[3];
		} else if (age >= 40 && age < 49) {
			infectionRate = rate[4];
		} else if (age >= 50 && age < 59) {
			infectionRate = rate[5];
		} else if (age >= 60 && age < 69) {
			infectionRate = rate[6];
		} else if (age >= 70 && age < 79) {
			infectionRate = rate[7];
		} else {
			infectionRate = rate[8];
		}
		return infectionRate;
	}
	
	/**
	 * 通过不同年龄段佩戴口罩得出感染概率
	 */
	public static double infectionRateByWearCost(int age) {
		double infectionRate = 0;
		double[] rate = SystemParameter.infectionRateByWearCost;
		if (age >= 0 && age < 9) {
			infectionRate = rate[0];
		} else if (age >= 10 && age < 19) {
			infectionRate = rate[1];
		} else if (age >= 20 && age < 29) {
			infectionRate = rate[2];
		} else if (age >= 30 && age < 39) {
			infectionRate = rate[3];
		} else if (age >= 40 && age < 49) {
			infectionRate = rate[4];
		} else if (age >= 50 && age < 59) {
			infectionRate = rate[5];
		} else if (age >= 60 && age < 69) {
			infectionRate = rate[6];
		} else if (age >= 70 && age < 79) {
			infectionRate = rate[7];
		} else {
			infectionRate = rate[8];
		}
		return infectionRate;
	}
	/**
	 * 通过不同年龄段接种疫苗得出感染概率
	 */
	public static double infectionRateByVaccination(int age) {
		double infectionRate = 0;
		double[] rate = SystemParameter.infectionRateByVaccination;
		if (age >= 0 && age < 9) {
			infectionRate = rate[0];
		} else if (age >= 10 && age < 19) {
			infectionRate = rate[1];
		} else if (age >= 20 && age < 29) {
			infectionRate = rate[2];
		} else if (age >= 30 && age < 39) {
			infectionRate = rate[3];
		} else if (age >= 40 && age < 49) {
			infectionRate = rate[4];
		} else if (age >= 50 && age < 59) {
			infectionRate = rate[5];
		} else if (age >= 60 && age < 69) {
			infectionRate = rate[6];
		} else if (age >= 70 && age < 79) {
			infectionRate = rate[7];
		} else {
			infectionRate = rate[8];
		}
		return infectionRate;
	}

	/**
	 * 通过年龄得出致死概率
	 */
	public static double deathRateByAge(int age) {
		double deathRate = 0;
		double[] rate = SystemParameter.DeathRateByAge;
		if (age >= 0 && age < 9) {
			deathRate = rate[0];
		} else if (age >= 10 && age < 19) {
			deathRate = rate[1];
		} else if (age >= 20 && age < 29) {
			deathRate = rate[2];
		} else if (age >= 30 && age < 39) {
			deathRate = rate[3];
		} else if (age >= 40 && age < 49) {
			deathRate = rate[4];
		} else if (age >= 50 && age < 59) {
			deathRate = rate[5];
		} else if (age >= 60 && age < 69) {
			deathRate = rate[6];
		} else if (age >= 70 && age < 79) {
			deathRate = rate[7];
		} else {
			deathRate = rate[8];
		}
		return deathRate;
	}

	
	
	
	
	/**
	 * 随机生成一个月份，通过月份得到当前月份的温度
	 */
	public static double createRandomTemper() {
		double[] rate = SystemParameter.AverageTemperature;
		int i=(int)(Math.random()*12);
		
		return rate[i];
	}
	/**
	 * 通过温度得到当前月份
	 */
	public static void monthByTemperature(double temperature) {
		double[] month = SystemParameter.AverageTemperature;
		for (int j = 0; j < month.length; j++) {
			if (temperature==month[j]) {
				switch (j) {
				case 0:
					DataStorage.dataStorage.setJanuaryNum(DataStorage.dataStorage.getJanuaryNum() + 1);
					break;
				case 1:
					DataStorage.dataStorage.setFebruaryNum(DataStorage.dataStorage.getJanuaryNum() + 1);
					break;
				case 2:
					DataStorage.dataStorage.setMarchNum(DataStorage.dataStorage.getJanuaryNum() + 1);
					break;
				case 3:
					DataStorage.dataStorage.setAprilNum(DataStorage.dataStorage.getJanuaryNum() + 1);
					break;
				case 4:
					DataStorage.dataStorage.setMayNum(DataStorage.dataStorage.getJanuaryNum() + 1);
					break;
				case 5:
					DataStorage.dataStorage.setJuneNum(DataStorage.dataStorage.getJanuaryNum() + 1);
					break;
				case 6:
					DataStorage.dataStorage.setJulyNum(DataStorage.dataStorage.getJanuaryNum() + 1);
					break;
				case 7:
					DataStorage.dataStorage.setAugustNum(DataStorage.dataStorage.getJanuaryNum() + 1);
					break;
				case 8:
					DataStorage.dataStorage.setSeptemberNum(DataStorage.dataStorage.getJanuaryNum() + 1);
					break;
				case 9:
					DataStorage.dataStorage.setOctoberNum(DataStorage.dataStorage.getJanuaryNum() + 1);
					break;
				case 10:
					DataStorage.dataStorage.setNovemberNum(DataStorage.dataStorage.getJanuaryNum() + 1);
					break;
				case 11:
					DataStorage.dataStorage.setDecemberNum(DataStorage.dataStorage.getJanuaryNum() + 1);
					break;
				}
		}
			}
		
		
	}

	/**
	 * 通过温度得出感染概率
	 * @param temperature
	 * @return
	 */
	public static double infectionRateByTemper(double temperature) {
		double infectionRate=0;
		double[] rate = SystemParameter.infectionRateByTemper;
		if ( temperature < 5) {
			infectionRate = rate[0];
		} else if (temperature >= 5 && temperature < 15) {
			infectionRate = rate[1];
		}else if (temperature >= 15 && temperature < 25) {
			infectionRate = rate[2];
		}else if (temperature >= 25 && temperature < 35) {
			infectionRate = rate[3];
		}
			
		return infectionRate;
	}
	
	/*
	 * 
	 * 是否戴口罩*/
		public static boolean  isWearMask(){
			/**
			 * 幸运数字，[1,10000]随机数
			 */
			int destiny = new Random().nextInt(10000) + 1;
			if (destiny >= 1 && destiny <= (int) (SystemParameter.WEAR_WASK_RATE * 10000)) {
				return true;
			}else {
				return false;
			}
		}
		/*
		 * 
		 * 
		 * 是否接种疫苗*/
		public static boolean isVaccination(){
			/**
			 * 幸运数字，[1,10000]随机数
			 */
			int destiny = new Random().nextInt(10000) + 1;
			if (destiny >= 1 && destiny <= (int) (SystemParameter.VACCINATION_RATE * 10000)) {
				return true;
			}else {
				return false;
			}
			
		}
}
