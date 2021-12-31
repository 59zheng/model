package age.data.source.patient;

import data.source.DataStorage;
import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 获取20-24确诊人数
 */
public class Age_20_24_Patient_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "Age_20_24_Patient_Num";
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
		return DataStorage.getInstance().getAge_20_24_Patient_Num();
	}

	@Override
	public void reset() {

	}

}
