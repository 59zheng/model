package excel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import data.source.DataStorage;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 
 * Excel表操作
 * 
 * @author LKD
 */
public class Excel {

	File f;

	public Excel() throws Exception {
		f = new File("output//data.xlsx");
		this.createUserExcelFile(f);
	}

	private int row = 0;// 初始的行
	private int column = 0;// 初始的列
	private WritableSheet sheet = null;//工作表
	private WritableWorkbook book = null;//工作薄
	/** 字体格式 */
	private static WritableCellFormat fontFormat = null;

	/** 初始化数据 */
	private static boolean init = false;

	// book必须想办法解决
	public void step(DataStorage dataStorage) throws WriteException, IOException, BiffException {
		Workbook wb = Workbook.getWorkbook(f);
		WritableWorkbook wwb = Workbook.createWorkbook(f, wb);
		WritableSheet ws = wwb.getSheet(0);

		// 设置列的宽度
		ws.setColumnView(0, 40);

		column++;
		row = 0;
		// 下一列
		ws.addCell(new Label(column, row++, Integer.toString(column), fontFormat));
		
	
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getNORMAL_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getSHADOW_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getCONFIRMED_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getFREEZE_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getCURED_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getDEATH_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getUseBedNum()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getUnuseBedNum()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getCONTACT_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getMILD_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getSEVERE_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getVACCINATION_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getWEAR_MASK_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getVACCINATION_MASK_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getHospital_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getStudent_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getControl_Level()), fontFormat));
		
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_0_4_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_5_9_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_10_14_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_15_19_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_20_24_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_25_29_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_30_34_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_35_39_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_40_44_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_45_49_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_50_54_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_55_59_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_60_64_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_65_69_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_70_74_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_75_79_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_80_84_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_85_89_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_90_94_Patient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_95_100_Patient_Num()), fontFormat));

		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_0_4_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_5_9_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_10_14_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_15_19_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_20_24_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_25_29_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_30_34_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_35_39_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_40_44_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_45_49_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_50_54_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_55_59_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_60_64_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_65_69_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_70_74_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_75_79_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_80_84_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_85_89_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_90_94_CloseContact_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_95_100_CloseContact_Num()), fontFormat));

		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_0_4_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_5_9_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_10_14_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_15_19_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_20_24_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_25_29_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_30_34_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_35_39_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_40_44_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_45_49_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_50_54_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_55_59_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_60_64_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_65_69_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_70_74_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_75_79_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_80_84_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_85_89_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_90_94_HealthyPerson_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_95_100_HealthyPerson_Num()), fontFormat));

		
		
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_0_4_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_5_9_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_10_14_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_15_19_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_20_24_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_25_29_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_30_34_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_35_39_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_40_44_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_45_49_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_50_54_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_55_59_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_60_64_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_65_69_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_70_74_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_75_79_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_80_84_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_85_89_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_90_94_QuarantinedPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_95_100_QuarantinedPatient_Num()), fontFormat));

		
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_0_4_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_5_9_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_10_14_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_15_19_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_20_24_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_25_29_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_30_34_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_35_39_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_40_44_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_45_49_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_50_54_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_55_59_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_60_64_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_65_69_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_70_74_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_75_79_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_80_84_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_85_89_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_90_94_LatentPatient_Num()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAge_95_100_LatentPatient_Num()), fontFormat));
		
		
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getJanuaryNum()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getFebruaryNum()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getMarchNum()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAprilNum()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getMayNum()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getJuneNum()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getJulyNum()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getAugustNum()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getSeptemberNum()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getOctoberNum()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getNovemberNum()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getDecemberNum()), fontFormat));
		
		
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getXDCloseContact()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getXDCollegeStudent()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getXDDiagnosis()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getXDHealthy()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getXDLatent()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getXDQuarantined()), fontFormat));
		
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getXHLCloseContact()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getXHLCollegeStudent()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getXHLDiagnosis()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getXHLHealthy()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getXHLLatent()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getXHLQuarantined()), fontFormat));
		
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getYZCloseContact()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getYZCollegeStudent()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getYZDiagnosis()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getYZHealthy()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getYZLatent()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getYZQuarantined()), fontFormat));
		
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getJCPCloseContact()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getJCPCollegeStudent()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getJCPDiagnosis()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getJCPHealthy()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getJCPLatent()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getJCPQuarantined()), fontFormat));
		
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getJyCloseContact()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getJyCollegeStudent()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getJyDiagnosis()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getJyHealthy()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getJyLatent()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getJyQuarantined()), fontFormat));
		
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getWblCloseContact()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getWblCollegeStudent()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getWblDiagnosis()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getWblHealthy()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getWblLatent()), fontFormat));
		ws.addCell(new Label(column, row++, Integer.toString(dataStorage.getWblQuarantined()), fontFormat));


		wwb.write();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(f.format(new Date()) + " 数据输入dataStorage表格！");
		if (wb != null)
			wwb.close();
		wb.close();
	}

	/**
	 * 初始化各单元格格式
	 * 
	 * @throws WriteException
	 *             初始化失败
	 */
	private void init() throws WriteException {
		WritableFont font1;
		// Arial字体，11号，粗体，单元格黄色，田字边框，居中对齐
		font1 = new WritableFont(WritableFont.TAHOMA, 11, WritableFont.BOLD, false);
		fontFormat = new WritableCellFormat(font1);
		fontFormat.setBackground(Colour.WHITE);
		fontFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		fontFormat.setAlignment(Alignment.CENTRE);

		init = true;
	}

	public void createUserExcelFile(File destFile) throws WriteException, IOException {
		// 字体初始化
		if (init == false)
			init();

		book = Workbook.createWorkbook(destFile);
		sheet = book.createSheet("人口统计表", 0);
		// 设置列的宽度
		sheet.setColumnView(0, 50);
		// 第一列
		sheet.addCell(new Label(column, row++, "第x天", fontFormat));
		
		
		sheet.addCell(new Label(column, row++, "当前健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "当前潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "当前确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "当前隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "累计治愈人数", fontFormat));
		sheet.addCell(new Label(column, row++, "累计死亡人数", fontFormat));
		sheet.addCell(new Label(column, row++, "已用床位数", fontFormat));
		sheet.addCell(new Label(column, row++, "未使用床位数", fontFormat));
		sheet.addCell(new Label(column, row++, "累计密切接触者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "累计轻症者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "累计重症者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "接种疫苗者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "佩戴口罩者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "佩戴口罩并接种疫苗者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "医院数量", fontFormat));
		sheet.addCell(new Label(column, row++, "高校学生数量", fontFormat));
		sheet.addCell(new Label(column, row++, "政府防控等级", fontFormat));

		
		
		sheet.addCell(new Label(column, row++, "0-4岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "5-9岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "10-14岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "15-19岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "20-24岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "25-29岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "30-34岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "35-39岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "40-44岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "45-49岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "50-54岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "55-59岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "60-64岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "65-69岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "70-74岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "75-79岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "80-84岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "85-89岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "90-94岁的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "95-100岁的确诊人数", fontFormat));

		sheet.addCell(new Label(column, row++, "0-4岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "5-9岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "10-14岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "15-19岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "20-24岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "25-29岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "30-34岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "35-39岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "40-44岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "45-49岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "50-54岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "55-59岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "60-64岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "65-69岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "70-74岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "75-79岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "80-84岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "85-89岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "90-94岁的密接人数", fontFormat));
		sheet.addCell(new Label(column, row++, "95-100岁的密接人数", fontFormat));

		sheet.addCell(new Label(column, row++, "0-4岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "5-9岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "10-14岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "15-19岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "20-24岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "25-29岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "30-34岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "35-39岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "40-44岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "45-49岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "50-54岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "55-59岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "60-64岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "65-69岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "70-74岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "75-79岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "80-84岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "85-89岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "90-94岁的健康人数", fontFormat));
		sheet.addCell(new Label(column, row++, "95-100岁的健康人数", fontFormat));

		sheet.addCell(new Label(column, row++, "0-4岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "5-9岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "10-14岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "15-19岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "20-24岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "25-29岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "30-34岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "35-39岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "40-44岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "45-49岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "50-54岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "55-59岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "60-64岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "65-69岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "70-74岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "75-79岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "80-84岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "85-89岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "90-94岁的隔离人数", fontFormat));
		sheet.addCell(new Label(column, row++, "95-100岁的隔离人数", fontFormat));

		sheet.addCell(new Label(column, row++, "0-4岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "5-9岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "10-14岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "15-19岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "20-24岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "25-29岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "30-34岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "35-39岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "40-44岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "45-49岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "50-54岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "55-59岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "60-64岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "65-69岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "70-74岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "75-79岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "80-84岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "85-89岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "90-94岁的潜伏人数", fontFormat));
		sheet.addCell(new Label(column, row++, "95-100岁的潜伏人数", fontFormat));
		
		sheet.addCell(new Label(column, row++, "一月的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "二月的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "三月的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "四月的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "五月的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "六月的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "七月的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "八月的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "九月的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "十月的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "十一月的确诊人数", fontFormat));
		sheet.addCell(new Label(column, row++, "十二月的确诊人数", fontFormat));
		
//		小店  杏花岭  迎泽  尖草坪  晋源  万柏林
		
		sheet.addCell(new Label(column, row++, "小店区密接者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "小店区高校人数", fontFormat));
		sheet.addCell(new Label(column, row++, "小店区确诊者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "小店区健康者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "小店区潜伏者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "小店区隔离者人数", fontFormat));
		
		sheet.addCell(new Label(column, row++, "杏花岭区密接者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "杏花岭区高校人数", fontFormat));
		sheet.addCell(new Label(column, row++, "杏花岭区确诊者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "杏花岭区健康者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "杏花岭区潜伏者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "杏花岭区隔离者人数", fontFormat));
		
		sheet.addCell(new Label(column, row++, "迎泽区密接者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "迎泽区高校人数", fontFormat));
		sheet.addCell(new Label(column, row++, "迎泽区确诊者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "迎泽区健康者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "迎泽区潜伏者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "迎泽区隔离者人数", fontFormat));
		
		sheet.addCell(new Label(column, row++, "尖草坪区密接者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "尖草坪区高校人数", fontFormat));
		sheet.addCell(new Label(column, row++, "尖草坪区确诊者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "尖草坪区健康者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "尖草坪区潜伏者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "尖草坪区隔离者人数", fontFormat));
		
		sheet.addCell(new Label(column, row++, "晋源区密接者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "晋源区高校人数", fontFormat));
		sheet.addCell(new Label(column, row++, "晋源区确诊者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "晋源区健康者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "晋源区潜伏者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "晋源区隔离者人数", fontFormat));
		
		sheet.addCell(new Label(column, row++, "万柏林区密接者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "万柏林区高校人数", fontFormat));
		sheet.addCell(new Label(column, row++, "万柏林区确诊者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "万柏林区健康者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "万柏林区潜伏者人数", fontFormat));
		sheet.addCell(new Label(column, row++, "万柏林区隔离者人数", fontFormat));
		book.write();
		if (book != null)
			book.close();
	}

}