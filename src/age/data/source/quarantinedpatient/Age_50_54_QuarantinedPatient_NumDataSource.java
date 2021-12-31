package age.data.source.quarantinedpatient;

import data.source.DataStorage;
import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 获取50-54确诊人数
 */
public class Age_50_54_QuarantinedPatient_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "Age_50_54_QuarantinedPatient_Num";
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
		return DataStorage.getInstance().getAge_50_54_QuarantinedPatient_Num();
	}

	@Override
	public void reset() {

	}

}
