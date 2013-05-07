package org.davepkxxx.dwspring.persistence;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.KeyHolder;

public interface EntityJdbcOperations {
	
	NamedParameterJdbcOperations getNamedParameterJdbcOperations();
	
	KeyHolder save(String sql, Object entity);
	
	int update(String sql, Object entity);

}
