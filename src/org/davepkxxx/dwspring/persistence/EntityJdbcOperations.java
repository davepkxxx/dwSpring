package org.davepkxxx.dwspring.persistence;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.KeyHolder;

public interface EntityJdbcOperations {
	
	JdbcOperations getJdbcOperations();
	
	NamedParameterJdbcOperations getNamedParameterJdbcOperations();
	
	Dialect getDialect();
	
	<T> RowMapper<T> newRowMapper(Class<T> entityType);
	
	KeyHolder save(String sql, Object entity);
	
	int update(String sql, Object entity);

}
