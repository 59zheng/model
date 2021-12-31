package data.source;

import repast.simphony.context.Context;
import repast.simphony.data2.AggregateDataSource;

/**
 *高校学生数据
 */
public class Student_NumDataSource implements AggregateDataSource {

	@Override
	public String getId() {
		return "Student_Num";
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
		return DataStorage.getInstance().getStudent_Num();
	}

	@Override
	public void reset() {

	}

}
