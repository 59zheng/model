package data.source;

import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 医院数量
 */
public class Hospital_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "Hospital_Num";
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
		return DataStorage.getInstance().getHospital_Num();
	}

	@Override
	public void reset() {

	}

}
