package agent.place;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.stringtemplate.v4.compiler.CodeGenerator.subtemplate_return;

import repast.simphony.context.Context;
import repast.simphony.util.ContextUtils;
import COVID_19.COVID_19;
import COVID_19.SystemFunction;
import COVID_19.SystemParameter;
import agent.person.CollegeStudent;
import agent.person.HealthyPerson;
import agent.utils.Point;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

/**
 * 高校
 */
public class College extends Point {
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private static College instance;

	// 当前政府
	Government government = COVID_19.government;

	public College(ContinuousSpace<Object> space, Grid<Object> grid, int x, int y) {
		super(x, y);
		this.space = space;
		this.grid = grid;

	}

	@ScheduledMethod(start = 1, interval = 3, priority = 4)
	public void update() {
		infectInit();
	}

	public void infectInit() {
		// 获取到高校周围健康者禁止移动
		/**
		 * 寻找周围人群
		 */
		//获取到的健康者的栅格
		Grid<Object> healthyPersonGrid = HealthyPerson.grid;
		ContinuousSpace<Object> healthyPersonSpace = HealthyPerson.space;
		CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<Object>();
		for (int i = -2; i < 2; i++) {
			for (int i1 = -2; i1 < 2; i1++) {
				// 获取到周围的点
				Iterable Itrable = (Iterable) healthyPersonGrid.getObjectsAt(this.getX() + i, this.getY() + i1);
				Itrable.forEach(ite -> {
					list.add(ite);
				});
				// 判断周围的点是不是健康者
				for (Object obj : list) {
					//获取到当前健康者的点
					NdPoint spacePt = healthyPersonSpace.getLocation(obj);
					if (spacePt == null) {
						continue;
					}
					if (obj instanceof HealthyPerson) {

						Context<Object> context = ContextUtils.getContext(obj);
						HealthyPerson healthyPerson = (HealthyPerson) obj;
						context.remove(obj);
						CollegeStudent collegeStudent = new CollegeStudent(space, grid, City.getInstance(),
								this.getX() + i, this.getY() + i1, healthyPerson.getAge(), SystemParameter.worldTime,
								healthyPerson.getTemperature(),healthyPerson.getRegional());
						collegeStudent.init();
						context.add(collegeStudent);
						space.moveTo(collegeStudent, spacePt.getX(), spacePt.getY());
						grid.moveTo(collegeStudent, this.getX(), this.getY());
					}
				}

			}
		}

	}

}
