package data.source;

import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 医院使用中床位数据
 */
public class UseBedNumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "UseBedNum";
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
		return DataStorage.getInstance().getUseBedNum();
	}

	@Override
	public void reset() {

	}

}
