package data.source;

import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 发病且已确诊为感染病人数据
 */
public class CONFIRMED_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "CONFIRMED_Num";
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
		return DataStorage.getInstance().getCONFIRMED_Num();
	}

	@Override
	public void reset() {

	}

}
