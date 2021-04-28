package com.kuta.akka.base.auto;

public abstract class PluginTaskDataBuilder<T extends PluginTaskData> {

	public abstract PluginJobDetailImpl<T> build();
}
