package age.data.source.healthyperson;

import data.source.DataStorage;
import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 获取30-34确诊人数
 */
public class Age_30_34_HealthyPerson_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "Age_30_34_HealthyPerson_Num";
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
		return DataStorage.getInstance().getAge_30_34_HealthyPerson_Num();
	}

	@Override
	public void reset() {

	}

}
