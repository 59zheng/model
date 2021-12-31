package temper.data.source;

import data.source.DataStorage;
import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 获取十月确诊人数
 */
public class OctoberNumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "OctoberNum";
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
		return DataStorage.getInstance().getOctoberNum();
	}

	@Override
	public void reset() {

	}

}
