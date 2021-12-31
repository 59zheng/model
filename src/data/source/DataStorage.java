package data.source;
import java.util.ArrayList;
import java.util.List;

import COVID_19.SystemParameter;
import repast.simphony.engine.schedule.ScheduledMethod;

public class DataStorage {

	public static DataStorage dataStorage;

	public static DataStorage getInstance() {
		if (dataStorage == null) {
			dataStorage = new DataStorage();
		}

		return dataStorage;
	}

	//@ScheduledMethod(start = 1, interval = 1, priority = 1)
	public void step() {
		 //System.out.println("NORMAL_Num"+NORMAL_Num);
		System.out.println("数据清除");
		 System.out.println("初始化移动要新增人数先把初始化的人数删除了,优先级1");
			unuseBedNum = getUnuseBedNum();
			NORMAL_Num =Math.abs(getNORMAL_Num()- SystemParameter.CITY_PERSON_SIZE);
			SHADOW_Num=Math.abs(getSHADOW_Num()-SystemParameter.LatentPatient_COUNT);
			CONFIRMED_Num=Math.abs(getCONFIRMED_Num()-SystemParameter.ORIGINAL_COUNT);
			Hospital_Num=9;

			CONTACT_Num=getCONTACT_Num();
			MILD_Num=getMILD_Num();
			SEVERE_Num=getSEVERE_Num();
			VACCINATION_Num=0;
			WEAR_MASK_Num=0;
			VACCINATION_MASK_Num = 0;
			
			useBedNum = getUseBedNum();
			SUSPECTED_Num = getSUSPECTED_Num();
			FREEZE_Num = getFREEZE_Num();
			DEATH_Num = getDEATH_Num();
			CURED_Num = getCURED_Num();
			Student_Num=0;
			Control_Level=0;
				
		
		Age_0_4_Patient_Num = 0;
		Age_5_9_Patient_Num = 0;
		Age_10_14_Patient_Num = 0;
		Age_15_19_Patient_Num = 0;
		Age_20_24_Patient_Num = 0;
		Age_25_29_Patient_Num = 0;
		Age_30_34_Patient_Num = 0;
		Age_35_39_Patient_Num = 0;
		Age_40_44_Patient_Num = 0;
		Age_45_49_Patient_Num = 0;
		Age_50_54_Patient_Num = 0;
		Age_55_59_Patient_Num = 0;
		Age_60_64_Patient_Num = 0;
		Age_65_69_Patient_Num = 0;
		Age_70_74_Patient_Num = 0;
		Age_75_79_Patient_Num = 0;
		Age_80_84_Patient_Num = 0;
		Age_85_89_Patient_Num = 0;
		Age_90_94_Patient_Num = 0;
		Age_95_100_Patient_Num = 0;

		Age_0_4_CloseContact_Num = 0;
		Age_5_9_CloseContact_Num = 0;
		Age_10_14_CloseContact_Num = 0;
		Age_15_19_CloseContact_Num = 0;
		Age_20_24_CloseContact_Num = 0;
		Age_25_29_CloseContact_Num = 0;
		Age_30_34_CloseContact_Num = 0;
		Age_35_39_CloseContact_Num = 0;
		Age_40_44_CloseContact_Num = 0;
		Age_45_49_CloseContact_Num = 0;
		Age_50_54_CloseContact_Num = 0;
		Age_55_59_CloseContact_Num = 0;
		Age_60_64_CloseContact_Num = 0;
		Age_65_69_CloseContact_Num = 0;
		Age_70_74_CloseContact_Num = 0;
		Age_75_79_CloseContact_Num = 0;
		Age_80_84_CloseContact_Num = 0;
		Age_85_89_CloseContact_Num = 0;
		Age_90_94_CloseContact_Num = 0;
		Age_95_100_CloseContact_Num = 0;

		Age_0_4_HealthyPerson_Num = 0;
		Age_5_9_HealthyPerson_Num = 0;
		Age_10_14_HealthyPerson_Num = 0;
		Age_15_19_HealthyPerson_Num = 0;
		Age_20_24_HealthyPerson_Num = 0;
		Age_25_29_HealthyPerson_Num = 0;
		Age_30_34_HealthyPerson_Num = 0;
		Age_35_39_HealthyPerson_Num = 0;
		Age_40_44_HealthyPerson_Num = 0;
		Age_45_49_HealthyPerson_Num = 0;
		Age_50_54_HealthyPerson_Num = 0;
		Age_55_59_HealthyPerson_Num = 0;
		Age_60_64_HealthyPerson_Num = 0;
		Age_65_69_HealthyPerson_Num = 0;
		Age_70_74_HealthyPerson_Num = 0;
		Age_75_79_HealthyPerson_Num = 0;
		Age_80_84_HealthyPerson_Num = 0;
		Age_85_89_HealthyPerson_Num = 0;
		Age_90_94_HealthyPerson_Num = 0;
		Age_95_100_HealthyPerson_Num = 0;

		Age_0_4_LatentPatient_Num = 0;
		Age_5_9_LatentPatient_Num = 0;
		Age_10_14_LatentPatient_Num = 0;
		Age_15_19_LatentPatient_Num = 0;
		Age_20_24_LatentPatient_Num = 0;
		Age_25_29_LatentPatient_Num = 0;
		Age_30_34_LatentPatient_Num = 0;
		Age_35_39_LatentPatient_Num = 0;
		Age_40_44_LatentPatient_Num = 0;
		Age_45_49_LatentPatient_Num = 0;
		Age_50_54_LatentPatient_Num = 0;
		Age_55_59_LatentPatient_Num = 0;
		Age_60_64_LatentPatient_Num = 0;
		Age_65_69_LatentPatient_Num = 0;
		Age_70_74_LatentPatient_Num = 0;
		Age_75_79_LatentPatient_Num = 0;
		Age_80_84_LatentPatient_Num = 0;
		Age_85_89_LatentPatient_Num = 0;
		Age_90_94_LatentPatient_Num = 0;
		Age_95_100_LatentPatient_Num = 0;

		
		Age_0_4_QuarantinedPatient_Num = 0;
		Age_5_9_QuarantinedPatient_Num = 0;
		Age_10_14_QuarantinedPatient_Num = 0;
		Age_15_19_QuarantinedPatient_Num = 0;
		Age_20_24_QuarantinedPatient_Num = 0;
		Age_25_29_QuarantinedPatient_Num = 0;
		Age_30_34_QuarantinedPatient_Num = 0;
		Age_35_39_QuarantinedPatient_Num = 0;
		Age_40_44_QuarantinedPatient_Num = 0;
		Age_45_49_QuarantinedPatient_Num = 0;
		Age_50_54_QuarantinedPatient_Num = 0;
		Age_55_59_QuarantinedPatient_Num = 0;
		Age_60_64_QuarantinedPatient_Num = 0;
		Age_65_69_QuarantinedPatient_Num = 0;
		Age_70_74_QuarantinedPatient_Num = 0;
		Age_75_79_QuarantinedPatient_Num = 0;
		Age_80_84_QuarantinedPatient_Num = 0;
		Age_85_89_QuarantinedPatient_Num = 0;
		Age_90_94_QuarantinedPatient_Num = 0;
		Age_95_100_QuarantinedPatient_Num = 0;
		
		JanuaryNum=0;
		FebruaryNum=0;
		 MarchNum=0;
		 AprilNum=0;
		 MayNum =0;
		 JuneNum=0;
		 JulyNum=0; 
		 AugustNum=0; 
		 SeptemberNum =0;
		 OctoberNum =0;
		 NovemberNum =0;
		 DecemberNum=0;
		 
	}

	/**
	 * 医院已使用床位数据
	 */
	private int unuseBedNum = 0;
	/**
	 * 医院未使用床位数据
	 */
	private int useBedNum = 0;
	/**
	 * 正常人数据
	 */
	private int NORMAL_Num = 0;
	/**
	 * 有暴露感染风险人数据
	 */
	private int SUSPECTED_Num = 0;
	/**
	 * 潜伏期人数据
	 */
	private int SHADOW_Num = 0;
	/**
	 * 发病且已确诊为感染病人数据
	 */
	private int CONFIRMED_Num = 0;
	/**
	 * 隔离治疗人数据
	 */
	private int FREEZE_Num = 0;
	/**
	 * 密切接触者数据
	 */
	private int CONTACT_Num = 0;
	/**
	 * 轻症者数据
	 */
	private int MILD_Num = 0;
	/**
	 * 重症者数据
	 */
	private int SEVERE_Num = 0;
	/**
	 * 接种疫苗者者数据
	 */
	private int VACCINATION_Num = 0;
	/**
	 * 佩戴口罩者数据
	 */
	private int WEAR_MASK_Num = 0;
	/**
	 * 佩戴口罩并接种疫苗者数据
	 */
	private int VACCINATION_MASK_Num = 0;
	/**
	 * 病死者数据
	 */
	private int DEATH_Num = 0;
	/**
	 * 已治愈出院的人数据
	 */
	private int CURED_Num = 0;
	/**
	 * 医院数量
	 */
	private int Hospital_Num = 0;
	/**
	 * 学生数量
	 */
	private int Student_Num = 0;
	/**
	 * 政府防控等级
	 */
	private int Control_Level = 0;
	

	/**
	 * 各个年龄段确诊人数统计
	 */
	private int Age_0_4_Patient_Num;
	private int Age_5_9_Patient_Num;
	private int Age_10_14_Patient_Num;
	private int Age_15_19_Patient_Num;
	private int Age_20_24_Patient_Num;
	private int Age_25_29_Patient_Num;
	private int Age_30_34_Patient_Num;
	private int Age_35_39_Patient_Num;
	private int Age_40_44_Patient_Num;
	private int Age_45_49_Patient_Num;
	private int Age_50_54_Patient_Num;
	private int Age_55_59_Patient_Num;
	private int Age_60_64_Patient_Num;
	private int Age_65_69_Patient_Num;
	private int Age_70_74_Patient_Num;
	private int Age_75_79_Patient_Num;
	private int Age_80_84_Patient_Num;
	private int Age_85_89_Patient_Num;
	private int Age_90_94_Patient_Num;
	private int Age_95_100_Patient_Num;

	/**
	 * 各个年龄段密接人数统计
	 */
	private int Age_0_4_CloseContact_Num;
	private int Age_5_9_CloseContact_Num;
	private int Age_10_14_CloseContact_Num;
	private int Age_15_19_CloseContact_Num;
	private int Age_20_24_CloseContact_Num;
	private int Age_25_29_CloseContact_Num;
	private int Age_30_34_CloseContact_Num;
	private int Age_35_39_CloseContact_Num;
	private int Age_40_44_CloseContact_Num;
	private int Age_45_49_CloseContact_Num;
	private int Age_50_54_CloseContact_Num;
	private int Age_55_59_CloseContact_Num;
	private int Age_60_64_CloseContact_Num;
	private int Age_65_69_CloseContact_Num;
	private int Age_70_74_CloseContact_Num;
	private int Age_75_79_CloseContact_Num;
	private int Age_80_84_CloseContact_Num;
	private int Age_85_89_CloseContact_Num;
	private int Age_90_94_CloseContact_Num;
	private int Age_95_100_CloseContact_Num;
	
		/**
	 * 各个年龄段健康人数统计
	 */
	private int Age_0_4_HealthyPerson_Num;
	private int Age_5_9_HealthyPerson_Num;
	private int Age_10_14_HealthyPerson_Num;
	private int Age_15_19_HealthyPerson_Num;
	private int Age_20_24_HealthyPerson_Num;
	private int Age_25_29_HealthyPerson_Num;
	private int Age_30_34_HealthyPerson_Num;
	private int Age_35_39_HealthyPerson_Num;
	private int Age_40_44_HealthyPerson_Num;
	private int Age_45_49_HealthyPerson_Num;
	private int Age_50_54_HealthyPerson_Num;
	private int Age_55_59_HealthyPerson_Num;
	private int Age_60_64_HealthyPerson_Num;
	private int Age_65_69_HealthyPerson_Num;
	private int Age_70_74_HealthyPerson_Num;
	private int Age_75_79_HealthyPerson_Num;
	private int Age_80_84_HealthyPerson_Num;
	private int Age_85_89_HealthyPerson_Num;
	private int Age_90_94_HealthyPerson_Num;
	private int Age_95_100_HealthyPerson_Num;

		/**
	 * 各个年龄段潜伏者人数统计
	 */
	private int Age_0_4_LatentPatient_Num;
	private int Age_5_9_LatentPatient_Num;
	private int Age_10_14_LatentPatient_Num;
	private int Age_15_19_LatentPatient_Num;
	private int Age_20_24_LatentPatient_Num;
	private int Age_25_29_LatentPatient_Num;
	private int Age_30_34_LatentPatient_Num;
	private int Age_35_39_LatentPatient_Num;
	private int Age_40_44_LatentPatient_Num;
	private int Age_45_49_LatentPatient_Num;
	private int Age_50_54_LatentPatient_Num;
	private int Age_55_59_LatentPatient_Num;
	private int Age_60_64_LatentPatient_Num;
	private int Age_65_69_LatentPatient_Num;
	private int Age_70_74_LatentPatient_Num;
	private int Age_75_79_LatentPatient_Num;
	private int Age_80_84_LatentPatient_Num;
	private int Age_85_89_LatentPatient_Num;
	private int Age_90_94_LatentPatient_Num;
	private int Age_95_100_LatentPatient_Num;

		/**
	 * 各个年龄段隔离人数统计
	 */
	private int Age_0_4_QuarantinedPatient_Num;
	private int Age_5_9_QuarantinedPatient_Num;
	private int Age_10_14_QuarantinedPatient_Num;
	private int Age_15_19_QuarantinedPatient_Num;
	private int Age_20_24_QuarantinedPatient_Num;
	private int Age_25_29_QuarantinedPatient_Num;
	private int Age_30_34_QuarantinedPatient_Num;
	private int Age_35_39_QuarantinedPatient_Num;
	private int Age_40_44_QuarantinedPatient_Num;
	private int Age_45_49_QuarantinedPatient_Num;
	private int Age_50_54_QuarantinedPatient_Num;
	private int Age_55_59_QuarantinedPatient_Num;
	private int Age_60_64_QuarantinedPatient_Num;
	private int Age_65_69_QuarantinedPatient_Num;
	private int Age_70_74_QuarantinedPatient_Num;
	private int Age_75_79_QuarantinedPatient_Num;
	private int Age_80_84_QuarantinedPatient_Num;
	private int Age_85_89_QuarantinedPatient_Num;
	private int Age_90_94_QuarantinedPatient_Num;
	private int Age_95_100_QuarantinedPatient_Num;
	
	
	/**
	 * 各个月份确诊人数统计
	 */
	private int JanuaryNum;
	private int FebruaryNum;
	private int MarchNum;
	private int AprilNum;
	private int MayNum;
	private int JuneNum;
	private int JulyNum;
	private int AugustNum;
	private int SeptemberNum;
	private int OctoberNum;
	private int NovemberNum;
	private int DecemberNum;
	
	
	/*
	* 各个区域 各个智能体统计
	* */
	private int XDHealthy;
	private int XDCollegeStudent;
	private int XDCloseContact;



	private int XDLatent;
	private int XDQuarantined;
	private int XDDiagnosis;
	private int JCPHealthy;
	private int JCPCollegeStudent;
	private int JCPCloseContact;
	private int JCPLatent;
	private int JCPQuarantined;
	private int JCPDiagnosis;
	private int wblHealthy;
	private int wblCollegeStudent;
	private int wblCloseContact;
	private int wblLatent;
	private int wblQuarantined;
	private int wblDiagnosis;
	private int jyHealthy;
	private int jyCollegeStudent;
	private int jyCloseContact;
	private int jyLatent;
	private int jyQuarantined;
	private int jyDiagnosis;
	private int XHLHealthy;
	private int XHLCollegeStudent;
	private int XHLCloseContact;
	private int XHLLatent;
	private int XHLQuarantined;
	private int XHLDiagnosis;
	private int YZHealthy;
	private int YZCollegeStudent;
	private int YZCloseContact;
	private int YZLatent;
	private int YZQuarantined;
	private int YZDiagnosis;


//	public static void main(String[] args) {
//		String[] arr={"XD","JCP","wbl","jy","XHL","YZ"};
//		String[] arr2={"Healthy","CollegeStudent","CloseContact","Latent","Quarantined","Diagnosis"};
//
//		List<String> objects = new ArrayList<String>();
//String a="private int %s;";
//		for (String s : arr) {
//
//			for (String s1 : arr2) {
//				String format = String.format(a, s + s1);
//				objects.add(format);
//			}
//		}
//		objects.forEach(System.out::println);
//
//
//	}


	public int getXDHealthy() {
		return XDHealthy;
	}

	public void setXDHealthy(int XDHealthy) {
		this.XDHealthy = XDHealthy;
	}

	public int getXDCollegeStudent() {
		return XDCollegeStudent;
	}

	public void setXDCollegeStudent(int XDCollegeStudent) {
		this.XDCollegeStudent = XDCollegeStudent;
	}

	public int getXDCloseContact() {
		return XDCloseContact;
	}

	public void setXDCloseContact(int XDCloseContact) {
		this.XDCloseContact = XDCloseContact;
	}

	public int getXDLatent() {
		return XDLatent;
	}

	public void setXDLatent(int XDLatent) {
		this.XDLatent = XDLatent;
	}

	public int getXDQuarantined() {
		return XDQuarantined;
	}

	public void setXDQuarantined(int XDQuarantined) {
		this.XDQuarantined = XDQuarantined;
	}

	public int getXDDiagnosis() {
		return XDDiagnosis;
	}

	public void setXDDiagnosis(int XDDiagnosis) {
		this.XDDiagnosis = XDDiagnosis;
	}

	public int getJCPHealthy() {
		return JCPHealthy;
	}

	public void setJCPHealthy(int JCPHealthy) {
		this.JCPHealthy = JCPHealthy;
	}

	public int getJCPCollegeStudent() {
		return JCPCollegeStudent;
	}

	public void setJCPCollegeStudent(int JCPCollegeStudent) {
		this.JCPCollegeStudent = JCPCollegeStudent;
	}

	public int getJCPCloseContact() {
		return JCPCloseContact;
	}

	public void setJCPCloseContact(int JCPCloseContact) {
		this.JCPCloseContact = JCPCloseContact;
	}

	public int getJCPLatent() {
		return JCPLatent;
	}

	public void setJCPLatent(int JCPLatent) {
		this.JCPLatent = JCPLatent;
	}

	public int getJCPQuarantined() {
		return JCPQuarantined;
	}

	public void setJCPQuarantined(int JCPQuarantined) {
		this.JCPQuarantined = JCPQuarantined;
	}

	public int getJCPDiagnosis() {
		return JCPDiagnosis;
	}

	public void setJCPDiagnosis(int JCPDiagnosis) {
		this.JCPDiagnosis = JCPDiagnosis;
	}

	public int getWblHealthy() {
		return wblHealthy;
	}

	public void setWblHealthy(int wblHealthy) {
		this.wblHealthy = wblHealthy;
	}

	public int getWblCollegeStudent() {
		return wblCollegeStudent;
	}

	public void setWblCollegeStudent(int wblCollegeStudent) {
		this.wblCollegeStudent = wblCollegeStudent;
	}

	public int getWblCloseContact() {
		return wblCloseContact;
	}

	public void setWblCloseContact(int wblCloseContact) {
		this.wblCloseContact = wblCloseContact;
	}

	public int getWblLatent() {
		return wblLatent;
	}

	public void setWblLatent(int wblLatent) {
		this.wblLatent = wblLatent;
	}

	public int getWblQuarantined() {
		return wblQuarantined;
	}

	public void setWblQuarantined(int wblQuarantined) {
		this.wblQuarantined = wblQuarantined;
	}

	public int getWblDiagnosis() {
		return wblDiagnosis;
	}

	public void setWblDiagnosis(int wblDiagnosis) {
		this.wblDiagnosis = wblDiagnosis;
	}

	public int getJyHealthy() {
		return jyHealthy;
	}

	public void setJyHealthy(int jyHealthy) {
		this.jyHealthy = jyHealthy;
	}

	public int getJyCollegeStudent() {
		return jyCollegeStudent;
	}

	public void setJyCollegeStudent(int jyCollegeStudent) {
		this.jyCollegeStudent = jyCollegeStudent;
	}

	public int getJyCloseContact() {
		return jyCloseContact;
	}

	public void setJyCloseContact(int jyCloseContact) {
		this.jyCloseContact = jyCloseContact;
	}

	public int getJyLatent() {
		return jyLatent;
	}

	public void setJyLatent(int jyLatent) {
		this.jyLatent = jyLatent;
	}

	public int getJyQuarantined() {
		return jyQuarantined;
	}

	public void setJyQuarantined(int jyQuarantined) {
		this.jyQuarantined = jyQuarantined;
	}

	public int getJyDiagnosis() {
		return jyDiagnosis;
	}

	public void setJyDiagnosis(int jyDiagnosis) {
		this.jyDiagnosis = jyDiagnosis;
	}

	public int getXHLHealthy() {
		return XHLHealthy;
	}

	public void setXHLHealthy(int XHLHealthy) {
		this.XHLHealthy = XHLHealthy;
	}

	public int getXHLCollegeStudent() {
		return XHLCollegeStudent;
	}

	public void setXHLCollegeStudent(int XHLCollegeStudent) {
		this.XHLCollegeStudent = XHLCollegeStudent;
	}

	public int getXHLCloseContact() {
		return XHLCloseContact;
	}

	public void setXHLCloseContact(int XHLCloseContact) {
		this.XHLCloseContact = XHLCloseContact;
	}

	public int getXHLLatent() {
		return XHLLatent;
	}

	public void setXHLLatent(int XHLLatent) {
		this.XHLLatent = XHLLatent;
	}

	public int getXHLQuarantined() {
		return XHLQuarantined;
	}

	public void setXHLQuarantined(int XHLQuarantined) {
		this.XHLQuarantined = XHLQuarantined;
	}

	public int getXHLDiagnosis() {
		return XHLDiagnosis;
	}

	public void setXHLDiagnosis(int XHLDiagnosis) {
		this.XHLDiagnosis = XHLDiagnosis;
	}

	public int getYZHealthy() {
		return YZHealthy;
	}

	public void setYZHealthy(int YZHealthy) {
		this.YZHealthy = YZHealthy;
	}

	public int getYZCollegeStudent() {
		return YZCollegeStudent;
	}

	public void setYZCollegeStudent(int YZCollegeStudent) {
		this.YZCollegeStudent = YZCollegeStudent;
	}

	public int getYZCloseContact() {
		return YZCloseContact;
	}

	public void setYZCloseContact(int YZCloseContact) {
		this.YZCloseContact = YZCloseContact;
	}

	public int getYZLatent() {
		return YZLatent;
	}

	public void setYZLatent(int YZLatent) {
		this.YZLatent = YZLatent;
	}

	public int getYZQuarantined() {
		return YZQuarantined;
	}

	public void setYZQuarantined(int YZQuarantined) {
		this.YZQuarantined = YZQuarantined;
	}

	public int getYZDiagnosis() {
		return YZDiagnosis;
	}

	public void setYZDiagnosis(int YZDiagnosis) {
		this.YZDiagnosis = YZDiagnosis;
	}
	
	

	public int getCONTACT_Num() {
		return CONTACT_Num;
	}

	public void setCONTACT_Num(int cONTACT_Num) {
		CONTACT_Num = cONTACT_Num;
	}

	public int getControl_Level() {
		return Control_Level;
	}

	public void setControl_Level(int control_Level) {
		Control_Level = control_Level;
	}

	public int getJanuaryNum() {
		return JanuaryNum;
	}

	public void setJanuaryNum(int januaryNum) {
		JanuaryNum = januaryNum;
	}

	public int getFebruaryNum() {
		return FebruaryNum;
	}

	public void setFebruaryNum(int februaryNum) {
		FebruaryNum = februaryNum;
	}

	public int getMarchNum() {
		return MarchNum;
	}

	public void setMarchNum(int marchNum) {
		MarchNum = marchNum;
	}

	public int getAprilNum() {
		return AprilNum;
	}

	public void setAprilNum(int aprilNum) {
		AprilNum = aprilNum;
	}

	public int getMayNum() {
		return MayNum;
	}

	public void setMayNum(int mayNum) {
		MayNum = mayNum;
	}

	public int getJuneNum() {
		return JuneNum;
	}

	public void setJuneNum(int juneNum) {
		JuneNum = juneNum;
	}

	public int getJulyNum() {
		return JulyNum;
	}

	public void setJulyNum(int julyNum) {
		JulyNum = julyNum;
	}

	public int getAugustNum() {
		return AugustNum;
	}

	public void setAugustNum(int augustNum) {
		AugustNum = augustNum;
	}

	public int getSeptemberNum() {
		return SeptemberNum;
	}

	public void setSeptemberNum(int septemberNum) {
		SeptemberNum = septemberNum;
	}

	public int getOctoberNum() {
		return OctoberNum;
	}

	public void setOctoberNum(int octoberNum) {
		OctoberNum = octoberNum;
	}

	public int getNovemberNum() {
		return NovemberNum;
	}

	public void setNovemberNum(int novemberNum) {
		NovemberNum = novemberNum;
	}

	public int getDecemberNum() {
		return DecemberNum;
	}

	public void setDecemberNum(int decemberNum) {
		DecemberNum = decemberNum;
	}

	public int getUnuseBedNum() {
		return unuseBedNum;
	}

	public void setUnuseBedNum(int unuseBedNum) {
		this.unuseBedNum = unuseBedNum;
	}

	public int getUseBedNum() {
		return useBedNum;
	}

	public void setUseBedNum(int useBedNum) {
		this.useBedNum = useBedNum;
	}

	public int getNORMAL_Num() {
		return NORMAL_Num;
	}

	public void setNORMAL_Num(int nORMAL_Num) {
		NORMAL_Num = nORMAL_Num;
	}

	public int getSUSPECTED_Num() {
		return SUSPECTED_Num;
	}

	public void setSUSPECTED_Num(int sUSPECTED_Num) {
		SUSPECTED_Num = sUSPECTED_Num;
	}

	public int getSHADOW_Num() {
		return SHADOW_Num;
	}

	public void setSHADOW_Num(int sHADOW_Num) {
		SHADOW_Num = sHADOW_Num;
	}

	public int getCONFIRMED_Num() {
		return CONFIRMED_Num;
	}

	public void setCONFIRMED_Num(int cONFIRMED_Num) {
		CONFIRMED_Num = cONFIRMED_Num;
	}

	public int getFREEZE_Num() {
		return FREEZE_Num;
	}

	public void setFREEZE_Num(int fREEZE_Num) {
		FREEZE_Num = fREEZE_Num;
	}

	public int getDEATH_Num() {
		return DEATH_Num;
	}

	public void setDEATH_Num(int dEATH_Num) {
		DEATH_Num = dEATH_Num;
	}

	public int getCURED_Num() {
		return CURED_Num;
	}

	public void setCURED_Num(int cURED_Num) {
		CURED_Num = cURED_Num;
	}
	

	public int getMILD_Num() {
		return MILD_Num;
	}

	public void setMILD_Num(int mILD_Num) {
		MILD_Num = mILD_Num;
	}

	public int getSEVERE_Num() {
		return SEVERE_Num;
	}

	public void setSEVERE_Num(int sEVERE_Num) {
		SEVERE_Num = sEVERE_Num;
	}
	
	

	public int getVACCINATION_Num() {
		return VACCINATION_Num;
	}

	public void setVACCINATION_Num(int vACCINATION_Num) {
		VACCINATION_Num = vACCINATION_Num;
	}

	public int getWEAR_MASK_Num() {
		return WEAR_MASK_Num;
	}

	public void setWEAR_MASK_Num(int wEAR_MASK_Num) {
		WEAR_MASK_Num = wEAR_MASK_Num;
	}

	public int getVACCINATION_MASK_Num() {
		return VACCINATION_MASK_Num;
	}

	public void setVACCINATION_MASK_Num(int vACCINATION_MASK_Num) {
		VACCINATION_MASK_Num = vACCINATION_MASK_Num;
	}
	

	public int getHospital_Num() {
		return Hospital_Num;
	}

	public void setHospital_Num(int hospital_Num) {
		Hospital_Num = hospital_Num;
	}

	public int getAge_0_4_Patient_Num() {
		return Age_0_4_Patient_Num;
	}

	public void setAge_0_4_Patient_Num(int age_0_4_Patient_Num) {
		Age_0_4_Patient_Num = age_0_4_Patient_Num;
	}

	public int getAge_5_9_Patient_Num() {
		return Age_5_9_Patient_Num;
	}

	public void setAge_5_9_Patient_Num(int age_5_9_Patient_Num) {
		Age_5_9_Patient_Num = age_5_9_Patient_Num;
	}

	public int getAge_10_14_Patient_Num() {
		return Age_10_14_Patient_Num;
	}

	public void setAge_10_14_Patient_Num(int age_10_14_Patient_Num) {
		Age_10_14_Patient_Num = age_10_14_Patient_Num;
	}

	public int getAge_15_19_Patient_Num() {
		return Age_15_19_Patient_Num;
	}

	public void setAge_15_19_Patient_Num(int age_15_19_Patient_Num) {
		Age_15_19_Patient_Num = age_15_19_Patient_Num;
	}

	public int getAge_20_24_Patient_Num() {
		return Age_20_24_Patient_Num;
	}

	public void setAge_20_24_Patient_Num(int age_20_24_Patient_Num) {
		Age_20_24_Patient_Num = age_20_24_Patient_Num;
	}

	public int getAge_25_29_Patient_Num() {
		return Age_25_29_Patient_Num;
	}

	public void setAge_25_29_Patient_Num(int age_25_29_Patient_Num) {
		Age_25_29_Patient_Num = age_25_29_Patient_Num;
	}

	public int getAge_30_34_Patient_Num() {
		return Age_30_34_Patient_Num;
	}

	public void setAge_30_34_Patient_Num(int age_30_34_Patient_Num) {
		Age_30_34_Patient_Num = age_30_34_Patient_Num;
	}

	public int getAge_35_39_Patient_Num() {
		return Age_35_39_Patient_Num;
	}

	public void setAge_35_39_Patient_Num(int age_35_39_Patient_Num) {
		Age_35_39_Patient_Num = age_35_39_Patient_Num;
	}

	public int getAge_40_44_Patient_Num() {
		return Age_40_44_Patient_Num;
	}

	public void setAge_40_44_Patient_Num(int age_40_44_Patient_Num) {
		Age_40_44_Patient_Num = age_40_44_Patient_Num;
	}

	public int getAge_45_49_Patient_Num() {
		return Age_45_49_Patient_Num;
	}

	public void setAge_45_49_Patient_Num(int age_45_49_Patient_Num) {
		Age_45_49_Patient_Num = age_45_49_Patient_Num;
	}

	public int getAge_50_54_Patient_Num() {
		return Age_50_54_Patient_Num;
	}

	public void setAge_50_54_Patient_Num(int age_50_54_Patient_Num) {
		Age_50_54_Patient_Num = age_50_54_Patient_Num;
	}

	public int getAge_55_59_Patient_Num() {
		return Age_55_59_Patient_Num;
	}

	public void setAge_55_59_Patient_Num(int age_55_59_Patient_Num) {
		Age_55_59_Patient_Num = age_55_59_Patient_Num;
	}

	public int getAge_60_64_Patient_Num() {
		return Age_60_64_Patient_Num;
	}

	public void setAge_60_64_Patient_Num(int age_60_64_Patient_Num) {
		Age_60_64_Patient_Num = age_60_64_Patient_Num;
	}

	public int getAge_65_69_Patient_Num() {
		return Age_65_69_Patient_Num;
	}

	public void setAge_65_69_Patient_Num(int age_65_69_Patient_Num) {
		Age_65_69_Patient_Num = age_65_69_Patient_Num;
	}

	public int getAge_70_74_Patient_Num() {
		return Age_70_74_Patient_Num;
	}

	public void setAge_70_74_Patient_Num(int age_70_74_Patient_Num) {
		Age_70_74_Patient_Num = age_70_74_Patient_Num;
	}

	public int getAge_75_79_Patient_Num() {
		return Age_75_79_Patient_Num;
	}

	public void setAge_75_79_Patient_Num(int age_75_79_Patient_Num) {
		Age_75_79_Patient_Num = age_75_79_Patient_Num;
	}

	public int getAge_80_84_Patient_Num() {
		return Age_80_84_Patient_Num;
	}

	public void setAge_80_84_Patient_Num(int age_80_84_Patient_Num) {
		Age_80_84_Patient_Num = age_80_84_Patient_Num;
	}

	public int getAge_85_89_Patient_Num() {
		return Age_85_89_Patient_Num;
	}

	public void setAge_85_89_Patient_Num(int age_85_89_Patient_Num) {
		Age_85_89_Patient_Num = age_85_89_Patient_Num;
	}

	public int getAge_90_94_Patient_Num() {
		return Age_90_94_Patient_Num;
	}

	public void setAge_90_94_Patient_Num(int age_90_94_Patient_Num) {
		Age_90_94_Patient_Num = age_90_94_Patient_Num;
	}

	public int getAge_95_100_Patient_Num() {
		return Age_95_100_Patient_Num;
	}

	public void setAge_95_100_Patient_Num(int age_95_100_Patient_Num) {
		Age_95_100_Patient_Num = age_95_100_Patient_Num;
	}

	public int getAge_0_4_CloseContact_Num() {
		return Age_0_4_CloseContact_Num;
	}

	public void setAge_0_4_CloseContact_Num(int age_0_4_CloseContact_Num) {
		Age_0_4_CloseContact_Num = age_0_4_CloseContact_Num;
	}

	public int getAge_5_9_CloseContact_Num() {
		return Age_5_9_CloseContact_Num;
	}

	public void setAge_5_9_CloseContact_Num(int age_5_9_CloseContact_Num) {
		Age_5_9_CloseContact_Num = age_5_9_CloseContact_Num;
	}

	public int getAge_10_14_CloseContact_Num() {
		return Age_10_14_CloseContact_Num;
	}

	public void setAge_10_14_CloseContact_Num(int age_10_14_CloseContact_Num) {
		Age_10_14_CloseContact_Num = age_10_14_CloseContact_Num;
	}

	public int getAge_15_19_CloseContact_Num() {
		return Age_15_19_CloseContact_Num;
	}

	public void setAge_15_19_CloseContact_Num(int age_15_19_CloseContact_Num) {
		Age_15_19_CloseContact_Num = age_15_19_CloseContact_Num;
	}

	public int getAge_20_24_CloseContact_Num() {
		return Age_20_24_CloseContact_Num;
	}

	public void setAge_20_24_CloseContact_Num(int age_20_24_CloseContact_Num) {
		Age_20_24_CloseContact_Num = age_20_24_CloseContact_Num;
	}

	public int getAge_25_29_CloseContact_Num() {
		return Age_25_29_CloseContact_Num;
	}

	public void setAge_25_29_CloseContact_Num(int age_25_29_CloseContact_Num) {
		Age_25_29_CloseContact_Num = age_25_29_CloseContact_Num;
	}

	public int getAge_30_34_CloseContact_Num() {
		return Age_30_34_CloseContact_Num;
	}

	public void setAge_30_34_CloseContact_Num(int age_30_34_CloseContact_Num) {
		Age_30_34_CloseContact_Num = age_30_34_CloseContact_Num;
	}

	public int getAge_35_39_CloseContact_Num() {
		return Age_35_39_CloseContact_Num;
	}

	public void setAge_35_39_CloseContact_Num(int age_35_39_CloseContact_Num) {
		Age_35_39_CloseContact_Num = age_35_39_CloseContact_Num;
	}

	public int getAge_40_44_CloseContact_Num() {
		return Age_40_44_CloseContact_Num;
	}

	public void setAge_40_44_CloseContact_Num(int age_40_44_CloseContact_Num) {
		Age_40_44_CloseContact_Num = age_40_44_CloseContact_Num;
	}

	public int getAge_45_49_CloseContact_Num() {
		return Age_45_49_CloseContact_Num;
	}

	public void setAge_45_49_CloseContact_Num(int age_45_49_CloseContact_Num) {
		Age_45_49_CloseContact_Num = age_45_49_CloseContact_Num;
	}

	public int getAge_50_54_CloseContact_Num() {
		return Age_50_54_CloseContact_Num;
	}

	public void setAge_50_54_CloseContact_Num(int age_50_54_CloseContact_Num) {
		Age_50_54_CloseContact_Num = age_50_54_CloseContact_Num;
	}

	public int getAge_55_59_CloseContact_Num() {
		return Age_55_59_CloseContact_Num;
	}

	public void setAge_55_59_CloseContact_Num(int age_55_59_CloseContact_Num) {
		Age_55_59_CloseContact_Num = age_55_59_CloseContact_Num;
	}

	public int getAge_60_64_CloseContact_Num() {
		return Age_60_64_CloseContact_Num;
	}

	public void setAge_60_64_CloseContact_Num(int age_60_64_CloseContact_Num) {
		Age_60_64_CloseContact_Num = age_60_64_CloseContact_Num;
	}

	public int getAge_65_69_CloseContact_Num() {
		return Age_65_69_CloseContact_Num;
	}

	public void setAge_65_69_CloseContact_Num(int age_65_69_CloseContact_Num) {
		Age_65_69_CloseContact_Num = age_65_69_CloseContact_Num;
	}

	public int getAge_70_74_CloseContact_Num() {
		return Age_70_74_CloseContact_Num;
	}

	public void setAge_70_74_CloseContact_Num(int age_70_74_CloseContact_Num) {
		Age_70_74_CloseContact_Num = age_70_74_CloseContact_Num;
	}

	public int getAge_75_79_CloseContact_Num() {
		return Age_75_79_CloseContact_Num;
	}

	public void setAge_75_79_CloseContact_Num(int age_75_79_CloseContact_Num) {
		Age_75_79_CloseContact_Num = age_75_79_CloseContact_Num;
	}

	public int getAge_80_84_CloseContact_Num() {
		return Age_80_84_CloseContact_Num;
	}

	public void setAge_80_84_CloseContact_Num(int age_80_84_CloseContact_Num) {
		Age_80_84_CloseContact_Num = age_80_84_CloseContact_Num;
	}

	public int getAge_85_89_CloseContact_Num() {
		return Age_85_89_CloseContact_Num;
	}

	public void setAge_85_89_CloseContact_Num(int age_85_89_CloseContact_Num) {
		Age_85_89_CloseContact_Num = age_85_89_CloseContact_Num;
	}

	public int getAge_90_94_CloseContact_Num() {
		return Age_90_94_CloseContact_Num;
	}

	public void setAge_90_94_CloseContact_Num(int age_90_94_CloseContact_Num) {
		Age_90_94_CloseContact_Num = age_90_94_CloseContact_Num;
	}

	public int getAge_95_100_CloseContact_Num() {
		return Age_95_100_CloseContact_Num;
	}

	public void setAge_95_100_CloseContact_Num(int age_95_100_CloseContact_Num) {
		Age_95_100_CloseContact_Num = age_95_100_CloseContact_Num;
	}

	public int getAge_0_4_HealthyPerson_Num() {
		return Age_0_4_HealthyPerson_Num;
	}

	public void setAge_0_4_HealthyPerson_Num(int age_0_4_HealthyPerson_Num) {
		Age_0_4_HealthyPerson_Num = age_0_4_HealthyPerson_Num;
	}
	public int getAge_5_9_HealthyPerson_Num() {
		return Age_5_9_HealthyPerson_Num;
	}

	public void setAge_5_9_HealthyPerson_Num(int age_5_9_HealthyPerson_Num) {
		Age_5_9_HealthyPerson_Num = age_5_9_HealthyPerson_Num;
	}

	public int getAge_10_14_HealthyPerson_Num() {
		return Age_10_14_HealthyPerson_Num;
	}

	public void setAge_10_14_HealthyPerson_Num(int age_10_14_HealthyPerson_Num) {
		Age_10_14_HealthyPerson_Num = age_10_14_HealthyPerson_Num;
	}

	public int getAge_15_19_HealthyPerson_Num() {
		return Age_15_19_HealthyPerson_Num;
	}

	public void setAge_15_19_HealthyPerson_Num(int age_15_19_HealthyPerson_Num) {
		Age_15_19_HealthyPerson_Num = age_15_19_HealthyPerson_Num;
	}

	public int getAge_20_24_HealthyPerson_Num() {
		return Age_20_24_HealthyPerson_Num;
	}

	public void setAge_20_24_HealthyPerson_Num(int age_20_24_HealthyPerson_Num) {
		Age_20_24_HealthyPerson_Num = age_20_24_HealthyPerson_Num;
	}

	public int getAge_25_29_HealthyPerson_Num() {
		return Age_25_29_HealthyPerson_Num;
	}

	public void setAge_25_29_HealthyPerson_Num(int age_25_29_HealthyPerson_Num) {
		Age_25_29_HealthyPerson_Num = age_25_29_HealthyPerson_Num;
	}

	public int getAge_30_34_HealthyPerson_Num() {
		return Age_30_34_HealthyPerson_Num;
	}

	public void setAge_30_34_HealthyPerson_Num(int age_30_34_HealthyPerson_Num) {
		Age_30_34_HealthyPerson_Num = age_30_34_HealthyPerson_Num;
	}

	public int getAge_35_39_HealthyPerson_Num() {
		return Age_35_39_HealthyPerson_Num;
	}

	public void setAge_35_39_HealthyPerson_Num(int age_35_39_HealthyPerson_Num) {
		Age_35_39_HealthyPerson_Num = age_35_39_HealthyPerson_Num;
	}

	public int getAge_40_44_HealthyPerson_Num() {
		return Age_40_44_HealthyPerson_Num;
	}

	public void setAge_40_44_HealthyPerson_Num(int age_40_44_HealthyPerson_Num) {
		Age_40_44_HealthyPerson_Num = age_40_44_HealthyPerson_Num;
	}

	public int getAge_45_49_HealthyPerson_Num() {
		return Age_45_49_HealthyPerson_Num;
	}

	public void setAge_45_49_HealthyPerson_Num(int age_45_49_HealthyPerson_Num) {
		Age_45_49_HealthyPerson_Num = age_45_49_HealthyPerson_Num;
	}

	public int getAge_50_54_HealthyPerson_Num() {
		return Age_50_54_HealthyPerson_Num;
	}

	public void setAge_50_54_HealthyPerson_Num(int age_50_54_HealthyPerson_Num) {
		Age_50_54_HealthyPerson_Num = age_50_54_HealthyPerson_Num;
	}

	public int getAge_55_59_HealthyPerson_Num() {
		return Age_55_59_HealthyPerson_Num;
	}

	public void setAge_55_59_HealthyPerson_Num(int age_55_59_HealthyPerson_Num) {
		Age_55_59_HealthyPerson_Num = age_55_59_HealthyPerson_Num;
	}

	public int getAge_60_64_HealthyPerson_Num() {
		return Age_60_64_HealthyPerson_Num;
	}

	public void setAge_60_64_HealthyPerson_Num(int age_60_64_HealthyPerson_Num) {
		Age_60_64_HealthyPerson_Num = age_60_64_HealthyPerson_Num;
	}

	public int getAge_65_69_HealthyPerson_Num() {
		return Age_65_69_HealthyPerson_Num;
	}

	public void setAge_65_69_HealthyPerson_Num(int age_65_69_HealthyPerson_Num) {
		Age_65_69_HealthyPerson_Num = age_65_69_HealthyPerson_Num;
	}

	public int getAge_70_74_HealthyPerson_Num() {
		return Age_70_74_HealthyPerson_Num;
	}

	public void setAge_70_74_HealthyPerson_Num(int age_70_74_HealthyPerson_Num) {
		Age_70_74_HealthyPerson_Num = age_70_74_HealthyPerson_Num;
	}

	public int getAge_75_79_HealthyPerson_Num() {
		return Age_75_79_HealthyPerson_Num;
	}

	public void setAge_75_79_HealthyPerson_Num(int age_75_79_HealthyPerson_Num) {
		Age_75_79_HealthyPerson_Num = age_75_79_HealthyPerson_Num;
	}

	public int getAge_80_84_HealthyPerson_Num() {
		return Age_80_84_HealthyPerson_Num;
	}

	public void setAge_80_84_HealthyPerson_Num(int age_80_84_HealthyPerson_Num) {
		Age_80_84_HealthyPerson_Num = age_80_84_HealthyPerson_Num;
	}

	public int getAge_85_89_HealthyPerson_Num() {
		return Age_85_89_HealthyPerson_Num;
	}

	public void setAge_85_89_HealthyPerson_Num(int age_85_89_HealthyPerson_Num) {
		Age_85_89_HealthyPerson_Num = age_85_89_HealthyPerson_Num;
	}

	public int getAge_90_94_HealthyPerson_Num() {
		return Age_90_94_HealthyPerson_Num;
	}

	public void setAge_90_94_HealthyPerson_Num(int age_90_94_HealthyPerson_Num) {
		Age_90_94_HealthyPerson_Num = age_90_94_HealthyPerson_Num;
	}

	public int getAge_95_100_HealthyPerson_Num() {
		return Age_95_100_HealthyPerson_Num;
	}

	public void setAge_95_100_HealthyPerson_Num(int age_95_100_HealthyPerson_Num) {
		Age_95_100_HealthyPerson_Num = age_95_100_HealthyPerson_Num;
	}

	public int getAge_0_4_LatentPatient_Num() {
		return Age_0_4_LatentPatient_Num;
	}

	public void setAge_0_4_LatentPatient_Num(int age_0_4_LatentPatient_Num) {
		Age_0_4_LatentPatient_Num = age_0_4_LatentPatient_Num;
	}

	public int getAge_5_9_LatentPatient_Num() {
		return Age_5_9_LatentPatient_Num;
	}

	public void setAge_5_9_LatentPatient_Num(int age_5_9_LatentPatient_Num) {
		Age_5_9_LatentPatient_Num = age_5_9_LatentPatient_Num;
	}

	public int getAge_10_14_LatentPatient_Num() {
		return Age_10_14_LatentPatient_Num;
	}

	public void setAge_10_14_LatentPatient_Num(int age_10_14_LatentPatient_Num) {
		Age_10_14_LatentPatient_Num = age_10_14_LatentPatient_Num;
	}

	public int getAge_15_19_LatentPatient_Num() {
		return Age_15_19_LatentPatient_Num;
	}

	public void setAge_15_19_LatentPatient_Num(int age_15_19_LatentPatient_Num) {
		Age_15_19_LatentPatient_Num = age_15_19_LatentPatient_Num;
	}

	public int getAge_20_24_LatentPatient_Num() {
		return Age_20_24_LatentPatient_Num;
	}

	public void setAge_20_24_LatentPatient_Num(int age_20_24_LatentPatient_Num) {
		Age_20_24_LatentPatient_Num = age_20_24_LatentPatient_Num;
	}

	public int getAge_25_29_LatentPatient_Num() {
		return Age_25_29_LatentPatient_Num;
	}

	public void setAge_25_29_LatentPatient_Num(int age_25_29_LatentPatient_Num) {
		Age_25_29_LatentPatient_Num = age_25_29_LatentPatient_Num;
	}

	public int getAge_30_34_LatentPatient_Num() {
		return Age_30_34_LatentPatient_Num;
	}

	public void setAge_30_34_LatentPatient_Num(int age_30_34_LatentPatient_Num) {
		Age_30_34_LatentPatient_Num = age_30_34_LatentPatient_Num;
	}

	public int getAge_35_39_LatentPatient_Num() {
		return Age_35_39_LatentPatient_Num;
	}

	public void setAge_35_39_LatentPatient_Num(int age_35_39_LatentPatient_Num) {
		Age_35_39_LatentPatient_Num = age_35_39_LatentPatient_Num;
	}

	public int getAge_40_44_LatentPatient_Num() {
		return Age_40_44_LatentPatient_Num;
	}

	public void setAge_40_44_LatentPatient_Num(int age_40_44_LatentPatient_Num) {
		Age_40_44_LatentPatient_Num = age_40_44_LatentPatient_Num;
	}

	public int getAge_45_49_LatentPatient_Num() {
		return Age_45_49_LatentPatient_Num;
	}

	public void setAge_45_49_LatentPatient_Num(int age_45_49_LatentPatient_Num) {
		Age_45_49_LatentPatient_Num = age_45_49_LatentPatient_Num;
	}

	public int getAge_50_54_LatentPatient_Num() {
		return Age_50_54_LatentPatient_Num;
	}

	public void setAge_50_54_LatentPatient_Num(int age_50_54_LatentPatient_Num) {
		Age_50_54_LatentPatient_Num = age_50_54_LatentPatient_Num;
	}

	public int getAge_55_59_LatentPatient_Num() {
		return Age_55_59_LatentPatient_Num;
	}

	public void setAge_55_59_LatentPatient_Num(int age_55_59_LatentPatient_Num) {
		Age_55_59_LatentPatient_Num = age_55_59_LatentPatient_Num;
	}

	public int getAge_60_64_LatentPatient_Num() {
		return Age_60_64_LatentPatient_Num;
	}

	public void setAge_60_64_LatentPatient_Num(int age_60_64_LatentPatient_Num) {
		Age_60_64_LatentPatient_Num = age_60_64_LatentPatient_Num;
	}

	public int getAge_65_69_LatentPatient_Num() {
		return Age_65_69_LatentPatient_Num;
	}

	public void setAge_65_69_LatentPatient_Num(int age_65_69_LatentPatient_Num) {
		Age_65_69_LatentPatient_Num = age_65_69_LatentPatient_Num;
	}

	public int getAge_70_74_LatentPatient_Num() {
		return Age_70_74_LatentPatient_Num;
	}

	public void setAge_70_74_LatentPatient_Num(int age_70_74_LatentPatient_Num) {
		Age_70_74_LatentPatient_Num = age_70_74_LatentPatient_Num;
	}

	public int getAge_75_79_LatentPatient_Num() {
		return Age_75_79_LatentPatient_Num;
	}

	public void setAge_75_79_LatentPatient_Num(int age_75_79_LatentPatient_Num) {
		Age_75_79_LatentPatient_Num = age_75_79_LatentPatient_Num;
	}

	public int getAge_80_84_LatentPatient_Num() {
		return Age_80_84_LatentPatient_Num;
	}

	public void setAge_80_84_LatentPatient_Num(int age_80_84_LatentPatient_Num) {
		Age_80_84_LatentPatient_Num = age_80_84_LatentPatient_Num;
	}

	public int getAge_85_89_LatentPatient_Num() {
		return Age_85_89_LatentPatient_Num;
	}

	public void setAge_85_89_LatentPatient_Num(int age_85_89_LatentPatient_Num) {
		Age_85_89_LatentPatient_Num = age_85_89_LatentPatient_Num;
	}

	public int getAge_90_94_LatentPatient_Num() {
		return Age_90_94_LatentPatient_Num;
	}

	public void setAge_90_94_LatentPatient_Num(int age_90_94_LatentPatient_Num) {
		Age_90_94_LatentPatient_Num = age_90_94_LatentPatient_Num;
	}

	public int getAge_95_100_LatentPatient_Num() {
		return Age_95_100_LatentPatient_Num;
	}

	public void setAge_95_100_LatentPatient_Num(int age_95_100_LatentPatient_Num) {
		Age_95_100_LatentPatient_Num = age_95_100_LatentPatient_Num;
	}

	public int getAge_0_4_QuarantinedPatient_Num() {
		return Age_0_4_QuarantinedPatient_Num;
	}

	public void setAge_0_4_QuarantinedPatient_Num(int age_0_4_QuarantinedPatient_Num) {
		Age_0_4_QuarantinedPatient_Num = age_0_4_QuarantinedPatient_Num;
	}

	public int getAge_5_9_QuarantinedPatient_Num() {
		return Age_5_9_QuarantinedPatient_Num;
	}

	public void setAge_5_9_QuarantinedPatient_Num(int age_5_9_QuarantinedPatient_Num) {
		Age_5_9_QuarantinedPatient_Num = age_5_9_QuarantinedPatient_Num;
	}

	public int getAge_10_14_QuarantinedPatient_Num() {
		return Age_10_14_QuarantinedPatient_Num;
	}

	public void setAge_10_14_QuarantinedPatient_Num(int age_10_14_QuarantinedPatient_Num) {
		Age_10_14_QuarantinedPatient_Num = age_10_14_QuarantinedPatient_Num;
	}

	public int getAge_15_19_QuarantinedPatient_Num() {
		return Age_15_19_QuarantinedPatient_Num;
	}

	public void setAge_15_19_QuarantinedPatient_Num(int age_15_19_QuarantinedPatient_Num) {
		Age_15_19_QuarantinedPatient_Num = age_15_19_QuarantinedPatient_Num;
	}

	public int getAge_20_24_QuarantinedPatient_Num() {
		return Age_20_24_QuarantinedPatient_Num;
	}

	public void setAge_20_24_QuarantinedPatient_Num(int age_20_24_QuarantinedPatient_Num) {
		Age_20_24_QuarantinedPatient_Num = age_20_24_QuarantinedPatient_Num;
	}

	public int getAge_25_29_QuarantinedPatient_Num() {
		return Age_25_29_QuarantinedPatient_Num;
	}

	public void setAge_25_29_QuarantinedPatient_Num(int age_25_29_QuarantinedPatient_Num) {
		Age_25_29_QuarantinedPatient_Num = age_25_29_QuarantinedPatient_Num;
	}

	public int getAge_30_34_QuarantinedPatient_Num() {
		return Age_30_34_QuarantinedPatient_Num;
	}

	public void setAge_30_34_QuarantinedPatient_Num(int age_30_34_QuarantinedPatient_Num) {
		Age_30_34_QuarantinedPatient_Num = age_30_34_QuarantinedPatient_Num;
	}

	public int getAge_35_39_QuarantinedPatient_Num() {
		return Age_35_39_QuarantinedPatient_Num;
	}

	public void setAge_35_39_QuarantinedPatient_Num(int age_35_39_QuarantinedPatient_Num) {
		Age_35_39_QuarantinedPatient_Num = age_35_39_QuarantinedPatient_Num;
	}

	public int getAge_40_44_QuarantinedPatient_Num() {
		return Age_40_44_QuarantinedPatient_Num;
	}

	public void setAge_40_44_QuarantinedPatient_Num(int age_40_44_QuarantinedPatient_Num) {
		Age_40_44_QuarantinedPatient_Num = age_40_44_QuarantinedPatient_Num;
	}

	public int getAge_45_49_QuarantinedPatient_Num() {
		return Age_45_49_QuarantinedPatient_Num;
	}

	public void setAge_45_49_QuarantinedPatient_Num(int age_45_49_QuarantinedPatient_Num) {
		Age_45_49_QuarantinedPatient_Num = age_45_49_QuarantinedPatient_Num;
	}

	public int getAge_50_54_QuarantinedPatient_Num() {
		return Age_50_54_QuarantinedPatient_Num;
	}

	public void setAge_50_54_QuarantinedPatient_Num(int age_50_54_QuarantinedPatient_Num) {
		Age_50_54_QuarantinedPatient_Num = age_50_54_QuarantinedPatient_Num;
	}

	public int getAge_55_59_QuarantinedPatient_Num() {
		return Age_55_59_QuarantinedPatient_Num;
	}

	public void setAge_55_59_QuarantinedPatient_Num(int age_55_59_QuarantinedPatient_Num) {
		Age_55_59_QuarantinedPatient_Num = age_55_59_QuarantinedPatient_Num;
	}

	public int getAge_60_64_QuarantinedPatient_Num() {
		return Age_60_64_QuarantinedPatient_Num;
	}

	public void setAge_60_64_QuarantinedPatient_Num(int age_60_64_QuarantinedPatient_Num) {
		Age_60_64_QuarantinedPatient_Num = age_60_64_QuarantinedPatient_Num;
	}

	public int getAge_65_69_QuarantinedPatient_Num() {
		return Age_65_69_QuarantinedPatient_Num;
	}

	public void setAge_65_69_QuarantinedPatient_Num(int age_65_69_QuarantinedPatient_Num) {
		Age_65_69_QuarantinedPatient_Num = age_65_69_QuarantinedPatient_Num;
	}

	public int getAge_70_74_QuarantinedPatient_Num() {
		return Age_70_74_QuarantinedPatient_Num;
	}

	public void setAge_70_74_QuarantinedPatient_Num(int age_70_74_QuarantinedPatient_Num) {
		Age_70_74_QuarantinedPatient_Num = age_70_74_QuarantinedPatient_Num;
	}

	public int getAge_75_79_QuarantinedPatient_Num() {
		return Age_75_79_QuarantinedPatient_Num;
	}

	public void setAge_75_79_QuarantinedPatient_Num(int age_75_79_QuarantinedPatient_Num) {
		Age_75_79_QuarantinedPatient_Num = age_75_79_QuarantinedPatient_Num;
	}

	public int getAge_80_84_QuarantinedPatient_Num() {
		return Age_80_84_QuarantinedPatient_Num;
	}

	public void setAge_80_84_QuarantinedPatient_Num(int age_80_84_QuarantinedPatient_Num) {
		Age_80_84_QuarantinedPatient_Num = age_80_84_QuarantinedPatient_Num;
	}

	public int getAge_85_89_QuarantinedPatient_Num() {
		return Age_85_89_QuarantinedPatient_Num;
	}

	public void setAge_85_89_QuarantinedPatient_Num(int age_85_89_QuarantinedPatient_Num) {
		Age_85_89_QuarantinedPatient_Num = age_85_89_QuarantinedPatient_Num;
	}

	public int getAge_90_94_QuarantinedPatient_Num() {
		return Age_90_94_QuarantinedPatient_Num;
	}

	public void setAge_90_94_QuarantinedPatient_Num(int age_90_94_QuarantinedPatient_Num) {
		Age_90_94_QuarantinedPatient_Num = age_90_94_QuarantinedPatient_Num;
	}

	public int getAge_95_100_QuarantinedPatient_Num() {
		return Age_95_100_QuarantinedPatient_Num;
	}

	public void setAge_95_100_QuarantinedPatient_Num(int age_95_100_QuarantinedPatient_Num) {
		Age_95_100_QuarantinedPatient_Num = age_95_100_QuarantinedPatient_Num;
	}

	public int getStudent_Num() {
		return Student_Num;
	}

	public void setStudent_Num(int student_Num) {
		Student_Num = student_Num;
	}

	
}
