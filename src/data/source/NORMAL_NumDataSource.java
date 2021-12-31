package data.source;

import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 *正常人数据
 */
public class NORMAL_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "NORMAL_Num";
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
		return DataStorage.getInstance().getNORMAL_Num();
	}

	@Override
	public void reset() {

	}

}
