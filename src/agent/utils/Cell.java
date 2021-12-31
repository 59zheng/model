package agent.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import agent.person.Agent;
import agent.place.Hospital;
import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.relogo.ide.dynamics.NetLogoSystemDynamicsParser.intg_return;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

/**
 * 栅格Agent，存储一个Cell内的所有数据
 *
 * @author QinShipeng
 *
 */
public class Cell extends Agent {
    // ========================== Constructors ===========================
    /**
     * 构造函数
     *
     * @param x
     *            x坐标
     * @param y
     *            y坐标
     * @param space
     *            空间
     * @param grid
     *            栅格
     */
    public Cell(int x, int y, Context<Object> context, ContinuousSpace<Object> space, Grid<Object> grid) {
        this.x = x;
        this.y = y;
        this.context = context;
        this.space = space;
        this.grid = grid;
    }

    // ======================= Private Properties ========================
    /**
     * x坐标
     */
    int x;
    /**
     * y坐标
     */
    int y;

    public Context<Object> getContext() {
        return context;
    }

    public void setContext(Context<Object> context) {
        this.context = context;
    }

    /**
     * context环境
     */
    Context<Object> context;
    /**
     * space空间
     */
    ContinuousSpace<Object> space;
    /**
     * grid栅格
     */
    Grid<Object> grid;


    double borderX;// 太原区边界x
    double borderY;// 太原区边界Y

    public double getTaiYaun() {
        return TaiYaun;
    }

    public void setTaiYaun(double taiYaun) {
        TaiYaun = taiYaun;
    }

    public int getIsXiaoDian() {
        return isXiaoDian;
    }

    public void setIsXiaoDian(int isXiaoDian) {
        this.isXiaoDian = isXiaoDian;
    }

    public int getIsXingHuaLing() {
        return isXingHuaLing;
    }

    public void setIsXingHuaLing(int isXingHuaLing) {
        this.isXingHuaLing = isXingHuaLing;
    }

    public int getIsYingZe() {
        return isYingZe;
    }

    public void setIsYingZe(int isYingZe) {
        this.isYingZe = isYingZe;
    }

    public int getIsJingYuan() {
        return isJingYuan;
    }

    public void setIsJingYuan(int isJingYuan) {
        this.isJingYuan = isJingYuan;
    }

    public int getIsWangBaiLing() {
        return isWangBaiLing;
    }

    public void setIsWangBaiLing(int isWangBaiLing) {
        this.isWangBaiLing = isWangBaiLing;
    }

    public int getIsJiangCaoPing() {
        return isJiangCaoPing;
    }

    public void setIsJiangCaoPing(int isJiangCaoPing) {
        this.isJiangCaoPing = isJiangCaoPing;
    }

    public double getTaiYaunM() {
		return TaiYaunM;
	}

	public void setTaiYaunM(double taiYaunM) {
		TaiYaunM = taiYaunM;
	}
	

	public int getIsAirport() {
		return isAirport;
	}

	public void setIsAirport(int isAirport) {
		this.isAirport = isAirport;
	}

	public int getIsRailwayStation() {
		return isRailwayStation;
	}

	public void setIsRailwayStation(int isRailwayStation) {
		this.isRailwayStation = isRailwayStation;
	}

	public int getIsBusStation() {
		return isBusStation;
	}

	public void setIsBusStation(int isBusStation) {
		this.isBusStation = isBusStation;
	}

	public int getIsBusinessDistrict() {
		return isBusinessDistrict;
	}

	public void setIsBusinessDistrict(int isBusinessDistrict) {
		this.isBusinessDistrict = isBusinessDistrict;
	}

	public int getIsCollege() {
		return isCollege;
	}

	public void setIsCollege(int isCollege) {
		this.isCollege = isCollege;
	}

	double TaiYaun;// 太原区边界Y
    double TaiYaunM;// 太原区面
    int  isXiaoDian;// 是否属于小店区
    int  isXingHuaLing;// 是否属于小店区
    int  isYingZe;// 是否属于小店区
    int  isJingYuan;// 是否属于小店区
    int  isWangBaiLing;// 是否属于小店区
    int  isJiangCaoPing;// 是否属于小店区
    int  isHospital;
    int  isAirport;
    int  isRailwayStation;
    int  isBusStation;
    int  isBusinessDistrict;
    int  isCollege; 

    public int getIsHospital() {
		return isHospital;
	}

	public void setIsHospital(int isHospital) {
		this.isHospital = isHospital;
	}

	public double getBorderX() {
        return borderX;
    }

    public void setBorderX(double border) {
        this.borderX = border;
    }

    public double getBorderY() {
        return borderY;
    }

    public void setBorderY(double border) {
        this.borderY = border;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ContinuousSpace<Object> getSpace() {
        return space;
    }

    public void setSpace(ContinuousSpace<Object> space) {
        this.space = space;
    }

    public Grid<Object> getGrid() {
        return grid;
    }

    public void setGrid(Grid<Object> grid) {
        this.grid = grid;
    }

}
