package data.source;

import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 病死者数据
 */
public class SEVERE_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "SEVERE_Num";
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
		return DataStorage.getInstance().getSEVERE_Num();
	}

	@Override
	public void reset() {

	}

}
