package data.source;

import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 *隔离治疗人数据
 */
public  class FREEZE_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "FREEZE_Num";
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
		
		return DataStorage.getInstance().getFREEZE_Num();
	}

	@Override
	public void reset() {

	}

}
