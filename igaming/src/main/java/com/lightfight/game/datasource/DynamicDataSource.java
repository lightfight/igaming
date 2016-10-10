package com.lightfight.game.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.stereotype.Component;

/**
 * 数据源管理类<BR>
 * 直接继承自 {@link org.springframework.jdbc.datasource.AbstractDataSource}
 * 
 * @author deliang
 *
 */

@Component
public class DynamicDataSource extends AbstractDataSource {

	/** <渠道ID,数据源> **/
	private Map<Integer, DataSource> dataSources = new HashMap<>();

	/** 默认的数据源 **/
	private DataSource defaultDataSource;

	@Resource
	DataSourceKeyContext dataSourceKeyContext;

	public void addDataSource(Integer key, DataSource dataSource) {
		dataSources.put(key, dataSource);
	}

	public void setDefaultDataSource(DataSource defaultDataSource) {
		this.defaultDataSource = defaultDataSource;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return lookupDataSource().getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return lookupDataSource().getConnection(username, password);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if (iface.isInstance(this)) {
			return (T) this;
		}
		return lookupDataSource().unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return (iface.isInstance(this) || lookupDataSource().isWrapperFor(iface));
	}

	protected DataSource lookupDataSource() {
		Integer lookupKey = dataSourceKeyContext.getDataSourceKey();
		DataSource dataSource = dataSources.get(lookupKey);

		if (dataSource == null) { // 如果不存在就使用默认的数据源
			dataSource = defaultDataSource;
		}

		if (dataSource == null) {
			throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
		}
		return dataSource;
	}

}
