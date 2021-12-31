package age.data.source.healthyperson;

import data.source.DataStorage;
import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 获取15-19确诊人数
 */
public class Age_15_19_HealthyPerson_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "Age_15_19_HealthyPerson_Num";
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
		return DataStorage.getInstance().getAge_15_19_HealthyPerson_Num();
	}

	@Override
	public void reset() {

	}

}
