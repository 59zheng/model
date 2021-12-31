package data.source;

import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 接种疫苗人数据
 */
public class VACCINATION_MASK_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "VACCINATION_MASK_Num";
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
		return DataStorage.getInstance().getVACCINATION_MASK_Num();
	}

	@Override
	public void reset() {

	}

}
