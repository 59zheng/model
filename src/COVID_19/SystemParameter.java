package COVID_19;

/**
 * 系统参数
 * 
 * @author Beyond
 *
 */
public class SystemParameter {
	/**
	 * 世界时间
	 */
	public static int worldTime = 0;
	/**
	 * 政府防控等级 1 2 3 4 4级无防控
	 */
	public static int controlLevel = 4;

	/**
	 * 城市总人口数量3002159 230935
	 * 
	 */
	public static int CITY_PERSON_SIZE = 230935;
	// public static int CITY_PERSON_SIZE = 3002159;-83倍
	/**
	 * 小店人口数量// 679766 53397
	 * 
	 */
	// public static int XD_PERSON_SIZE = 6694166;
	public static int XD_PERSON_SIZE = 53397;
	/**
	 * 迎泽区人口数量547718 -12 42132
	 * 
	 */
	public static int YZ_PERSON_SIZE = 42132;

	/**
	 * 万柏林人口数量592184 45553
	 * 
	 */
	public static int WBL_PERSON_SIZE = 45553;
	/**
	 * 杏花岭总人口数量619463 47651
	 * 
	 */
	public static int XHL_PERSON_SIZE = 47651;
	/**
	 * 晋源总人口数量215551 16581
	 * 
	 */
	public static int JY_PERSON_SIZE = 16581;
	/**
	 * 尖草坪总人口数量213110 25621
	 * 
	 */
	public static int JCP_PERSON_SIZE = 25621;

	/**
	 * 初始感染数量
	 */
	public static int ORIGINAL_COUNT = 1;
	/**
	 * 初始潜伏者数量
	 */
	public static int LatentPatient_COUNT = 3;

	// /**
	// * 传播率。无措施时的接触传播率为1%~10%,戴口罩之后变为原来的0.2倍。
	// */
	// public static double BROAD_RATE = 0.2;
	/**
	 * 潜伏时间，14天为14 德尔塔5天左右
	 */
	public static float SHADOW_TIME = 4.4f;
	/**
	 * 医院收治响应时间
	 */
	public static int HOSPITAL_RECEIVE_TIME = 2;
	/**
	 * 医院床位
	 */
	public static int BED_COUNT = 2576;

	/**
	 * 流动意向平均值，建议调整范围：[-0.99,0.99]
	 * <p>
	 * -0.99 人群流动最慢速率，甚至完全控制疫情传播 。0.99为人群流动最快速率, 可导致全城感染
	 */
	// public static float u = -0.33f;
	// 无防控措施流动能力加大0.99
	public static double u;
//	空间流动方差
	public static double sig = 1;
	/**
	 * 病死率，根据6月2日数据估算（病死数/确诊数）为0.0594
	 */
	// public static float FATALITY_RATE = 0.0594f;
	// 无防控措施11月8日4.7%
	public static float FATALITY_RATE=0.0594f;

	/**
	 * 死亡时间均值，30天，从发病（确诊）时开始计时
	 */
	public static int DIE_TIME = 30;
	// 无防控措施17
	// public static int DIE_TIME = 17;
	/**
	 * 死亡时间方差
	 */
	public static double DIE_VARIANCE = 1;

	/**
	 * 治愈时间均值，30天，从发病（确诊）时开始计时
	 */
	// 重症治愈时间均值
	public static int CURE_TIME = 18;
	// 轻症治愈时间均值
	public static int Mild_CURE_TIME = 14;
	/**
	 * 治愈时间方差
	 */
	public static double CURE_VARIANCE = 1;

	/**
	 * 城市大小即窗口边界，限制不允许出城
	 */
	public static final int CITY_WIDTH = 1032;
	public static final int CITY_HEIGHT = 959;
	/**
	 * 城市中心位置--政府所在地
	 * 
	 */
	public static final int CITY_X = 550;
	public static final int CITY_Y = 550;
	/**
	 * 医院中心位置
	 */
	public static final int HOSPITAL_X = 660;
	public static final int HOSPITAL_Y = 660;

	/**
	 * 年龄结构比例（0-14，15-64，65岁以上）
	 */
	public static double[] AgeStructureRate = { 0.1349, 0.7858, 0.0793 };

	/**
	 * 不同年龄段的感染率（0-9，10-19,20-29,30-39,40-49,50-59,60-69,70-79,80周岁以上） 生成随机数规则 int
	 * num = random.nextInt(224-9+1); int ratenum=(num+1)/1000;
	 */
	// public static double[] infectionRateByAge = { 0.0090, 0.0120, 0.0810, 0.1700,
	// 0.1920, 0.2240, 0.1920, 0.0880,0.0320 };
	// 无防控措施
	public static double[] infectionRateByAge = { 0.0090, 0.0120, 0.0810, 0.1700, 0.1920, 0.2240, 0.1920, 0.0880,
			0.0320 };

	// 不同温度的感染率
	public static double[] infectionRateByTemper = { 0.01, 0.01, 0.02, 0.03 };
	/**
	 * 不同月份的温度 // 2020年1-12月的平均温度
	 */
	public static double[] AverageTemperature = { -3.5, -0.5, 7.5, 14.5, 20.5, 23.5, 25.5, 24.5, 19, 12.5, 4.5, -2.5 };
	/**
	 * 温度 temperature
	 */
	public static double temperature = 4;

	/*
	 * 戴口罩后不同年龄段的感染率比不戴口罩降低50%（0-9，10-19,20-29,30-39,40-49,50-59,60-69,70-79,80周岁以上）
	 * 生成随机数规则 int num = random.nextInt(1120-45+1); int ratenum=(num+1)/10000;
	 */
	public static double[] infectionRateByWearCost = { 0.0045, 0.0060, 0.0405, 0.0850, 0.0960, 0.1120, 0.0960, 0.0440,
			0.0160 };
	/*
	 * 接种疫苗后不同年龄段的感染率降低29.2倍（0-9，10-19,20-29,30-39,40-49,50-59,60-69,70-79,80周岁以上）
	 * int num = random.nextInt(546-22+1); int ratenum=(num+1)/10000;
	 * 
	 * 
	 * 两种相乘 int num = random.nextInt(612-1+1); int ratenum=(num+1)/100000;
	 */
	public static double[] infectionRateByVaccination = { 0.0022, 0.0029, 0.0198, 0.0415, 0.0468, 0.0546, 0.0468,
			0.0215, 0.0078 };

	/**
	 * 不同年龄段的致死率（0-9，10-19,20-29,30-39,40-49,50-59,60-69,70-79,80周岁以上）
	 * 
	 */
	public static double[] DeathRateByAge = { 0.0010, 0.0070, 0.0180, 0.0370, 0.1270, 0.3020, 0.3050, 0.2030 };

	/**
	 * 根据新冠疫情风险等级划分新冠肺炎疫情防控应急响应等级密接者被隔离概率（高风险，中风险，低风险） 一、低中高风险等级划分标准
	 * 
	 * 1、低风险：无确诊病例或连续14天无新增确诊病例;
	 * 
	 * 2、中风险：14天内有新增确诊病例，累计确诊病例不超过50例，或累计确诊病例超过50例，14天内未发生聚集性疫情;
	 * 
	 * 3、高风险：累计病例超过50例，14天内有聚集性疫情发生。
	 * 
	 */
	public static double[] isolationRateByRisk = { 0.1, 0.5, 0.8, 0.99 };

	/**
	 * 根据新冠疫情风险等级划分新冠肺炎疫情防控应急响应等级密接者被隔离概率（高风险，中风险，低风险）
	 * 
	 * 应急响应等级 就诊时间/天 就诊率/% 隔离比例/% 疫苗接种率/% 自我防护率/% 流动意愿/% IV级 4 0.5 0 0 0.2 0.99 III级
	 * 3 0.5 0.5 0 0.5 0 II级 2 0.7 0.7 0.5 0.8 -0.5 I级 1 0.9 0.9 0.8 0.95 -0.99
	 * 
	 * 
	 */
	// 就诊时间
	public static double clinicalTime;
//	就诊率
	public static double outPatientRate;
//	隔离比例
	public static double isolationRatio;

	/**
	 * 接种疫苗的概率
	 */
	public static double VACCINATION_RATE;
	/**
	 * 戴口罩的概率 不
	 */
//public static double WEAR_WASK_RATE = 0.8;
//无防控措施0
	public static double WEAR_WASK_RATE;

//	级别1 I级	
	public static double[] controlLevel1 = { 1, 0.9, 0.9, 0.8, 0.95, -0.99 };
//	级别2
	public static double[] controlLevel2 = { 2, 0.7, 0.7, 0.5, 0.8, -0.5 };
//	级别3
	public static double[] controlLevel3 = { 3, 0.5, 0.5, 0, 0.5, 0 };
//	级别4 IV级	
	public static double[] controlLevel4 = { 4, 0.5, 0, 0, 0.2, 0.99 };

}
