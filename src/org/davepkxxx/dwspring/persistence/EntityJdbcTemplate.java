package org.davepkxxx.dwspring.persistence;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

public class EntityJdbcTemplate implements EntityJdbcOperations {
	
	private final JdbcOperations classicJdbcOperations;
	
	private final NamedParameterJdbcOperations classicNamedParameterJdbcOperations;
	
	private Dialect dialect;

	public EntityJdbcTemplate(DataSource dataSource) {
		this.classicJdbcOperations = new JdbcTemplate(dataSource);
		this.classicNamedParameterJdbcOperations = new NamedParameterJdbcTemplate(classicJdbcOperations);
	}

	public EntityJdbcTemplate(JdbcOperations classicJdbcTemplate) {
		Assert.notNull(classicJdbcTemplate, "JdbcTemplate must not be null");
		this.classicJdbcOperations = classicJdbcTemplate;
		this.classicNamedParameterJdbcOperations = new NamedParameterJdbcTemplate(classicJdbcTemplate);
	}

	public EntityJdbcTemplate(NamedParameterJdbcOperations classicNamedParameterJdbcOperations) {
		Assert.notNull(classicNamedParameterJdbcOperations, "NamedParameterJdbcTemplate must not be null");
		this.classicJdbcOperations = classicNamedParameterJdbcOperations.getJdbcOperations();
		this.classicNamedParameterJdbcOperations = classicNamedParameterJdbcOperations;
	}

	public JdbcOperations getJdbcOperations() {
		return classicJdbcOperations;
	}

	public NamedParameterJdbcOperations getNamedParameterJdbcOperations() {
		return classicNamedParameterJdbcOperations;
	}
	
	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public KeyHolder save(Object entity, String... columnNames) {
		String sql = dialect.toInsert(columnNames);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(entity);
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		List<String> idColumnNames = new EntityParser(entity.getClass()).getIdColumnNames();
		String[] keyColumnNames = idColumnNames.toArray(new String[idColumnNames.size()]);
		classicNamedParameterJdbcOperations.update(sql, paramSource, generatedKeyHolder, keyColumnNames);
		return generatedKeyHolder;
	}

	public KeyHolder save(Object entity) {
		List<String> columnNames = new EntityParser(entity.getClass()).getColumnNames();
		String[] colNames = columnNames.toArray(new String[columnNames.size()]);
		return save(entity, colNames);
	}
	
	public KeyHolder saveDefault(Object entity) {
		List<String> columnNames = new EntityParser(entity.getClass()).getColumnNames();
		String[] colNames = columnNames.toArray(new String[columnNames.size()]);
		return save(entity, colNames);
	}

	public int update(Object entity, String... columnNames) {
		List<String> idColumnNames = new EntityParser(entity.getClass()).getIdColumnNames();
		String[] idColNames = idColumnNames.toArray(new String[idColumnNames.size()]);
		String sql = dialect.toUpdate(columnNames) + dialect.toWhere(idColNames);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(entity);
		return classicNamedParameterJdbcOperations.update(sql, paramSource);
	}

	public int update(Object entity) {
		List<String> columnNames = new EntityParser(entity.getClass()).getColumnNames();
		String[] colNames = columnNames.toArray(new String[columnNames.size()]);
		return update(entity, colNames);
	}
	
	public int updateDefault(Object entity) {
		List<String> columnNames = new EntityParser(entity.getClass()).getColumnNames();
		String[] colNames = columnNames.toArray(new String[columnNames.size()]);
		return update(entity, colNames);
	}
	
	public <T> List<T> query(Class<T> entityType) {
		List<String> columnNames = new EntityParser(entityType).getColumnNames();
		String[] colNames = columnNames.toArray(new String[columnNames.size()]);
		String sql = dialect.toSelect(colNames);
		return getJdbcOperations().query(sql, new BeanPropertyRowMapper<T>(entityType));
	}
	
	public <T> List<T> queryById(Class<T> entityType, Object id) {
		List<String> columnNames = new EntityParser(entityType).getColumnNames();
		String[] colNames = columnNames.toArray(new String[columnNames.size()]);
		List<String> idColumnNames = new EntityParser(entityType).getIdColumnNames();
		String[] idColNames = idColumnNames.toArray(new String[idColumnNames.size()]);
		String sql = dialect.toSelect(colNames) + dialect.toWhere(idColNames);
		return getJdbcOperations().query(sql, new BeanPropertyRowMapper<T>(entityType));
	}
	
	public <T> List<T> queryByIds(Object entity) {
		EntityParser parser = new EntityParser(entity.getClass());
		List<String> columnNames = parser.getColumnNames();
		String[] colNames = columnNames.toArray(new String[columnNames.size()]);
		List<String> idColumnNames = parser.getIdColumnNames();
		String[] idColNames = idColumnNames.toArray(new String[idColumnNames.size()]);
		String sql = dialect.toSelect(colNames) + dialect.toWhere(idColNames);
		return getJdbcOperations().query(sql, new BeanPropertyRowMapper<T>(entity.getClass()));
	}

}
