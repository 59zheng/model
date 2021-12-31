package age.data.source.closecontact;

import data.source.DataStorage;
import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 * 获取65-69确诊人数
 */
public class Age_65_69_CloseContact_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "Age_65_69_CloseContact_Num";
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
		return DataStorage.getInstance().getAge_65_69_CloseContact_Num();
	}

	@Override
	public void reset() {

	}

}
