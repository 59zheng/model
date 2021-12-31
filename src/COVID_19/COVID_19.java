package COVID_19;

import java.awt.Color;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import repast.simphony.valueLayer.GridValueLayer;
import repast.simphony.valueLayer.ValueLayer;
import styles.ColorStyle;
import agent.person.DiagnosisPatient;
import agent.person.HealthyPerson;
import agent.person.LatentPatient;

import agent.person.QuarantinedPatient;
import agent.place.Airport;
import agent.place.BusStation;
import agent.place.BusinessDistrict;
import agent.place.City;
import agent.place.College;
import agent.place.Government;
import agent.place.Hospital;
import agent.place.RailwayStation;
import agent.utils.BackgroundPicture;
import agent.utils.Cell;
import agent.utils.Point;
import agent.utils.Raster;
import data.source.DataStorage;
import excel.Excel;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;

/**
 * COVID_19模拟
 * 
 * 健康者 潜伏者 发病者 隔离者 healthy person（绿色） Latent patient（黄色） Patient（红色） Quarantined
 * patient（白色） DeathPerson（黑色）
 * 
 * @author Beyond
 *
 */
public class COVID_19 implements ContextBuilder<Object> {
	public static Context<Object> context;
	public static Government government=new Government();
	
	public static ArrayList<HealthyPerson> healthyPersonTeam = new ArrayList<HealthyPerson>();// 健康者集合
	public static ArrayList<LatentPatient> latentPatientTeam = new ArrayList<LatentPatient>();// 潜伏者集合
	public static ArrayList<DiagnosisPatient> patientTeam = new ArrayList<DiagnosisPatient>();// 发病者集合
	public static ArrayList<QuarantinedPatient> quarantinedPatientTeam = new ArrayList<QuarantinedPatient>();// 隔离者集合
	public static ArrayList<Cell> cellTeam = new ArrayList<Cell>();// 太原市边界栅格集合（存储所有栅格）
	public static ArrayList<Cell> XDcellTeam = new ArrayList<Cell>();// 小店区栅格集合（存储所有栅格）
	public static ArrayList<Cell> YZcellTeam = new ArrayList<Cell>();// 迎泽区栅格集合（存储所有栅格）
	public static ArrayList<Cell> XHLcellTeam = new ArrayList<Cell>();// 杏花岭区栅格集合（存储所有栅格）
	public static ArrayList<Cell> WBLcellTeam = new ArrayList<Cell>();// 万柏林区栅格集合（存储所有栅格）
	public static ArrayList<Cell> JYcellTeam = new ArrayList<Cell>();// 晋源区栅格集合（存储所有栅格）
	public static ArrayList<Cell> JCPcellTeam = new ArrayList<Cell>();// 尖草坪区栅格集合（存储所有栅格）
	public static ArrayList<Cell> TYMcellTeam = new ArrayList<Cell>();// 太原市面栅格集合
	public static ArrayList<Cell> HospitalcellTeam = new ArrayList<Cell>();// 医院坐标栅格集合
	public static ArrayList<Cell> AirportcellTeam = new ArrayList<Cell>();// 机场坐标栅格集合
	public static ArrayList<Cell> RailwayStationcellTeam = new ArrayList<Cell>();// 火车站坐标栅格集合
	public static ArrayList<Cell> BusStationcellTeam = new ArrayList<Cell>();// 汽车站坐标栅格集合
	public static ArrayList<Cell> BusinessDistrictcellTeam = new ArrayList<Cell>();// 商圈坐标栅格集合
	public static ArrayList<Cell> CollegecellTeam = new ArrayList<Cell>();// 高校坐标栅格集合
			


	// 占用床位
	int BedNumTotal = 0;
	public COVID_19() {
		super();
	}

	public static ArrayList<Cell> getCellTeam() {
		return cellTeam;
	}

	public static void setCellTeam(ArrayList<Cell> cellTeam) {
		COVID_19.cellTeam = cellTeam;
	}

	public static ArrayList<Cell> getTYMcellTeam() {
		return TYMcellTeam;
	}

	public static void setTYMcellTeam(ArrayList<Cell> tYMcellTeam) {
		TYMcellTeam = tYMcellTeam;
	}

	/**
	 * 地图路径
	 */
	private static String MAP_URL = "./data/geographicdata/";
	
	private Raster taiYuan = new Raster();// 太原边界信息
	private Raster xiaoDian = new Raster();// 小店边界信息
	private Raster xingHuaLing = new Raster();// 杏花岭边界信息
	private Raster wangBaiLing = new Raster();// 万柏林边界信息
	private Raster yingZe = new Raster();// 迎泽边界信息
	private Raster jingYuan = new Raster();// 晋源边界信息
	private Raster jiangCaoPing = new Raster();// 尖草坪边界信息
	private Raster TaiYuan = new Raster();// 太原市面信息
	private Raster HospitalRaster = new Raster();// 医院信息
	private  Raster AirportRaster = new Raster();// 机场
	private  Raster RailwayStationRaster = new Raster();// 火车站
	private  Raster BusStationRaster = new Raster();// 汽车站
	private  Raster BusinessDistrictRaster = new Raster();// 商圈
	private  Raster CollegeRaster = new Raster();// 高校

	Excel excel;

	/**
	 * 值层数据 GIS将shp类型的地理数据转换成txt数据的步骤是：工具箱——转为栅格——要素转栅格——由栅格转出——栅格转Ascii码
	 * 以上的Raster是用来读取GIS所转换成的txt数据的，将至以java认识的二维数组形式存储。
	 * 之后是将以上的数组等数据导入GridValue中，为agent的存在提供空间环境基础
	 */

	private GridValueLayer border;// 太原市边界
	private GridValueLayer TYM;// 太原市面
	private GridValueLayer xdValueLayer; // 小店边界信息
	private GridValueLayer xhlValueLayer; // 杏花岭边界信息
	private GridValueLayer wbLValueLayer; // 万柏林边界信息
	private GridValueLayer yzValueLayer; // 迎泽边界信息
	private GridValueLayer jyValueLayer; // 晋源边界信息
	private GridValueLayer jcpValueLayer; // 尖草坪边界信息

	@Override
	public Context build(Context<Object> context) {

		// 设置环境ID
		context.setId("COVID_19");


		try {
			excel = new Excel();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 创建空间 指定城市边界不可出城市
		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace("space", context,
				new RandomCartesianAdder<Object>(), new repast.simphony.space.continuous.WrapAroundBorders(),
				SystemParameter.CITY_WIDTH, SystemParameter.CITY_HEIGHT);

		// 栅格数据
		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
		Grid<Object> grid = gridFactory.createGrid("grid", context,
				new GridBuilderParameters<Object>(new WrapAroundBorders(), new SimpleGridAdder<Object>(), true,
						SystemParameter.CITY_WIDTH, SystemParameter.CITY_HEIGHT));
	
		// 创建值层
		border = new GridValueLayer("border", false, SystemParameter.CITY_WIDTH, SystemParameter.CITY_HEIGHT);
		TYM = new GridValueLayer("TYM", false, SystemParameter.CITY_WIDTH, SystemParameter.CITY_HEIGHT);
		xdValueLayer = new GridValueLayer("xdValueLayer", false, SystemParameter.CITY_WIDTH,
				SystemParameter.CITY_HEIGHT);
		xhlValueLayer = new GridValueLayer("xhlValueLayer", false, SystemParameter.CITY_WIDTH,
				SystemParameter.CITY_HEIGHT);
		wbLValueLayer = new GridValueLayer("wbLValueLayer", false, SystemParameter.CITY_WIDTH,
				SystemParameter.CITY_HEIGHT);
		yzValueLayer = new GridValueLayer("yzValueLayer", false, SystemParameter.CITY_WIDTH,
				SystemParameter.CITY_HEIGHT);
		jyValueLayer = new GridValueLayer("jyValueLayer", false, SystemParameter.CITY_WIDTH,
				SystemParameter.CITY_HEIGHT);
		jcpValueLayer = new GridValueLayer("jcpValueLayer", false, SystemParameter.CITY_WIDTH,
				SystemParameter.CITY_HEIGHT);
		// 在环境中添加值层
		context.addValueLayer(border);
		context.addValueLayer(TYM);
		context.addValueLayer(xdValueLayer);
		context.addValueLayer(xhlValueLayer);
		context.addValueLayer(wbLValueLayer);
		context.addValueLayer(yzValueLayer);
		context.addValueLayer(jyValueLayer);
		context.addValueLayer(jcpValueLayer);

		// 获取Repast界面参数

		Parameters params = RunEnvironment.getInstance().getParameters();
		double temperature = (double) params.getValue("temperature");
		SystemParameter.temperature = temperature;
		System.out.println("SystemParameter.temperature = " + SystemParameter.temperature);

		// 读取地图数据
		taiYuan.ReadArcgisRasterData(MAP_URL + "太原线.txt");
		xiaoDian.ReadArcgisRasterData(MAP_URL + "小店区.txt");
		xingHuaLing.ReadArcgisRasterData(MAP_URL + "杏花岭区.txt");
		yingZe.ReadArcgisRasterData(MAP_URL + "迎泽区.txt");
		jingYuan.ReadArcgisRasterData(MAP_URL + "晋源区.txt");

		wangBaiLing.ReadArcgisRasterData(MAP_URL + "万柏林区.txt");
		jiangCaoPing.ReadArcgisRasterData(MAP_URL + "尖草坪区.txt");
		TaiYuan.ReadArcgisRasterData(MAP_URL + "太原面.txt");
		HospitalRaster.ReadArcgisRasterData(MAP_URL + "太原医院.txt");
		AirportRaster.ReadArcgisRasterData(MAP_URL + "太原机场.txt");// 机场
		RailwayStationRaster.ReadArcgisRasterData(MAP_URL + "太原火车站.txt");// 火车站
		BusStationRaster.ReadArcgisRasterData(MAP_URL + "太原汽车站.txt");// 汽车站
		BusinessDistrictRaster.ReadArcgisRasterData(MAP_URL + "太原商圈.txt");// 商圈
		CollegeRaster.ReadArcgisRasterData(MAP_URL + "太原高校.txt");// 高校
	// 背景图
		/*BackgroundPicture backgroundPicture = new BackgroundPicture(SystemParameter.CITY_WIDTH / 2,
				SystemParameter.CITY_HEIGHT / 2, context, space, grid);
		context.add(backgroundPicture);
		grid.moveTo(backgroundPicture, backgroundPicture.getX(), backgroundPicture.getY());*/

		// 地图显示
		for (int i = 0; i < SystemParameter.CITY_WIDTH; i++) {
			for (int j = 0; j < SystemParameter.CITY_HEIGHT; j++) {

				// 获取数据，获取方式为单个获取（存储为后缀为Temp的局部变量中）
				

					 double taiYuanTemp = taiYuan.getData(i, j);
				        double xiaoDianTemp = xiaoDian.getData(i, j);
				        double xingHuaLingTemp = xingHuaLing.getData(i, j);
				        double yingZeTemp = yingZe.getData(i, j);
				        double jingYuanTemp = jingYuan.getData(i, j);
				        double wangBaiLingTemp = wangBaiLing.getData(i, j);
				        double jiangCaoPingTemp = jiangCaoPing.getData(i, j);
				        double TaiYuanTemp = TaiYuan.getData(i, j);
				        double HospitalTemp = HospitalRaster.getData(i, j);
				        double  AirportTemp=AirportRaster.getData(i, j);// 机场
				        double  RailwayStationTemp=RailwayStationRaster.getData(i, j);// 火车站
				        double  BusStationTemp=BusStationRaster.getData(i, j);// 汽车站
				        double  BusinessDistrictTemp=BusinessDistrictRaster.getData(i, j);// 商圈
				        double  CollegeTemp=CollegeRaster.getData(i, j);// 高校
				

					// 设置数据（设置在值层中）
					border.set(taiYuanTemp, i, j);
					TYM.set(TaiYuanTemp, i, j);
					xdValueLayer.set(xiaoDianTemp, i, j);
					xhlValueLayer.set(xingHuaLingTemp, i, j);
					wbLValueLayer.set(wangBaiLingTemp, i, j);
					yzValueLayer.set(yingZeTemp, i, j);
					jyValueLayer.set(jingYuanTemp, i, j);
					jcpValueLayer.set(jiangCaoPingTemp, i, j);
					if (SystemParameter.worldTime<=1) {
					addMapCell(context, space, grid, i, j,taiYuanTemp, xiaoDianTemp, xingHuaLingTemp, yingZeTemp,
							jingYuanTemp, wangBaiLingTemp, jiangCaoPingTemp, TaiYuanTemp,HospitalTemp,AirportTemp,RailwayStationTemp,BusStationTemp,BusinessDistrictTemp,CollegeTemp);
					}
			

			}
		}
		/**
		 * 添加数据记录器
		 */
		DataStorage dataStorage = DataStorage.getInstance();
		context.add(dataStorage);

		// 页面数据初始化控件
//		初始化政府各项防空等级各种等级
		initPreventionControl();
		/**
		 * 添加城市
		 */
		City city = City.getInstance();
		// 太原边界显示颜色
		
		  List<Cell> object1 = cellTeam.stream().collect(Collectors.toList()); for
		  (Cell object : object1) { 
			  Point point = new Point(object.getX(),object.getY()); context.add(point); grid.moveTo(point, point.getX(),
		  point.getY());
		  
		 }
		 


	if (SystemParameter.worldTime<=1) {
		xiaoDian(context, space, grid, city);

		yingZe(context, space, grid, city);

		xingHuaLing(context, space, grid, city);

		jinYuan(context, space, grid, city);

		wanBaiLin(context, space, grid, city);

		jianCaoPing(context, space, grid, city);
		/**
		 * 添加医院、火车站、机场、商圈、以及高校
		 */
		for (Cell ite : HospitalcellTeam) {
			Hospital hospital = new Hospital(ite.getX(), ite.getY(), 234);
			hospital.inint();
			context.add(hospital);
			BedNumTotal = BedNumTotal + hospital.getBedNum();
			grid.moveTo(hospital, ite.getX(), ite.getY());
		}
		for (Cell ite : AirportcellTeam) {
			Airport airport = new Airport(ite.getX(), ite.getY());
			context.add(airport);
			grid.moveTo(airport, ite.getX(), ite.getY());
			//System.out.println("添加机场"+ ite.getX()+"y"+ ite.getX());
		}
		for (Cell ite : RailwayStationcellTeam) {
			RailwayStation RailwayStation = new RailwayStation(ite.getX(), ite.getY());
			context.add(RailwayStation);
			grid.moveTo(RailwayStation, ite.getX(), ite.getY());
		}
		for (Cell ite : BusStationcellTeam) {
			BusStation BusStation = new BusStation(ite.getX(), ite.getY());
			context.add(BusStation);
			grid.moveTo(BusStation, ite.getX(), ite.getY());
		}
		for (Cell ite : BusinessDistrictcellTeam) {
			BusinessDistrict BusinessDistrict = new BusinessDistrict(ite.getX(), ite.getY());
			context.add(BusinessDistrict);
			grid.moveTo(BusinessDistrict, ite.getX(), ite.getY());
		}
		for (Cell ite : CollegecellTeam) {
			College College = new College(space, grid,ite.getX(), ite.getY());
			context.add(College);
			grid.moveTo(College, ite.getX(), ite.getY());
		}
		
		
		/**
		 * 更新床位数据
		 */
		dataStorage.setUnuseBedNum(BedNumTotal);
	}

		/*
		 * 健康者，潜伏者，发病者，隔离者往地图栅格上边投放 就是地图显示的方法 显示正方形原因你去new 出来的类里边去看比如new
		 * QuarantinedPatient（）这个方法里边{对市民的初始位置进行N(x,100)的正态分布随机}这边就是处理位置的
		 */

		for (Object obj : context) {
			// 健康者
			if (obj.getClass() == HealthyPerson.class) {
				HealthyPerson healthyPerson = (HealthyPerson) obj;
				grid.moveTo(obj, healthyPerson.getX(), healthyPerson.getY());
			}
			// 潜伏者
			if (obj.getClass() == LatentPatient.class) {
				LatentPatient latentPatient = (LatentPatient) obj;
				grid.moveTo(obj, latentPatient.getX(), latentPatient.getY());
			}
			// 发病者
			if (obj.getClass() == DiagnosisPatient.class) {
				DiagnosisPatient patient = (DiagnosisPatient) obj;
				grid.moveTo(obj, patient.getX(), patient.getY());
			}
			// 隔离者
			if (obj.getClass() == QuarantinedPatient.class) {
				QuarantinedPatient quarantinedPatient = (QuarantinedPatient) obj;
				grid.moveTo(obj, quarantinedPatient.getX(), quarantinedPatient.getY());
			}

		}
		
		context.add(this);// 为了数据更新
		return context;
	}

	private void addMapCell(Context<Object> context, ContinuousSpace<Object> space, Grid<Object> grid, int i, int j,double taiYuanTemp,
			double xiaoDianTemp, double xingHuaLingTemp, double yingZeTemp, double jingYuanTemp,
			double wangBaiLingTemp, double jiangCaoPingTemp, double TaiYuanTemp,double HospitalTemp,double  AirportTemp,double  RailwayStationTemp,double  BusStationTemp,double  BusinessDistrictTemp,double  CollegeTemp  ) {
		// 添加Agent，borderTemp != -9999的点为无效数据，不考虑
		// 都在太原边界内
		Cell cell = new Cell(i, j, context, space, grid);
		// 设置太远边界
		if (taiYuanTemp != -9999) {
			// 设置栅格属性
			cell.setTaiYaun(taiYuanTemp);

			cellTeam.add(cell);// 将cell添加至cellTeam，之后更新地图
			context.add(cell);
			grid.moveTo(cell, i, j);

		}
		// 设置太原市面
		if (TaiYuanTemp != -9999) {
			// 设置栅格属性
			cell.setTaiYaunM(TaiYuanTemp);
			TYMcellTeam.add(cell);// 将cell添加至cellTeam，之后更新地图
			context.add(cell);
			grid.moveTo(cell, i, j);
		}
		// 设置各地区面
		
		if (xiaoDianTemp != -9999) {
			cell.setIsXiaoDian(1);
			XDcellTeam.add(cell);// 将cell添加至cellTeam，之后更新地图
			context.add(cell);
			grid.moveTo(cell, i, j);
		}

		if (xingHuaLingTemp != -9999) {
			cell.setIsXingHuaLing(1);
			XHLcellTeam.add(cell);// 将cell添加至cellTeam，之后更新地图
			context.add(cell);
			grid.moveTo(cell, i, j);
		}

		if (yingZeTemp != -9999) {
			cell.setIsYingZe(1);
			YZcellTeam.add(cell);// 将cell添加至cellTeam，之后更新地图
			context.add(cell);
			grid.moveTo(cell, i, j);
		}

		if (jingYuanTemp != -9999) {
			cell.setIsJiangCaoPing(1);
			JYcellTeam.add(cell);// 将cell添加至cellTeam，之后更新地图
			context.add(cell);
			grid.moveTo(cell, i, j);
		}

		if (wangBaiLingTemp != -9999) {
			cell.setIsWangBaiLing(1);
			WBLcellTeam.add(cell);// 将cell添加至cellTeam，之后更新地图
			context.add(cell);
			grid.moveTo(cell, i, j);
		}
		if (jiangCaoPingTemp != -9999) {
			cell.setIsWangBaiLing(1);
			JCPcellTeam.add(cell);// 将cell添加至cellTeam，之后更新地图
			context.add(cell);
			grid.moveTo(cell, i, j);
		}
		
if (HospitalTemp != -9999 && HospitalTemp != 0) {
			
			cell.setIsHospital(1);
			HospitalcellTeam.add(cell);// 将cell添加至cellTeam，之后更新地图
			context.add(cell);
			 grid.moveTo(cell, i, j);

		}
if (AirportTemp != -9999 && AirportTemp != 0) {
			
			cell.setIsAirport(1);
			AirportcellTeam.add(cell);// 将cell添加至cellTeam，之后更新地图
			context.add(cell);
			 grid.moveTo(cell, i, j);

		}

		if (RailwayStationTemp != -9999 && RailwayStationTemp != 0) {

			cell.setIsRailwayStation(1);
			RailwayStationcellTeam.add(cell);// 将cell添加至cellTeam，之后更新地图
			context.add(cell);
			 grid.moveTo(cell, i, j);

		}if (BusStationTemp != -9999 && BusStationTemp != 0) {

			cell.setIsBusStation(1);
			BusStationcellTeam.add(cell);// 将cell添加至cellTeam，之后更新地图
			context.add(cell);
			 grid.moveTo(cell, i, j);

		}if (CollegeTemp != -9999 && CollegeTemp != 0) {

			cell.setIsCollege(1);
			CollegecellTeam.add(cell);// 将cell添加至cellTeam，之后更新地图
			context.add(cell);
			 grid.moveTo(cell, i, j);

		}if (BusinessDistrictTemp != -9999 && BusinessDistrictTemp != 0) {

			cell.setIsBusinessDistrict(1);
			BusinessDistrictcellTeam.add(cell);// 将cell添加至cellTeam，之后更新地图
			context.add(cell);
			 grid.moveTo(cell, i, j);

		}
		

	}

	public void controlLevel(int confirmed_Num) {
		/**
		 * 根据感染人数设置当前防控等级
		 */
		
	
		if (confirmed_Num >= 50) {
			government.setControlLevel(3);// 防控等级为高风险
			government.setIsCheckWearMask(1);// 各大商场严查并强制要求佩戴口罩
			government.setIsTravelTestrictions(1);// 限制居民出行
			government.setIsNAT(1);
			SystemParameter.controlLevel=1;
			DataStorage.dataStorage.setControl_Level(1);
		} else if (confirmed_Num > 0) {
			government.setControlLevel(2);// 防控等级为中风险
			government.setIsCheckWearMask(1);// 各大商场严查并强制要求佩戴口罩
			government.setIsTravelTestrictions(1);// 限制居民出行
			DataStorage.dataStorage.setControl_Level(2);
			SystemParameter.controlLevel=2;
		} else if (confirmed_Num == 0) {
			government.setControlLevel(1);
			DataStorage.dataStorage.setControl_Level(3);
			SystemParameter.controlLevel=3;
		}else {
			government.setControlLevel(0);
			DataStorage.dataStorage.setControl_Level(4);
			SystemParameter.controlLevel=4;
		}
	}

	public void jianCaoPing(Context<Object> context, ContinuousSpace<Object> space, Grid<Object> grid, City city) {
		/*
		 *
		 * JCP区添加健康者 随机将人口分配在栅格中
		 */
		// 总人数
		int JCP_PERSON_SIZE = SystemParameter.JCP_PERSON_SIZE;
		// 总栅格数
		int jcpcellsize = JCPcellTeam.size();
		List<Cell> jcpcell = JCPcellTeam.stream().collect(Collectors.toList());
		Collections.shuffle(jcpcell);
		for (Cell object : jcpcell) {
			if (JCP_PERSON_SIZE>0) {
			HealthyPerson healthyPerson = new HealthyPerson(space, grid, city, object.getX(), object.getY(),
					SystemFunction.createRandomAge(), SystemFunction.createRandomTemper(),"JCP",false);
			healthyPersonTeam.add(healthyPerson);
			healthyPerson.init();
			context.add(healthyPerson);
					JCP_PERSON_SIZE--;
			}
		}
	}

	public void wanBaiLin(Context<Object> context, ContinuousSpace<Object> space, Grid<Object> grid, City city) {
		/*
		 *
		 * wbl区添加健康者 随机将人口分配在栅格中
		 */
		// 总人数
		int WBL_PERSON_SIZE = SystemParameter.WBL_PERSON_SIZE;
		// 总栅格数
		int wblcellsize = WBLcellTeam.size();
		List<Cell> wblcell = WBLcellTeam.stream().collect(Collectors.toList());
		Collections.shuffle(wblcell);
		for (Cell object : wblcell) {
			if (WBL_PERSON_SIZE>0) {
			HealthyPerson healthyPerson = new HealthyPerson(space, grid, city, object.getX(), object.getY(),
					SystemFunction.createRandomAge(), SystemFunction.createRandomTemper(),"wbl",false);
			healthyPersonTeam.add(healthyPerson);
			healthyPerson.init();
			context.add(healthyPerson);
					WBL_PERSON_SIZE--;
			}
		}
	}

	public void jinYuan(Context<Object> context, ContinuousSpace<Object> space, Grid<Object> grid, City city) {
		/*
		 *
		 * jy区添加健康者 随机将人口分配在栅格中
		 */
		// 总人数
		int JY_PERSON_SIZE = SystemParameter.JY_PERSON_SIZE;
		// 总栅格数
		int jycellsize = JYcellTeam.size();
		List<Cell> jycell = JYcellTeam.stream().collect(Collectors.toList());
		Collections.shuffle(jycell);
		for (Cell object : jycell) {		
			if (JY_PERSON_SIZE > 0) {
				HealthyPerson healthyPerson = new HealthyPerson(space, grid, city, object.getX(), object.getY(),
						SystemFunction.createRandomAge(), SystemFunction.createRandomTemper(),"JY",false);
				healthyPersonTeam.add(healthyPerson);
				healthyPerson.init();
				context.add(healthyPerson);
					JY_PERSON_SIZE--;
			}
		}
	}

	public void xingHuaLing(Context<Object> context, ContinuousSpace<Object> space, Grid<Object> grid, City city) {
		/*
		 *
		 * XHL区添加健康者 随机将人口分配在栅格中
		 */
		// 总人数
		int XHL_PERSON_SIZE = SystemParameter.XHL_PERSON_SIZE;
		// 总栅格数
		int xhlcellsize = YZcellTeam.size();
		List<Cell> xhlcell = XHLcellTeam.stream().collect(Collectors.toList());
		Collections.shuffle(xhlcell);
		for (Cell object : xhlcell) {

			if (XHL_PERSON_SIZE > 0) {
				HealthyPerson healthyPerson = new HealthyPerson(space, grid, city, object.getX(), object.getY(),
						SystemFunction.createRandomAge(), SystemFunction.createRandomTemper(),"XHL",false);
				healthyPersonTeam.add(healthyPerson);
				healthyPerson.init();
				context.add(healthyPerson);
					XHL_PERSON_SIZE--;
		}
		}
	}

	public void yingZe(Context<Object> context, ContinuousSpace<Object> space, Grid<Object> grid, City city) {
		/*
		 *
		 * 迎泽区添加健康者 随机将人口分配在栅格中
		 */
		// 总人数
		int YZ_PERSON_SIZE = SystemParameter.YZ_PERSON_SIZE;
		// 总栅格数
		int yzcellsize = YZcellTeam.size();
		List<Cell> yzcell = YZcellTeam.stream().collect(Collectors.toList());
		Collections.shuffle(yzcell);
		for (Cell object : yzcell) {
			if (YZ_PERSON_SIZE > 0) {
				HealthyPerson healthyPerson = new HealthyPerson(space, grid, city, object.getX(), object.getY(),
						SystemFunction.createRandomAge(), SystemFunction.createRandomTemper(),"YZ",false);
				healthyPersonTeam.add(healthyPerson);
				healthyPerson.init();
				context.add(healthyPerson);
					YZ_PERSON_SIZE--;
			}
		}
	}

	
	/*
	 *
	 * 小店区添加健康者 随机将人口分配在栅格中
	 */
	public void xiaoDian(Context<Object> context, ContinuousSpace<Object> space, Grid<Object> grid, City city) {
		/**
		 * 添加潜伏者
		 */
		int latentPatientCount = SystemParameter.LatentPatient_COUNT;
		/**
		 * 添加发病者（即初始感染者）
		 */
		int patientCount = SystemParameter.ORIGINAL_COUNT;
		// 总人数
		int XD_PERSON_SIZE = SystemParameter.XD_PERSON_SIZE;
		// 总栅格数
		int cellsize = XDcellTeam.size();
		List<Cell> objects = XDcellTeam.stream().collect(Collectors.toList());
		Collections.shuffle(objects);
		for (Cell object : objects) {
			if (XD_PERSON_SIZE > 0) {
					HealthyPerson healthyPerson = new HealthyPerson(space, grid, city, object.getX(), object.getY(),
							SystemFunction.createRandomAge(), SystemFunction.createRandomTemper(),"XD",false);
					healthyPerson.init();
					healthyPersonTeam.add(healthyPerson);
					context.add(healthyPerson);
					XD_PERSON_SIZE--;
					
			}
				 
			// 添加潜伏着
			if (latentPatientCount > 0) {

				LatentPatient latentPatient = new LatentPatient(space, grid, city, object.getX(), object.getY(),
						SystemFunction.createRandomAge(), SystemParameter.worldTime,
						SystemFunction.createRandomTemper(),"XD");
				latentPatientTeam.add(latentPatient);
				latentPatient.init();
				context.add(latentPatient);
				latentPatientCount--;
			}

		}
	}
	
	/**
	 *是否根据政府防控等级影响就诊时间、就诊率、隔离比例、免疫比例、自我防护率
	 * 
	
	 */
	
	private void initPreventionControl() {
 int controlLver	=	SystemParameter.controlLevel;
 System.out.println("当前防控等级"+controlLver);
 switch (controlLver) {
 //
case 1:
	SystemParameter.clinicalTime=SystemParameter.controlLevel1[0];
	SystemParameter.outPatientRate=SystemParameter.controlLevel1[1];
	SystemParameter.isolationRatio=SystemParameter.controlLevel1[2];
	SystemParameter.VACCINATION_RATE=SystemParameter.controlLevel1[3];
	SystemParameter.WEAR_WASK_RATE=SystemParameter.controlLevel1[4];
	SystemParameter.u=SystemParameter.controlLevel1[5];
	break;
case 2:
	SystemParameter.clinicalTime=SystemParameter.controlLevel2[0];
	SystemParameter.outPatientRate=SystemParameter.controlLevel2[1];
	SystemParameter.isolationRatio=SystemParameter.controlLevel2[2];
	SystemParameter.VACCINATION_RATE=SystemParameter.controlLevel2[3];
	SystemParameter.WEAR_WASK_RATE=SystemParameter.controlLevel2[4];
	SystemParameter.u=SystemParameter.controlLevel2[5];
	break;
case 3:
	SystemParameter.clinicalTime=SystemParameter.controlLevel3[0];
	SystemParameter.outPatientRate=SystemParameter.controlLevel3[1];
	SystemParameter.isolationRatio=SystemParameter.controlLevel3[2];
	SystemParameter.VACCINATION_RATE=SystemParameter.controlLevel3[3];
	SystemParameter.WEAR_WASK_RATE=SystemParameter.controlLevel3[4];
	SystemParameter.u=SystemParameter.controlLevel3[5];
	break;
case 4:
	SystemParameter.clinicalTime=SystemParameter.controlLevel4[0];
	SystemParameter.outPatientRate=SystemParameter.controlLevel4[1];
	SystemParameter.isolationRatio=SystemParameter.controlLevel4[2];
	SystemParameter.VACCINATION_RATE=SystemParameter.controlLevel4[3];
	SystemParameter.WEAR_WASK_RATE=SystemParameter.controlLevel4[4];
	SystemParameter.u=SystemParameter.controlLevel4[5];
	break;

default:
	break;
	
}

 System.out.println("就诊时间"+SystemParameter.clinicalTime);
 System.out.println("就诊率"+SystemParameter.outPatientRate);
 System.out.println("隔离比例"+SystemParameter.isolationRatio);
 System.out.println("接种疫苗的概率"+SystemParameter.VACCINATION_RATE);
 System.out.println("口罩比例"+SystemParameter.WEAR_WASK_RATE);
 System.out.println("流动意愿"+SystemParameter.u);

	}

	/**
	 * 更新数据
	 * 
	 * @throws IOException
	 * @throws BiffException
	 * @throws WriteException
	 * @throws InterruptedException
	 */
	@ScheduledMethod(start = 0, interval = 3, priority = 0)
	public void step() throws WriteException, BiffException, IOException, InterruptedException {
		/**
		 * 添加数据记录器
		 */
		//System.out.println("m"+tym+"xd"+xdc+"jy"+jyc+"wbl"+wblc+"jcp"+jcpc+"yz"+yzc+"xhl"+xhlc);
		DataStorage dataStorage = DataStorage.getInstance();
		
		int confirmed_Num = dataStorage.getCONFIRMED_Num();
//		controlLevel(confirmed_Num);
		//无措施
		//controlLevel(0);
		
		SystemParameter.worldTime++;// 时间+1
		System.out.println("世界时间为：" + SystemParameter.worldTime);
		System.out.println("数据更新");

		/**
		 * 温度影响病毒活性
		 * 4度，病毒14天后仍保持稳定；22度，病毒能够保持稳定一整天，7天后病毒感染性降低至0.1%；37度，病毒仅能保持稳定3-4小时，
		 * 传染性在1天后就降至0.1%
		 */
		if (SystemParameter.temperature == 4) {
			System.out.println("4度，病毒14天后仍保持稳定");
		}
		if (SystemParameter.temperature == 22) {
			System.out.println("22度，病毒能够保持稳定一整天，7天后病毒感染性降低至0.1%");
			if (SystemParameter.worldTime > 0 && SystemParameter.worldTime < 8) {

			}
			if (SystemParameter.worldTime == 8) {

			}
		}
		if (SystemParameter.temperature == 37) {
			System.out.println("37度，病毒仅能保持稳定3-4小时，传染性在1天后就降至0.1%");
			if (SystemParameter.worldTime == 1) {

			}
			if (SystemParameter.worldTime != 1) {

			}
		}

		
		System.out.println("世界时间为：" + SystemParameter.worldTime + "现在未使用的床位数=" + dataStorage.getUnuseBedNum());
		System.out.println("世界时间为：" + SystemParameter.worldTime + "现在已使用的床位数=" + dataStorage.getUseBedNum());
		System.out.println("世界时间为：" + SystemParameter.worldTime + "现在健康人数=" + dataStorage.getNORMAL_Num());
		System.out.println("世界时间为：" + SystemParameter.worldTime + "现在确诊数=" + dataStorage.getCONFIRMED_Num());
		System.out.println("世界时间为：" + SystemParameter.worldTime + "现在隔离数=" + dataStorage.getFREEZE_Num());
		System.out.println("世界时间为：" + SystemParameter.worldTime + "累计密接数=" + dataStorage.getCONTACT_Num());
		System.out.println("世界时间为：" + SystemParameter.worldTime + "现在潜伏数=" + dataStorage.getSHADOW_Num());
		System.out.println("世界时间为：" + SystemParameter.worldTime + "现在死亡人数=" + dataStorage.getDEATH_Num());
		System.out.println("世界时间为：" + SystemParameter.worldTime + "现在治愈人数=" + dataStorage.getCURED_Num());
		 //判断当前政府防控等级
		System.out.println("世界时间为：" + SystemParameter.worldTime + "当前政府防控等级" + dataStorage.getControl_Level());

		excel.step(dataStorage);
		//dataStorage.step();
	}

}
