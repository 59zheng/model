package data.source;

import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 已治愈出院的人数据
 */
public class CURED_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "CURED_Num";
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
		return DataStorage.getInstance().getCURED_Num();
	}

	@Override
	public void reset() {

	}


}
