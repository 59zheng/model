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
 * @author suya 确诊者
 */
public class DiagnosisPatient extends Point {
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
    private int cureTime = 0;// 治愈所需时间
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

    public DiagnosisPatient(ContinuousSpace<Object> space, Grid<Object> grid, City city, int x, int y, int age,
                            int quarantinedMoment, double temperature, String regional) {
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
        }


    }

    /**
     * 对各种状态的人进行不同的处理，更新发布市民健康状态
     */
    @ScheduledMethod(start = 1, interval = 1, priority = 6)
    public void update() {
        //System.out.println("确诊者判断是否出院");
        /**
         * 判断是否出院
         */
        judgeDischarge();
//        剩余床位的话救治随机救治当前没住院的确诊着
    }


    public void init() {
        /**
         * 确诊者数据统计
         */
        DataStorage.dataStorage.setCONFIRMED_Num(DataStorage.dataStorage.getCONFIRMED_Num() + 1);
        /**
         * 确诊者分年龄段统计
         */
        ageStatistics();
        regionalStatistics();

        //确定当前月份
        SystemFunction.monthByTemperature(temperature);
        /**
         * 轻症者数据统计
         */
        if (this.isMildDisease()) {
            DataStorage.dataStorage.setMILD_Num(DataStorage.dataStorage.getMILD_Num() + 1);
        } else {
            DataStorage.dataStorage.setSEVERE_Num(DataStorage.dataStorage.getSEVERE_Num() + 1);

        }
        /**
         * 初始化时候就计算是否死亡，和死亡时间 只要打疫苗就不会死亡
         */
        if (!this.isVaccination) {
            destiny();
        } else {
            countCureMoment();
        }

    }

    /**
     * 判断是否出院
     */
    private void judgeDischarge() {
        Context<Object> context = ContextUtils.getContext(this);
        if (this.isHospitalized()  && (SystemParameter.worldTime - this.getHospitalizationMoment()) > this.cureTime) {
            //System.out.println("可能死亡");
            if (SystemParameter.worldTime == this.dieMoment) {
                /**
                 * 在医院中死亡
                 */
                System.out.println("在医院中死亡dieMoment" + dieMoment);
                context.remove(this);
                /**
                 * 死亡数+1
                 */
                DataStorage.dataStorage.setDEATH_Num(DataStorage.dataStorage.getDEATH_Num() + 1);
                // * 确诊者-1
                DataStorage.dataStorage.setCONFIRMED_Num(DataStorage.dataStorage.getCONFIRMED_Num() - 1);
                /**
                 * 床位数+1
                 */
                DataStorage.dataStorage.setUnuseBedNum(DataStorage.dataStorage.getUnuseBedNum() + 1);
                DataStorage.dataStorage.setUseBedNum(DataStorage.dataStorage.getUseBedNum() - 1);
            }

        } else if (this.isHospitalized() && (SystemParameter.worldTime - this.getHospitalizationMoment()) == this.cureTime) {
            context.remove(this);
            HealthyPerson healthyPerson = new HealthyPerson(space, grid, City.getInstance(), this.getX(), this.getY(),
                    this.getAge(), this.getTemperature(), this.regional,true);
            context.add(healthyPerson);
            grid.moveTo(healthyPerson, healthyPerson.getX(), healthyPerson.getY());
            System.out.println("我康复变为健康者了");
            // * 确诊者-1
            DataStorage.dataStorage.setCONFIRMED_Num(DataStorage.dataStorage.getCONFIRMED_Num() - 1);
            /**
             * 床位数+1
             */
            DataStorage.dataStorage.setUnuseBedNum(DataStorage.dataStorage.getUnuseBedNum() + 1);
            DataStorage.dataStorage.setUseBedNum(DataStorage.dataStorage.getUseBedNum() - 1);
            /**
             * 康复者数据统计
             */
            DataStorage.dataStorage.setCURED_Num(DataStorage.dataStorage.getCURED_Num() + 1);
            System.out.println("出院");
        } else if (!this.isHospitalized() && (SystemParameter.worldTime == this.dieMoment)) {
            System.out.println("未住院死亡");
            context.remove(this);
            /**
             * 死亡数+1
             */
            DataStorage.dataStorage.setDEATH_Num(DataStorage.dataStorage.getDEATH_Num() + 1);
            // * 确诊者-1
            DataStorage.dataStorage.setCONFIRMED_Num(DataStorage.dataStorage.getCONFIRMED_Num() - 1);
        }
//        是否有空余床位
        else  if (DataStorage.dataStorage.getUnuseBedNum() > 0) {
        	hospitalizationMoment = SystemParameter.worldTime;
            //System.out.println("潜伏isHospitalized"+isHospitalized);
            this.setHospitalized(true);
            this.setHospitalizationMoment(hospitalizationMoment);
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

    /**
     * 确诊者分年龄段统计
     */
    private void ageStatistics() {
        if (this.age >= 0 && this.age < 5) {
            DataStorage.dataStorage.setAge_0_4_Patient_Num(DataStorage.dataStorage.getAge_0_4_Patient_Num() + 1);
        } else if (this.age >= 5 && this.age < 10) {
            DataStorage.dataStorage.setAge_5_9_Patient_Num(DataStorage.dataStorage.getAge_5_9_Patient_Num() + 1);
        } else if (this.age >= 10 && this.age < 15) {
            DataStorage.dataStorage.setAge_10_14_Patient_Num(DataStorage.dataStorage.getAge_10_14_Patient_Num() + 1);
        } else if (this.age >= 15 && this.age < 20) {
            DataStorage.dataStorage.setAge_15_19_Patient_Num(DataStorage.dataStorage.getAge_15_19_Patient_Num() + 1);
        } else if (this.age >= 20 && this.age < 25) {
            DataStorage.dataStorage.setAge_20_24_Patient_Num(DataStorage.dataStorage.getAge_20_24_Patient_Num() + 1);
        } else if (this.age >= 25 && this.age < 30) {
            DataStorage.dataStorage.setAge_25_29_Patient_Num(DataStorage.dataStorage.getAge_25_29_Patient_Num() + 1);
        } else if (this.age >= 30 && this.age < 35) {
            DataStorage.dataStorage.setAge_30_34_Patient_Num(DataStorage.dataStorage.getAge_30_34_Patient_Num() + 1);
        } else if (this.age >= 35 && this.age < 40) {
            DataStorage.dataStorage.setAge_35_39_Patient_Num(DataStorage.dataStorage.getAge_35_39_Patient_Num() + 1);
        } else if (this.age >= 40 && this.age < 45) {
            DataStorage.dataStorage.setAge_40_44_Patient_Num(DataStorage.dataStorage.getAge_40_44_Patient_Num() + 1);
        } else if (this.age >= 45 && this.age < 50) {
            DataStorage.dataStorage.setAge_45_49_Patient_Num(DataStorage.dataStorage.getAge_45_49_Patient_Num() + 1);
        } else if (this.age >= 50 && this.age < 55) {
            DataStorage.dataStorage.setAge_50_54_Patient_Num(DataStorage.dataStorage.getAge_50_54_Patient_Num() + 1);
        } else if (this.age >= 55 && this.age < 60) {
            DataStorage.dataStorage.setAge_55_59_Patient_Num(DataStorage.dataStorage.getAge_55_59_Patient_Num() + 1);
        } else if (this.age >= 60 && this.age < 65) {
            DataStorage.dataStorage.setAge_60_64_Patient_Num(DataStorage.dataStorage.getAge_60_64_Patient_Num() + 1);
        } else if (this.age >= 65 && this.age < 70) {
            DataStorage.dataStorage.setAge_65_69_Patient_Num(DataStorage.dataStorage.getAge_65_69_Patient_Num() + 1);
        } else if (this.age >= 70 && this.age < 75) {
            DataStorage.dataStorage.setAge_70_74_Patient_Num(DataStorage.dataStorage.getAge_70_74_Patient_Num() + 1);
        } else if (this.age >= 75 && this.age < 80) {
            DataStorage.dataStorage.setAge_75_79_Patient_Num(DataStorage.dataStorage.getAge_75_79_Patient_Num() + 1);
        } else if (this.age >= 80 && this.age < 85) {
            DataStorage.dataStorage.setAge_80_84_Patient_Num(DataStorage.dataStorage.getAge_80_84_Patient_Num() + 1);
        } else if (this.age >= 85 && this.age < 90) {
            DataStorage.dataStorage.setAge_85_89_Patient_Num(DataStorage.dataStorage.getAge_85_89_Patient_Num() + 1);
        } else if (this.age >= 90 && this.age < 95) {
            DataStorage.dataStorage.setAge_90_94_Patient_Num(DataStorage.dataStorage.getAge_90_94_Patient_Num() + 1);
        } else {
            DataStorage.dataStorage.setAge_95_100_Patient_Num(DataStorage.dataStorage.getAge_95_100_Patient_Num() + 1);
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
        if (destiny >= 1 && destiny <= (int) (SystemParameter.FATALITY_RATE * 10000)) {

            /**
             * 如果幸运数字落在死亡区间
             */
            int dieTime = -(int) MathUtil.stdGaussian(SystemParameter.DIE_VARIANCE, SystemParameter.DIE_TIME);
            /**
             * 发病后确定死亡时刻
             */
            this.dieMoment = SystemParameter.worldTime + dieTime;
            System.out.println("死亡时间" + this.dieMoment);
        } else {
            /**
             * 逃过了死神的魔爪
             */
            this.dieMoment = -1;
            countCureMoment();
        }
    }

    /**
     * 计算治愈时间
     */
    private void countCureMoment() {
        /**
         * 如果幸运数字落在治愈区间
         */
        if (this.isMildDisease()) {
            cureTime = -(int) MathUtil.stdGaussian(SystemParameter.CURE_VARIANCE, SystemParameter.Mild_CURE_TIME);
        } else {
            cureTime = -(int) MathUtil.stdGaussian(SystemParameter.CURE_VARIANCE, SystemParameter.CURE_TIME);
        }

        /**
         * 住院后确定治愈时间
         */
        this.cureTime = cureTime;

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

    public boolean isMildDisease() {
        return isMildDisease;
    }

    public void setMildDisease(boolean isMildDisease) {
        this.isMildDisease = isMildDisease;
    }

    public boolean isHospitalized() {
        return isHospitalized;
    }

    public void setHospitalized(boolean isHospitalized) {
        this.isHospitalized = isHospitalized;
    }

    public int getHospitalizationMoment() {
        return hospitalizationMoment;
    }

    public void setHospitalizationMoment(int hospitalizationMoment) {
        this.hospitalizationMoment = hospitalizationMoment;
    }

    public void regionalStatistics() {
        switch (this.regional) {
            case "XD":
                DataStorage.dataStorage.setXDDiagnosis(DataStorage.dataStorage.getXDDiagnosis() + 1);
                break;
            case "JCP":
                DataStorage.dataStorage.setJCPDiagnosis(DataStorage.dataStorage.getJCPDiagnosis() + 1);
                break;
            case "wbl":
                DataStorage.dataStorage.setWblDiagnosis(DataStorage.dataStorage.getWblDiagnosis() + 1);
                break;
            case "jy":
                DataStorage.dataStorage.setJyDiagnosis(DataStorage.dataStorage.getJyDiagnosis() + 1);
                break;
            case "XHL":
                DataStorage.dataStorage.setXHLDiagnosis(DataStorage.dataStorage.getXHLDiagnosis() + 1);
                break;
            case "YZ":
                DataStorage.dataStorage.setYZDiagnosis(DataStorage.dataStorage.getYZDiagnosis() + 1);
                break;
            default:

                break;
        }
    }
}
