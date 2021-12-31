package age.data.source.patient;

import data.source.DataStorage;
import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 获取55-59确诊人数
 */
public class Age_55_59_Patient_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "Age_55_59_Patient_Num";
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
		return DataStorage.getInstance().getAge_55_59_Patient_Num();
	}

	@Override
	public void reset() {

	}

}
