package data.source;

import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 医院未使用床位数据
 */
public class UnuseBedNumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "UnuseBedNum";
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
		return DataStorage.getInstance().getUnuseBedNum();
	}

	@Override
	public void reset() {

	}

}
