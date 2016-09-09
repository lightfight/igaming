package com.lightfight.game.mysql;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.lightfight.game.vo.HeroVO;

public class DatabaseTest {

	NamedParameterJdbcTemplate jdbcTemplate; // 主要是查看这个类的源代码
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	void insert(){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("key", "value");
		
		JdbcOperations operations =  jdbcTemplate.getJdbcOperations();
		String table_count_sql = "SELECT count(table_name) FROM information_schema.TABLES WHERE table_schema =? and table_name =?";
		operations.queryForObject(table_count_sql, Integer.class, "rank", "t_10000100001");
		
		operations.execute("sql"); // 执行某条语句
		
		// 批量获取
		String sql = "select * from onlineplayercountlog20160820";
		jdbcTemplate.query(sql, new BeanPropertyRowMapper(HeroVO.class));
		
		// 批量插入
	}
}
