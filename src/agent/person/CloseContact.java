package agent.person;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

import COVID_19.COVID_19;
import COVID_19.MathUtil;
import COVID_19.SystemFunction;
import COVID_19.SystemParameter;
import agent.place.City;
import agent.place.Government;
import agent.utils.Cell;
import agent.utils.MoveTarget;
import agent.utils.Point;
import data.source.DataStorage;
import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.relogo.ide.dynamics.NetLogoSystemDynamicsParser.ref_return;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;

/**
 * @author suya 密切接触者 是否戴口罩，是否接种疫苗影响感染机率
 */
public class CloseContact extends Point {
    private ContinuousSpace<Object> space;
    private Grid<Object> grid;
    private City city;
    private int age;// 年龄
    private MoveTarget moveTarget;
    private double temperature;
    private int contactMoment;
    private int worldTime;// 当前天数
    private String regional;
    /*
     *
     * 是否戴口罩
     */
    private boolean isWearMask;
    /*
     *
     *
     * 是否接种疫苗
     */
    private boolean isVaccination;
    // 密切接触数组
    List<Object> CloseContacts = LatentPatient.CloseContacts;
    // 太原市边界
    List<Cell> cellTeam = COVID_19.cellTeam;

    // 太原面
    List<Cell> TYMcellTeam = COVID_19.TYMcellTeam;
    //当前政府
    Government government = COVID_19.government;

    // 随机数
    Random random = new Random();


    /**
     * 正态分布N(mu,sigma)随机位移目标位置
     */
    int targetXU;// x方向的均值mu
    int targetYU;// y方向的均值mu
    double targetSig = 50;// 方差sigma

    public CloseContact(ContinuousSpace<Object> space, Grid<Object> grid, City city, int x, int y, int age,
                        int contactMoment, double temperature, String regional) {
        super(x, y);
        this.space = space;
        this.grid = grid;
        this.city = city;
        this.age = age;
        this.temperature = temperature;
        this.contactMoment = contactMoment;
        this.regional = regional;
        /**
         * 对市民的初始位置进行N(x,100)的正态分布随机
         */
        // int XU = (int)MathUtil.stdGaussian(1000, x);
        // int YU = (int)MathUtil.stdGaussian(1000, y);
        int XU = random.nextInt(TYMcellTeam.size());
        try {
            targetXU = TYMcellTeam.get(XU).getX();
            targetYU = TYMcellTeam.get(XU).getY();
        } catch (Exception e) {
            // System.out.print("IndexOutOfBoundsException");
        }
        // 初始化的时候设置时间
        worldTime = SystemParameter.worldTime;
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
       if (moveTarget==null) {
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
        if (getX() > xMax || getX() < xMin) {
            moveTarget = null;
            if (udX > 0) {
                udX = -udX;
            }
        }
        /**
         * 纵向运动边界
         */
        if (getY() > yMax || getY() < yMin) {
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
     */
    @ScheduledMethod(start = 1, interval = 1, priority = 2)
    public void update() {
        //System.out.println("密切接触移动2");
        /**
         * 处理未隔离者的移动问题
         */
        action();
    }

    @ScheduledMethod(start = 1, interval = 1, priority = 2)
    public void infectInit() {
        //System.out.println("密切接触试图隔离");
        quarantine();
    }


    public void init() {
        /**
         * 密切接触者数据统计
         */
        DataStorage.getInstance().setCONTACT_Num(DataStorage.getInstance().getCONTACT_Num() + 1);


        /**
         * 密接者分年龄段统计
         */
        ageStatistics();
        regionalStatistics();

    }


    /**
     * 密接者分年龄段统计
     */
    public void ageStatistics() {
        if (this.age >= 0 && this.age < 5) {
            DataStorage.getInstance()
                    .setAge_0_4_CloseContact_Num(DataStorage.getInstance().getAge_0_4_CloseContact_Num() + 1);
        } else if (this.age >= 5 && this.age < 10) {
            DataStorage.getInstance()
                    .setAge_5_9_CloseContact_Num(DataStorage.getInstance().getAge_5_9_CloseContact_Num() + 1);
        } else if (this.age >= 10 && this.age < 15) {
            DataStorage.getInstance()
                    .setAge_10_14_CloseContact_Num(DataStorage.getInstance().getAge_10_14_CloseContact_Num() + 1);
        } else if (this.age >= 15 && this.age < 20) {
            DataStorage.getInstance()
                    .setAge_15_19_CloseContact_Num(DataStorage.getInstance().getAge_15_19_CloseContact_Num() + 1);
        } else if (this.age >= 20 && this.age < 25) {
            DataStorage.getInstance()
                    .setAge_20_24_CloseContact_Num(DataStorage.getInstance().getAge_20_24_CloseContact_Num() + 1);
        } else if (this.age >= 25 && this.age < 30) {
            DataStorage.getInstance()
                    .setAge_25_29_CloseContact_Num(DataStorage.getInstance().getAge_25_29_CloseContact_Num() + 1);
        } else if (this.age >= 30 && this.age < 35) {
            DataStorage.getInstance()
                    .setAge_30_34_CloseContact_Num(DataStorage.getInstance().getAge_30_34_CloseContact_Num() + 1);
        } else if (this.age >= 35 && this.age < 40) {
            DataStorage.getInstance()
                    .setAge_35_39_CloseContact_Num(DataStorage.getInstance().getAge_35_39_CloseContact_Num() + 1);
        } else if (this.age >= 40 && this.age < 45) {
            DataStorage.getInstance()
                    .setAge_40_44_CloseContact_Num(DataStorage.getInstance().getAge_40_44_CloseContact_Num() + 1);
        } else if (this.age >= 45 && this.age < 50) {
            DataStorage.getInstance()
                    .setAge_45_49_CloseContact_Num(DataStorage.getInstance().getAge_45_49_CloseContact_Num() + 1);
        } else if (this.age >= 50 && this.age < 55) {
            DataStorage.getInstance()
                    .setAge_50_54_CloseContact_Num(DataStorage.getInstance().getAge_50_54_CloseContact_Num() + 1);
        } else if (this.age >= 55 && this.age < 60) {
            DataStorage.getInstance()
                    .setAge_55_59_CloseContact_Num(DataStorage.getInstance().getAge_55_59_CloseContact_Num() + 1);
        } else if (this.age >= 60 && this.age < 65) {
            DataStorage.getInstance()
                    .setAge_60_64_CloseContact_Num(DataStorage.getInstance().getAge_60_64_CloseContact_Num() + 1);
        } else if (this.age >= 65 && this.age < 70) {
            DataStorage.getInstance()
                    .setAge_65_69_CloseContact_Num(DataStorage.getInstance().getAge_65_69_CloseContact_Num() + 1);
        } else if (this.age >= 70 && this.age < 75) {
            DataStorage.getInstance()
                    .setAge_70_74_CloseContact_Num(DataStorage.getInstance().getAge_70_74_CloseContact_Num() + 1);
        } else if (this.age >= 75 && this.age < 80) {
            DataStorage.getInstance()
                    .setAge_75_79_CloseContact_Num(DataStorage.getInstance().getAge_75_79_CloseContact_Num() + 1);
        } else if (this.age >= 80 && this.age < 85) {
            DataStorage.getInstance()
                    .setAge_80_84_CloseContact_Num(DataStorage.getInstance().getAge_80_84_CloseContact_Num() + 1);
        } else if (this.age >= 85 && this.age < 90) {
            DataStorage.getInstance()
                    .setAge_85_89_CloseContact_Num(DataStorage.getInstance().getAge_85_89_CloseContact_Num() + 1);
        } else if (this.age >= 90 && this.age < 95) {
            DataStorage.getInstance()
                    .setAge_90_94_CloseContact_Num(DataStorage.getInstance().getAge_90_94_CloseContact_Num() + 1);
        } else {
            DataStorage.getInstance()
                    .setAge_95_100_CloseContact_Num(DataStorage.getInstance().getAge_95_100_CloseContact_Num() + 1);
        }
    }

    /**
     * 密切接触者部分根据响应等级被及时隔离 隔离的成为隔离者，未隔离的还是密切接触者或变为潜伏者，运动过程中有可能感染其他人
     *
     * @throws InterruptedException
     */

    private void quarantine() {
        //		一个人只判定一次
        if (SystemParameter.worldTime - worldTime == 2) {
                // 根据根据新冠疫情风险等级划分新冠肺炎疫情防控应急响应等级密接者被隔离概率
                // 成为隔离者的概率
                double rate = SystemParameter.isolationRatio;
                System.out.println("当前隔离比例"+rate);
               if (MathUtil.randomSelect(rate)){
                    CloseContact closeContact = this;
                    // 密切接触者是否打疫苗
                    boolean vaccination = closeContact.isVaccination();
                    boolean wearMask = closeContact.isWearMask();
                    NdPoint spacePt = space.getLocation(closeContact);
                    Context<Object> context = ContextUtils.getContext(closeContact);
                    if (context != null) {
                        context.remove(closeContact);
                        // System.out.println("SystemParameter.worldTime-closeContact.getContactMoment()"+SystemParameter.worldTime+closeContact.getContactMoment());
                        // 变为隔离者
                        QuarantinedPatient quarantinedPatient = new QuarantinedPatient(space, grid, City.getInstance(),
                                this.getX(), this.getY(), this.getAge(), SystemParameter.worldTime, this.getTemperature(), this.regional);
                        quarantinedPatient.setVaccination(vaccination);
                        quarantinedPatient.setWearMask(wearMask);
                        quarantinedPatient.init();
                        // * 密切接触者-1
                        DataStorage.dataStorage.setCONTACT_Num(DataStorage.dataStorage.getCONTACT_Num() - 1);

                        context.add(quarantinedPatient);
                        space.moveTo(quarantinedPatient, spacePt.getX(), spacePt.getY());
                        grid.moveTo(quarantinedPatient, this.getX(), this.getY());
                        CloseContacts.remove(closeContact);
                    }
               }else {
                   // 未隔离的密切接触者有防控措施的一定概率成为潜伏者
                   // 未隔离的密切接触者没有防控措施的90%概率成为潜伏者
                   becomeLatent();
               }

        }
        //14天后没有没有处理变为健康着
        else if (SystemParameter.worldTime - worldTime == 14) {
       
            CloseContact closeContact =this;
            NdPoint spacePt = space.getLocation(closeContact);
            Context<Object> context = ContextUtils.getContext(closeContact);
            if (context != null) {
                context.remove(closeContact);
        
        		HealthyPerson healthyPerson = new HealthyPerson(space, grid, city, this.getX(), this.getY(),
    					SystemFunction.createRandomAge(), SystemFunction.createRandomTemper(),this.regional,false);
    			
    			healthyPerson.init();
    			context.add(healthyPerson);
                // * 密切接触者-1
                DataStorage.dataStorage.setCONTACT_Num(DataStorage.dataStorage.getCONTACT_Num() - 1);
                space.moveTo(healthyPerson, spacePt.getX(), spacePt.getY());
                grid.moveTo(healthyPerson, this.getX(), this.getY());
                CloseContacts.remove(closeContact);
            }
        
        }
    }


    /**
     * 未隔离的密切接触者有防控措施的一定概率成为潜伏者 // 未隔离的密切接触者没有防控措施的按年龄概率成为潜伏者
     *
     * @throws InterruptedException
     */

    private void becomeLatent() {
            CloseContact closeContact = this;
            double infectRate = quarantinedRate(closeContact);
        System.out.println("变成潜伏者的概率"+infectRate);
            // 在一定防控措施下变为潜伏者
            if (MathUtil.randomSelect(infectRate)) {

                NdPoint spacePt = space.getLocation(closeContact);
                Context<Object> context = ContextUtils.getContext(closeContact);
                if (context != null) {
                    context.remove(closeContact);
                    
                    // 变为潜伏者
                    // 密切接触者是否打疫苗
                    boolean vaccination = this.isVaccination();
                    boolean wearMask = this.isWearMask();
                    LatentPatient latentPatient = new LatentPatient(space, grid, City.getInstance(), this.getX(),
                            this.getY(), this.getAge(), SystemParameter.worldTime, this.getTemperature(), this.regional);
                    latentPatient.setVaccination(vaccination);
                    latentPatient.setWearMask(wearMask);
                    latentPatient.init();
                    // * 密切接触者-1
                    DataStorage.dataStorage.setCONTACT_Num(DataStorage.dataStorage.getCONTACT_Num() - 1);
                    context.add(latentPatient);
                    space.moveTo(latentPatient, spacePt.getX(), spacePt.getY());
                    grid.moveTo(latentPatient, this.getX(), this.getY());
                    CloseContacts.remove(closeContact);
//				System.out.println("新增有防控措施潜伏者==================");

            }

        }

    }

    /**
     * 密切接触者有防控措施的成为潜伏者的概率 没有防控措施的按年龄概率成为潜伏者
     */

    private double quarantinedRate(CloseContact closeContact) {
        double infectRate = 0;
        //当前温度影响下的感染率率
        double infectionRateByTemper = SystemFunction.infectionRateByTemper(temperature);
        if (closeContact.isVaccination()  && closeContact.isWearMask()) {
            double rate1 = SystemFunction.infectionRateByVaccination(closeContact.getAge());
            double rate2 = SystemFunction.infectionRateByWearCost(closeContact.getAge());
            infectRate = rate1 * rate2 + infectionRateByTemper;
            return infectRate;
        } else if (closeContact.isVaccination()) {
            infectRate = SystemFunction.infectionRateByVaccination(closeContact.getAge());
            return infectRate + infectionRateByTemper;
        } else if (closeContact.isWearMask() ) {
            infectRate = SystemFunction.infectionRateByWearCost(closeContact.getAge());
            return infectRate + infectionRateByTemper;
        }
        // 没有措施感染概率根据年龄得出0.9 closeContact.isVaccination() == false &&
        else if (!closeContact.isVaccination()  && !closeContact.isWearMask()) {
            infectRate = SystemFunction.infectionRateByAge(closeContact.getAge());
            return infectRate + infectionRateByTemper;
        }
        return 0.0;
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

    public int getContactMoment() {
        return contactMoment;
    }

    public void setContactMoment(int contactMoment) {
        this.contactMoment = contactMoment;
    }

    public List<Object> getCloseContacts() {
        return CloseContacts;
    }

    public void add(List<Object> closeContacts) {
        CloseContacts = closeContacts;
    }

    public void regionalStatistics() {
        switch (this.regional) {
            case "XD":
                DataStorage.dataStorage.setXDCloseContact(DataStorage.dataStorage.getXDCloseContact() + 1);
                break;
            case "JCP":
                DataStorage.dataStorage.setJCPCloseContact(DataStorage.dataStorage.getJCPCloseContact() + 1);
                break;
            case "wbl":
                DataStorage.dataStorage.setWblCloseContact(DataStorage.dataStorage.getWblCloseContact() + 1);
                break;
            case "jy":
                DataStorage.dataStorage.setJyCloseContact(DataStorage.dataStorage.getJyCloseContact() + 1);
                break;
            case "XHL":
                DataStorage.dataStorage.setXHLCloseContact(DataStorage.dataStorage.getXHLCloseContact() + 1);
                break;
            case "YZ":
                DataStorage.dataStorage.setYZCloseContact(DataStorage.dataStorage.getYZCloseContact() + 1);
                break;
            default:

                break;
        }
    }
}


