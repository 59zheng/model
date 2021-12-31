package age.data.source.quarantinedpatient;

import data.source.DataStorage;
import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 获取35-39确诊人数
 */
public class Age_35_39_QuarantinedPatient_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "Age_35_39_QuarantinedPatient_Num";
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
		return DataStorage.getInstance().getAge_35_39_QuarantinedPatient_Num();
	}

	@Override
	public void reset() {

	}

}
