package data.source;

import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 政府防控等级
 */
public class Control_LevelDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "Control_Level";
	}

	@Override
	public Class<?> getDataType() {
		return int.class;
	}

	@Override
	public Class<?> getSourceType() {
		return Context.class;
	}

	@Override
	public Object get(Iterable<?> objs, int size) {
		return DataStorage.getInstance().getControl_Level();
	}

	@Override
	public void reset() {

	}

	
}
