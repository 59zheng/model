package data.source;

import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 有暴露感染风险人数据
 */
public class SUSPECTED_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "SUSPECTED_Num";
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
		//return 0;
		return DataStorage.getInstance().getSUSPECTED_Num();
	}

	@Override
	public void reset() {

	}

}
