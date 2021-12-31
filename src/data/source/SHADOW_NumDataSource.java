package data.source;

import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 *潜伏期数据
 */
public class SHADOW_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "SHADOW_Num";
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
		return DataStorage.getInstance().getSHADOW_Num();
		//return DataStorage.getInstance().getSHADOW_Num()*2.4;
	}

	@Override
	public void reset() {

	}

}
