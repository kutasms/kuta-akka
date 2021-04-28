package com.kuta.akka.base.auto;

public class JSONPluginTaskDataBuilder extends PluginTaskDataBuilder<JSONTaskData> {

	private PluginDataSource<JSONTaskData> dataSource;
	
	public static JSONPluginTaskDataBuilder newBuilder() {
		return new JSONPluginTaskDataBuilder();
	}

	public JSONPluginTaskDataBuilder withDataSource( PluginDataSource<JSONTaskData> dataSource) {
		this.dataSource = dataSource;
		return this;
	}
	
	@Override
	public PluginJobDetailImpl<JSONTaskData> build() {
		// TODO Auto-generated method stub
		JSONPluginJobDetailImpl impl = new JSONPluginJobDetailImpl();
		impl.setDataSource(dataSource);
		return impl;
	}
}
