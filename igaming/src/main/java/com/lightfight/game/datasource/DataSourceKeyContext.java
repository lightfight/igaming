package com.lightfight.game.datasource;

import org.springframework.stereotype.Component;

/**
 * 数据源键上下文
 * 
 * @author deliang
 *
 */
@Component
public class DataSourceKeyContext {

	private ThreadLocal<Integer> context = new ThreadLocal<>();

	public void setDataSourceKey(Integer dataSourceKey) {
		context.set(dataSourceKey);
	}

	public Integer getDataSourceKey() {
		return context.get();
	}

	public void clearDataSourceKey() {
		context.remove();
	}
}
