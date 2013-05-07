package org.davepkxxx.dwspring.persistence;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class EntityJdbcTemplate implements EntityJdbcOperations {
	
	private final NamedParameterJdbcOperations classicNamedParameterJdbcOperations;

	public EntityJdbcTemplate(DataSource dataSource) {
		this.classicNamedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
	}

	public EntityJdbcTemplate(NamedParameterJdbcOperations classicNamedParameterJdbcOperations) {
		this.classicNamedParameterJdbcOperations = classicNamedParameterJdbcOperations;
	}

	public NamedParameterJdbcOperations getNamedParameterJdbcOperations() {
		return classicNamedParameterJdbcOperations;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public KeyHolder save(String sql, Object entity) {
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(entity);
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		List<String> idColumnNames = new EntityParser(entity.getClass()).getIdColumnNames();
		String[] keyColumnNames = idColumnNames.toArray(new String[idColumnNames.size()]);
		classicNamedParameterJdbcOperations.update(sql, paramSource, generatedKeyHolder, keyColumnNames);
		return generatedKeyHolder;
	}

	public int update(String sql, Object entity) {
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(entity);
		return classicNamedParameterJdbcOperations.update(sql, paramSource);
	}

}
