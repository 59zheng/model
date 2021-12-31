package age.data.source.patient;

import data.source.DataStorage;
import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 获取25-29确诊人数
 */
public class Age_25_29_Patient_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "Age_25_29_Patient_Num";
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
		return DataStorage.getInstance().getAge_25_29_Patient_Num();
	}

	@Override
	public void reset() {

	}

}
