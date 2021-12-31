package age.data.source.latentpatient;

import data.source.DataStorage;
import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 获取60-64确诊人数
 */
public class Age_60_64_LatentPatient_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "Age_60_64_LatentPatient_Num";
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
		return DataStorage.getInstance().getAge_60_64_LatentPatient_Num();
	}

	@Override
	public void reset() {

	}

}
