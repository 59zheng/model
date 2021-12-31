package agent.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import agent.person.LatentPatient;
import agent.place.City;
import agent.place.Government;
import agent.utils.Cell;
import agent.utils.MoveTarget;
import agent.utils.Point;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.stringtemplate.v4.compiler.CodeGenerator.list_return;

import COVID_19.COVID_19;
import COVID_19.MathUtil;
import COVID_19.SystemFunction;
import COVID_19.SystemParameter;
import data.source.DataStorage;
import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.relogo.ide.dynamics.NetLogoSystemDynamicsParser.ref_return;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;

/**
 * @author Administrator 潜伏者
 */
public class LatentPatient extends Point {
    private ContinuousSpace<Object> space;
    private Grid<Object> grid;
    private City city;
    private int age;// 年龄
    private MoveTarget moveTarget;
    private double temperature;
    private int worldTime;// 当前天数
    private boolean isWearMask;// 是否佩戴口罩
    private boolean isVaccination;// 是否接种疫苗
    List<Cell> cellTeam = COVID_19.cellTeam;// 太原市边界
    List<Cell> TYMcellTeam = COVID_19.TYMcellTeam;// 太原面
    Random random = new Random();// 随机数
    int sig = 1;// 人群流动意愿影响系数：正态分布方差sigma
    private int confirmedMoment;// 确诊时刻(2-3天)
    private int dieMoment = 0;// 死亡时刻，初始为0，-1为不会死亡，其他值为死亡时间
    private int nucleicAcidDetectionInterval;// 核酸检测时间间隔
    private boolean isHospitalized;// 是否住院，false为没住院，true为住院
    private int hospitalizationMoment = 0;// 住院时刻
    private String regional; //地域
    /**
     * 正态分布N(mu,sigma)随机位移目标位置
     */
    double targetXU;// x方向的均值mu
    double targetYU;// y方向的均值mu
    private int latentPatientMoment;// 变为潜伏者时刻
    private double stdRnShadowtime;// 增加一个正态分布用于潜伏期内随机发病时间间隔
    //潜伏者数组
    public static List<Object> CloseContacts = new ArrayList<>();

    public LatentPatient(ContinuousSpace<Object> space, Grid<Object> grid, City city, int x, int y, int age,
                         int latentPatientMoment, double temperature, String regional) {
        super(x, y);
        this.space = space;
        this.grid = grid;
        this.city = city;
        this.age = age;
        this.temperature = temperature;
        this.regional = regional;
        /**
         * 对市民的初始位置进行N(x,100)的正态分布随机
         */
        int XU = random.nextInt(TYMcellTeam.size());
        try {
            targetXU = TYMcellTeam.get(XU).getX();
            targetYU = TYMcellTeam.get(XU).getY();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.latentPatientMoment = latentPatientMoment;
        /**
         * 计算随机发病时间,多数是5天左右
         */
        this.stdRnShadowtime = -(MathUtil.stdGaussian(0.3689, SystemParameter.SHADOW_TIME));


    }

    /**
     * 流动意愿标准化
     * <p>
     * 根据标准正态分布生成随机人口流动意愿
     * <p>
     * 流动意愿标准化后判断是在0的左边还是右边从而决定是否流动。
     * <p>
     * 设X随机变量为服从正态分布，sigma是影响分布形态的系数，从而影响整体人群流动意愿分布
     * u值决定正态分布的中轴是让更多人群偏向希望流动或者希望懒惰。
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
            // double targetX = MathUtil.stdGaussian(targetSig, targetXU);
            // double targetY = MathUtil.stdGaussian(targetSig, targetYU);
            int XU = random.nextInt(TYMcellTeam.size());
            try {
                int targetX = TYMcellTeam.get(XU).getX();
                int targetY = TYMcellTeam.get(XU).getY();
                // 根据地图信息随机产生移动点
                moveTarget = new MoveTarget(targetX, targetY);
            } catch (Exception e) {
                // System.out.print("IndexOutOfBoundsException");
            }

        }

        /**
         * 计算运动位移
         */
        if(moveTarget==null) {
        	return;
        }
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
        if (getX() > xMax || getX() < xMin) {
            moveTarget = null;
           
                udX =this.getX();
            
        }
        /**
         * 纵向运动边界
         */
        if (getY() > yMax || getY() < yMin) {
            moveTarget = null;
       
                udY = this.getY();
            
        }


        moveTo(udX, udY);
        grid.moveTo(this, this.getX(), this.getY());
    }
//    private void action() {
//
//		if (!wantMove()) {
//			return;
//		}
//		/**
//		 * 存在流动意愿的，将进行流动，流动位移仍然遵循标准正态分布
//		 */
//		if (moveTarget == null || moveTarget.isArrived()) {
//			/**
//			 * 在想要移动并且没有目标时，将自身移动目标设置为随机生成的符合正态分布的目标点
//			 */
//			// 产生N(a,b)的数：Math.sqrt(b)*random.nextGaussian()+a
//			// double targetX = MathUtil.stdGaussian(targetSig, targetXU);
//			// double targetY = MathUtil.stdGaussian(targetSig, targetYU);
//			int XU = random.nextInt(TYMcellTeam.size()) + 1;
//			try {
//				int targetX = TYMcellTeam.get(XU).getX();
//				int targetY = TYMcellTeam.get(XU).getY();
//				// 根据地图信息随机产生移动点
//				moveTarget = new MoveTarget(targetX, targetY);
//			} catch (Exception e) {
//				// System.out.print("IndexOutOfBoundsException");
//			}
//
//		}
//
//		/**
//		 * 计算运动位移
//		 */
//		int dX = moveTarget.getX() - getX();
//		int dY = moveTarget.getY() - getY();
//
//		double length = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));// 与目标点的距离
//
//		if (length < 1) {
//			/**
//			 * 判断是否到达目标点
//			 */
//			moveTarget.setArrived(true);
//			return;
//		}
//		/**
//		 * x轴dX为位移量，符号为沿x轴前进方向, 即udX为X方向表示量
//		 */
//		int udX = (int) (dX / length);
//		if (udX == 0 && dX != 0) {
//			if (dX > 0) {
//				udX = 1;
//			} else {
//				udX = -1;
//			}
//		}
//
//		/**
//		 * y轴dY为位移量，符号为沿x轴前进方向，即udY为Y方向表示量
//		 */
//		int udY = (int) (dY / length);
//		// FIXED: 修正一处错误
//		if (udY == 0 && dY != 0) {
//			if (dY > 0) {
//				udY = 1;
//			} else {
//				udY = -1;
//			}
//		}
//
//		/**
//		 * 横向运动边界
//		 */
//
//		int xMax = cellTeam.stream().max(Comparator.comparing(Cell::getX)).get().getX();
//		int xMin = cellTeam.stream().min(Comparator.comparing(Cell::getX)).get().getX();
//		int yMax = cellTeam.stream().max(Comparator.comparing(Cell::getY)).get().getY();
//		int yMin = cellTeam.stream().min(Comparator.comparing(Cell::getY)).get().getY();
//		if (getX() > xMax || getX() < xMin) {
//			moveTarget = null;
//			if (udX > 0) {
//				udX = -udX;
//			}
//		}
//		/**
//		 * 纵向运动边界
//		 */
//		if (getY() > yMax || getY() < yMin) {
//			moveTarget = null;
//			if (udY > 0) {
//				udY = -udY;
//			}
//		}
//
//		moveTo(udX, udY);
//		grid.moveTo(this, this.getX(), this.getY());
//	}

    /**
     * 对各种状态的人进行不同的处理，更新发布市民健康状态
     */
    @ScheduledMethod(start = 1, interval = 1)
    public void update() {
        //System.out.println("潜伏者试图感染4");
        /**
         * 处理未隔离者的移动问题
         */
        action();
        infect();
    }

    @ScheduledMethod(start = 2, interval = 3, priority = 5)
    public void infectInit() {
        //System.out.println("潜伏者试图发病5");


        /**
         * 处理潜伏者发病的问题
         */
        morbidity();


    }

	/*
	 * @ScheduledMethod(start = 1, interval = 1, priority = 6) public void
	 * seeDoctor() {
	 * 
	 * if (SystemParameter.worldTime - latentPatientMoment ==
	 * SystemParameter.clinicalTime) { // 就诊了 if
	 * (MathUtil.randomSelect(SystemParameter.outPatientRate)) { this.isHospitalized
	 * = true; // morbidity(); } else { this.isHospitalized = false; }
	 * 
	 * }
	 * 
	 * }
	 */


    public void init() {
        /**
         * 潜伏者数据统计
         */
        DataStorage.dataStorage.setSHADOW_Num(DataStorage.dataStorage.getSHADOW_Num() + 1);
        //System.out.println("潜伏者数据统计！！！==================" + DataStorage.dataStorage.getSHADOW_Num());
        /**
         * 潜伏者分年龄段统计
         */
        ageStatistics();
        regionalStatistics();

    }

    /**
     * 潜伏者分年龄段统计
     */
    public void ageStatistics() {
        if (this.age >= 0 && this.age < 5) {
            DataStorage.dataStorage
                    .setAge_0_4_LatentPatient_Num(DataStorage.dataStorage.getAge_0_4_LatentPatient_Num() + 1);
        } else if (this.age >= 5 && this.age < 10) {
            DataStorage.dataStorage
                    .setAge_5_9_LatentPatient_Num(DataStorage.dataStorage.getAge_5_9_LatentPatient_Num() + 1);
        } else if (this.age >= 10 && this.age < 15) {
            DataStorage.dataStorage
                    .setAge_10_14_LatentPatient_Num(DataStorage.dataStorage.getAge_10_14_LatentPatient_Num() + 1);
        } else if (this.age >= 15 && this.age < 20) {
            DataStorage.dataStorage
                    .setAge_15_19_LatentPatient_Num(DataStorage.dataStorage.getAge_15_19_LatentPatient_Num() + 1);
        } else if (this.age >= 20 && this.age < 25) {
            DataStorage.dataStorage
                    .setAge_20_24_LatentPatient_Num(DataStorage.dataStorage.getAge_20_24_LatentPatient_Num() + 1);
        } else if (this.age >= 25 && this.age < 30) {
            DataStorage.dataStorage
                    .setAge_25_29_LatentPatient_Num(DataStorage.dataStorage.getAge_25_29_LatentPatient_Num() + 1);
        } else if (this.age >= 30 && this.age < 35) {
            DataStorage.dataStorage
                    .setAge_30_34_LatentPatient_Num(DataStorage.dataStorage.getAge_30_34_LatentPatient_Num() + 1);
        } else if (this.age >= 35 && this.age < 40) {
            DataStorage.dataStorage
                    .setAge_35_39_LatentPatient_Num(DataStorage.dataStorage.getAge_35_39_LatentPatient_Num() + 1);
        } else if (this.age >= 40 && this.age < 45) {
            DataStorage.dataStorage
                    .setAge_40_44_LatentPatient_Num(DataStorage.dataStorage.getAge_40_44_LatentPatient_Num() + 1);
        } else if (this.age >= 45 && this.age < 50) {
            DataStorage.dataStorage
                    .setAge_45_49_LatentPatient_Num(DataStorage.dataStorage.getAge_45_49_LatentPatient_Num() + 1);
        } else if (this.age >= 50 && this.age < 55) {
            DataStorage.dataStorage
                    .setAge_50_54_LatentPatient_Num(DataStorage.dataStorage.getAge_50_54_LatentPatient_Num() + 1);
        } else if (this.age >= 55 && this.age < 60) {
            DataStorage.dataStorage
                    .setAge_55_59_LatentPatient_Num(DataStorage.dataStorage.getAge_55_59_LatentPatient_Num() + 1);
        } else if (this.age >= 60 && this.age < 65) {
            DataStorage.dataStorage
                    .setAge_60_64_LatentPatient_Num(DataStorage.dataStorage.getAge_60_64_LatentPatient_Num() + 1);
        } else if (this.age >= 65 && this.age < 70) {
            DataStorage.dataStorage
                    .setAge_65_69_LatentPatient_Num(DataStorage.dataStorage.getAge_65_69_LatentPatient_Num() + 1);
        } else if (this.age >= 70 && this.age < 75) {
            DataStorage.dataStorage
                    .setAge_70_74_LatentPatient_Num(DataStorage.dataStorage.getAge_70_74_LatentPatient_Num() + 1);
        } else if (this.age >= 75 && this.age < 80) {
            DataStorage.dataStorage
                    .setAge_75_79_LatentPatient_Num(DataStorage.dataStorage.getAge_75_79_LatentPatient_Num() + 1);
        } else if (this.age >= 80 && this.age < 85) {
            DataStorage.dataStorage
                    .setAge_80_84_LatentPatient_Num(DataStorage.dataStorage.getAge_80_84_LatentPatient_Num() + 1);
        } else if (this.age >= 85 && this.age < 90) {
            DataStorage.dataStorage
                    .setAge_85_89_LatentPatient_Num(DataStorage.dataStorage.getAge_85_89_LatentPatient_Num() + 1);
        } else if (this.age >= 90 && this.age < 95) {
            DataStorage.dataStorage
                    .setAge_90_94_LatentPatient_Num(DataStorage.dataStorage.getAge_90_94_LatentPatient_Num() + 1);
        } else {
            DataStorage.dataStorage
                    .setAge_95_100_LatentPatient_Num(DataStorage.dataStorage.getAge_95_100_LatentPatient_Num() + 1);
        }
    }

    /**
     * 处理潜伏者发病的问题
     */
    public void morbidity() {
        double morbidityRate = 0;// 发病概率morbidit
        double mildDisease = 0;// 轻症概率mildDisease
        // 随机数
        double randomNum = 0;
        // 到达发病时间
        if ((SystemParameter.worldTime - this.latentPatientMoment > stdRnShadowtime)) {
            // 发病概率morbidity 以及轻症概率mildDisease
            if (this.isVaccination && this.isWearMask) {
                morbidityRate = 0.33 * 0.5;
                mildDisease = 0.77 * 0.5;
                randomNum = random.nextDouble();
            } else if (this.isVaccination) {
                morbidityRate = 0.33;
                mildDisease = 0.77;
                randomNum = random.nextDouble();
            } else if (this.isWearMask) {
                morbidityRate = 0.5;
                mildDisease = 0.5;
                randomNum = random.nextDouble();
            }
//			无防护
            else {
                randomNum = 0;
                morbidityRate = 1;
            }
         // 发病
            if (randomNum < morbidityRate) {
                Context<Object> context = ContextUtils.getContext(this);
                context.remove(this);
                DiagnosisPatient patient = new DiagnosisPatient(space, grid, City.getInstance(), this.getX(),
                        this.getY(), this.getAge(), SystemParameter.worldTime, this.getTemperature(), this.regional);
                //System.out.println("潜伏者发病");
//                口罩，疫苗
                patient.setVaccination(this.isVaccination);
                patient.setWearMask(this.isWearMask);
                // 确定轻症还是重症
                if (randomNum < mildDisease) {
                    patient.setMildDisease(true);
                }
                //判断是否住院
                if (!isHospitalized) {
                	if (SystemParameter.worldTime - latentPatientMoment >=SystemParameter.clinicalTime) {
                		if (DataStorage.dataStorage.getUnuseBedNum() > 0) {
                			// 就诊了 if
                      	  if(MathUtil.randomSelect(SystemParameter.outPatientRate)) { 
                      		  this.isHospitalized= true; // morbidity(); }
                	                        hospitalizationMoment = SystemParameter.worldTime;
                      
                        patient.setHospitalized(isHospitalized);
                        patient.setHospitalizationMoment(hospitalizationMoment);
                        /**
                         * 使用床位数+1
                         */
                        DataStorage.dataStorage.setUseBedNum(DataStorage.dataStorage.getUseBedNum() + 1);
                        /**
                         * 未使用床位数-1
                         */
                        DataStorage.dataStorage.setUnuseBedNum(DataStorage.dataStorage.getUnuseBedNum() - 1);
                    }
                		}
                	}
                }else {
                	 patient.setHospitalized(false);

				}
                patient.init();
                context.add(patient);
                grid.moveTo(patient, patient.getX(), patient.getY());
                /**
                 * 潜伏者-1
                 */
                DataStorage.dataStorage.setSHADOW_Num(DataStorage.dataStorage.getSHADOW_Num() - 1);

            }
//            没有发病   
            


        }
    }


    /**
     * 寻找周围人群,周围8个栅格内对象设置为密切接触者
     */

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void infect() {

        //获取到周围潜伏者位置
        GridPoint pt = grid.getLocation(this);
        CopyOnWriteArrayList<Object> latentPatientList = new CopyOnWriteArrayList<Object>();
        Iterable latentPatientItrable = (Iterable) grid.getObjectsAt(pt.getX(), pt.getY());
        latentPatientItrable.forEach(ite -> {
            latentPatientList.add(ite);
        });

        //获取到的健康者的栅格
        Grid<Object> healthyPersonGrid = HealthyPerson.grid;
        ContinuousSpace<Object> healthyPersonSpace = HealthyPerson.space;


        for (Object obj : latentPatientList) {

            //获取到潜伏者的点
            NdPoint spacePt1 = space.getLocation(obj);
            if (spacePt1 == null) {
                continue;
            }
            //距离近的变为密切接触者

            if (obj instanceof LatentPatient) {

                for (int i = -2; i < 2; i++) {
                    for (int i1 = -2; i1 < 3; i1++) {
                        CopyOnWriteArrayList<Object> healthyPersonList1 = new CopyOnWriteArrayList<Object>();
                        Iterable healthyPerson1 = (Iterable) healthyPersonGrid.getObjectsAt(pt.getX() + i, pt.getY() + i1);
                        healthyPerson1.forEach(ite -> {
                            healthyPersonList1.add(ite);
                        });


                        // 周围人群是健康者的变为密切接触者
                        for (Object obj1 : healthyPersonList1) {
                            //获取到当前健康者的点
                            NdPoint spacePt = healthyPersonSpace.getLocation(obj1);
                            if (spacePt == null) {
                                continue;
                            }
                            if (obj1 instanceof HealthyPerson) {
                            	 HealthyPerson healthyPerson = (HealthyPerson) obj1;
//                            	 免疫，跳过
                            	if (healthyPerson.immuneFlag()) {
                            		continue;
								}
                                Context<Object> context = ContextUtils.getContext(obj1);
                                context.remove(obj1);
                               
                                // 健康者是否佩戴口罩
                                boolean vaccination = healthyPerson.isVaccination();
                                boolean wearMask = healthyPerson.isWearMask();
                                CloseContact closeContact = new CloseContact(space, grid, city, this.getX() + i, this.getY() + i1, healthyPerson.getAge(), SystemParameter.worldTime, healthyPerson.getTemperature(), healthyPerson.getRegional());

                                closeContact.setVaccination(vaccination);
                                closeContact.setWearMask(wearMask);
                                closeContact.init();
                                context.add(closeContact);
                                CloseContacts.add(closeContact);
                                space.moveTo(closeContact, spacePt.getX(), spacePt.getY());
                                grid.moveTo(closeContact, this.getX(), this.getY());
                                // 健康者-1                         
                                DataStorage.dataStorage.setNORMAL_Num(DataStorage.dataStorage.getNORMAL_Num() - 1);
                            }

                        }
                    }

                }
            }
        }
    }


    /**
     * 判断是否会死亡并计算死亡时间和治愈时间
     */
    private void destiny() {
        /**
         * 幸运数字，[1,10000]随机数
         */
        int destiny = new Random().nextInt(10000) + 1;

        /**
         * 如果幸运数字落在死亡区间
         */
       
        int dieTime = -(int) MathUtil.stdGaussian(SystemParameter.DIE_VARIANCE, SystemParameter.DIE_TIME);
        /**
         * 发病后确定死亡时刻
         */
        dieMoment = SystemParameter.worldTime + dieTime;
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

    public void regionalStatistics() {
        switch (this.regional) {
            case "XD":
                DataStorage.dataStorage.setXDLatent(DataStorage.dataStorage.getXDLatent() + 1);
                break;
            case "JCP":
                DataStorage.dataStorage.setJCPLatent(DataStorage.dataStorage.getJCPLatent() + 1);
                break;
            case "wbl":
                DataStorage.dataStorage.setWblLatent(DataStorage.dataStorage.getWblLatent() + 1);
                break;
            case "jy":
                DataStorage.dataStorage.setJyLatent(DataStorage.dataStorage.getJyLatent() + 1);
                break;
            case "XHL":
                DataStorage.dataStorage.setXHLLatent(DataStorage.dataStorage.getXHLLatent() + 1);
                break;
            case "YZ":
                DataStorage.dataStorage.setYZLatent(DataStorage.dataStorage.getYZLatent() + 1);
                break;
            default:

                break;
        }
    }
}
