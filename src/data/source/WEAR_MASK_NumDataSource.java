package data.source;

import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 戴口罩人数据
 */
public class WEAR_MASK_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "WEAR_MASK_Num";
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
		return DataStorage.getInstance().getWEAR_MASK_Num();
	}

	@Override
	public void reset() {

	}

}
