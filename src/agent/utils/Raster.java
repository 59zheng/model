package agent.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;

/**
 * 栅格数据处理
 * 
 * @author QinShipeng
 *
 */
public class Raster {
	// ========================== Constructors ===========================
	public Raster() {

	}

	/**
	 * 构造函数，创建对象时，直接读取ArcgisTxt数据
	 * 
	 * @param fileName
	 *            文件路径
	 */
	public Raster(String fileName) {
		ReadArcgisRasterData(fileName);
	}

	// ======================= Private Properties ========================
	/**
	 * 栅格数据的列数
	 */
	private int ncols;
	/**
	 * 栅格数据的行数
	 */
	private int nrows;
	/**
	 * 地图左下角的坐标x
	 */
	private String xllcorner;
	/**
	 * 地图左下角的坐标y
	 */
	private String yllcorner;
	/**
	 * 一个栅格的大小（单位：平方米）
	 */
	private String cellsize;
	/**
	 * 无数据的指示值
	 */
	private String NODATA_value;
	/**
	 * 栅格数据的有效数据
	 */
	private double[][] data;

	// ========================== Public Methods =========================

	/**
	 * 对ArcgisTxt数据文件进行读操作（读操作时，为了计算精确，将数据存储为double型的）
	 * 
	 * @param fileName
	 *            文件路径
	 */
	public void ReadArcgisRasterData(String fileName) {
		FileReader fileReader;// 文件流
		BufferedReader bufferedReader;// 字符流
		try {
			// 打开文件流
			fileReader = new FileReader(fileName);
			// 打开字符流
			bufferedReader = new BufferedReader(fileReader);
			// 读取头文件并存储
			readHeader(bufferedReader, fileName);
			/*
			 * 读取头文件信息后，data的大小可以确定，ncols为横坐标，nrows为纵坐标
			 * 
			 * 定义data[nrows][ncols]，完全对应地图
			 */
			data = new double[nrows][ncols];
			// 读取栅格数据并存储
			readBody(bufferedReader);
			try {
				// 关闭字符流
				bufferedReader.close();
				// 关闭文件流
				fileReader.close();
			} catch (IOException e) {
				System.out.println("关闭字符流、文件流异常");
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			System.out.println("读取ArcgisTxt数据文件异常");
			e.printStackTrace();
		}
	}

	/**
	 * 读取头文件
	 * 
	 * @param bufferedReader
	 *            字符流缓存读取器
	 */
	private void readHeader(BufferedReader bufferedReader, String fileName) {
		String stringLine;// 存储一行数据
		String[] arrayLine;// 将一行数据存储为数组
		try {
			// 读取头文件
			// 读取头文件的第一行，设置列数
			stringLine = bufferedReader.readLine();
			arrayLine = stringLine.split("\\s+");
			ncols = Integer.parseInt(arrayLine[1]);

			// 读取头文件的第二行，设置行数
			stringLine = bufferedReader.readLine();
			arrayLine = stringLine.split("\\s+");
			nrows = Integer.parseInt(arrayLine[1]);

			// 读取头文件的第三行，设置左下角横坐标
			stringLine = bufferedReader.readLine();
			arrayLine = stringLine.split("\\s+");
			xllcorner = arrayLine[1];

			// 读取头文件的第四行，设置左下角纵坐标
			stringLine = bufferedReader.readLine();
			arrayLine = stringLine.split("\\s+");
			yllcorner = arrayLine[1];

			// 读取头文件的第五行，设置单元格的大小
			stringLine = bufferedReader.readLine();
			arrayLine = stringLine.split("\\s+");
			cellsize = arrayLine[1];

			// 读取头文件的第六行，设置无数据时的表示值
			stringLine = bufferedReader.readLine();
			arrayLine = stringLine.split("\\s+");
			NODATA_value = arrayLine[1];
		} catch (IOException e) {
			System.out.println("读取头文件异常");
			e.printStackTrace();
		}

		System.out.println("打印头文件信息：");
		System.out.println("文件路径：" + fileName);
		System.out.println("ncols = " + ncols);
		System.out.println("nrows = " + nrows);
		System.out.println("xllcorner = " + xllcorner);
		System.out.println("yllcorner = " + yllcorner);
		System.out.println("cellsize = " + cellsize);
		System.out.println("NODATA_value = " + NODATA_value);
	}

	/**
	 * 读取栅格数据
	 * 
	 * @param bufferedReader
	 *            字符流缓存读取器
	 */
	private void readBody(BufferedReader bufferedReader) {
		String stringLine;// 存储一行数据
		String[] arrayLine;// 将一行数据存储为数组
		int nLine = nrows - 1;// 设置为纵坐标最大值
		try {
			// 读取数据
			while (bufferedReader.ready()) {
				stringLine = bufferedReader.readLine();// 每次读一行
				arrayLine = stringLine.split("\\s+");// 把一行数据分成数组
				// 将二维数据数据和地图坐标对应
				for (int i = 0; i < arrayLine.length; i++) {
					data[nLine][i] = Double.parseDouble(arrayLine[i]);
				}
				nLine--;
			}
		} catch (IOException e) {
			System.out.println("读取栅格数据异常");
			e.printStackTrace();
		}
	}

	/**
	 * 对ArcgisTxt数据文件进行写操作（写操作时可对输出数据格式进行修改，double或者integer，默认为integer）
	 * 
	 * @param fileName
	 *            文件路径
	 * @param format
	 *            数据存储格式（double或者integer）
	 */
	public void WriteArcgisRasterData(String fileName, String format) {
		FileWriter fileWriter;// 文件流
		BufferedWriter bufferedWriter;// 字符流
		try {
			// 打开文件流
			fileWriter = new FileWriter(fileName);
			// 打开字符流
			bufferedWriter = new BufferedWriter(fileWriter);
			// 将头文件写入ArcgisTxt数据文件
			writeHead(bufferedWriter);
			// 将栅格数据写入ArcgisTxt数据文件
			writeBody(bufferedWriter, format);
			try {
				// 关闭字符流
				bufferedWriter.close();
				// 关闭文件流
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("关闭字符流、文件流异常");
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("写ArcgisTxt数据文件异常");
			e.printStackTrace();
		}
	}

	/**
	 * 写头文件
	 * 
	 * @param bufferedWriter
	 *            字符流缓存读取器
	 */
	private void writeHead(BufferedWriter bufferedWriter) {
		try {
			bufferedWriter.append("ncols         " + Integer.toString(ncols) + "\r\n");
			bufferedWriter.append("nrows         " + Integer.toString(nrows) + "\r\n");
			bufferedWriter.append("xllcorner     " + xllcorner + "\r\n");
			bufferedWriter.append("yllcorner     " + yllcorner + "\r\n");
			bufferedWriter.append("cellsize      " + cellsize + "\r\n");
			bufferedWriter.append("NODATA_value  " + NODATA_value + "\r\n");
		} catch (IOException e) {
			System.out.println("写头文件异常");
			e.printStackTrace();
		}
	}

	/**
	 * 写栅格数据
	 * 
	 * @param bufferedWriter
	 *            字符流缓存读取器
	 * @param format
	 *            数据存储格式（double或者integer）
	 */
	private void writeBody(BufferedWriter bufferedWriter, String format) {
		if (format.equals("double")) {
			int nLine = nrows - 1;
			try {
				// 将数据写入ArcgisTxt文件
				for (int i = 0; i <= nLine; nLine--) {
					for (int j = 0; j < ncols; j++) {
						if (data[nLine][j] != -9999) {
//							bufferedWriter.append(Double.toString(data[nLine][j]) + " ");
							
							NumberFormat nf = NumberFormat.getNumberInstance();
					        nf.setMaximumFractionDigits(2);
							bufferedWriter.append((nf.format(data[nLine][j])) + " ");
						} else {
							bufferedWriter.append((int) (data[nLine][j]) + " ");
						}
					}
					bufferedWriter.append("\r\n");// 换行
				}
			} catch (IOException e) {
				System.out.println("写栅格数据异常");
				e.printStackTrace();
			}
		} else if (format.equals("integer")) {
			int nLine = nrows - 1;
			try {
				// 将数据写入ArcgisTxt文件
				for (int i = 0; i <= nLine; nLine--) {
					for (int j = 0; j < ncols; j++) {
						bufferedWriter.append((int) data[nLine][j] + " ");
					}
					bufferedWriter.append("\r\n");// 换行
				}
			} catch (IOException e) {
				System.out.println("写栅格数据异常");
				e.printStackTrace();
			}
		} else {
			int nLine = nrows - 1;
			try {
				// 将数据写入ArcgisTxt文件
				for (int i = 0; i <= nLine; nLine--) {
					for (int j = 0; j < ncols; j++) {
						bufferedWriter.append((int) data[nLine][j] + " ");
					}
					bufferedWriter.append("\r\n");// 换行
				}
			} catch (IOException e) {
				System.out.println("写栅格数据异常");
				e.printStackTrace();
			}
		}
	}

	// ======================= Getters and Setters =======================
	public int getNcols() {
		return ncols;
	}

	public void setNcols(int ncols) {
		this.ncols = ncols;
	}

	public int getNrows() {
		return nrows;
	}

	public void setNrows(int nrows) {
		this.nrows = nrows;
	}

	public String getXllcorner() {
		return xllcorner;
	}

	public void setXllcorner(String xllcorner) {
		this.xllcorner = xllcorner;
	}

	public String getYllcorner() {
		return yllcorner;
	}

	public void setYllcorner(String yllcorner) {
		this.yllcorner = yllcorner;
	}

	public String getCellsize() {
		return cellsize;
	}

	public void setCellsize(String cellsize) {
		this.cellsize = cellsize;
	}

	public String getNODATA_value() {
		return NODATA_value;
	}

	public void setNODATA_value(String nODATA_value) {
		NODATA_value = nODATA_value;
	}

	public double[][] getData() {
		return data;
	}

	public void setData(double[][] data) {
		this.data = data;
	}

	/**
	 * 获取坐标为（x，y）的值，其值对应Repast中的Grid坐标
	 * 
	 * @param x
	 *            grid的横坐标
	 * @param y
	 *            grid的纵坐标
	 * @return 点（x，y）应该对应在地图显示上的数值
	 */
	public double getData(int x, int y) {
		return data[y][x];
	}

	/**
	 * 设置坐标为（x，y）的值，其值对应Repast中的Grid坐标
	 * 
	 * @param data
	 *            值
	 * @param x
	 *            grid的横坐标
	 * @param y
	 *            grid的纵坐标
	 */
	public void setData(double data, int x, int y) {
		this.data[y][x] = data;
	}
}
