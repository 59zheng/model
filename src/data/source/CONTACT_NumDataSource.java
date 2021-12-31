package data.source;

import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 密切接触者数据
 */
public class CONTACT_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "CONTACT_Num";
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
		return DataStorage.getInstance().getCONTACT_Num();
	}

	@Override
	public void reset() {

	}

}
