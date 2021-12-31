package age.data.source.quarantinedpatient;

import data.source.DataStorage;
import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 获取5-9确诊人数
 */
public class Age_5_9_QuarantinedPatient_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "Age_5_9_QuarantinedPatient_Num";
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
		return DataStorage.getInstance().getAge_5_9_QuarantinedPatient_Num();
	}

	@Override
	public void reset() {

	}

}
