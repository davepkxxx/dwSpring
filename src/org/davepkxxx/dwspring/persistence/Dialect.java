package org.davepkxxx.dwspring.persistence;

import org.springframework.jdbc.core.RowMapper;

public interface Dialect {
	
	public void forSaveAll(Object entity, StringBuilder sql);
	
	public void forSaveDefault(Object entity, StringBuilder sql);
	
	public void forSaveSome(Object entity, StringBuilder sql, String... fieldNames);
	
	public void forSaveSomeColumns(Object entity, StringBuilder sql, String... columnNames);
	
	public void forUpdateAll(Object entity, StringBuilder sql);
	
	public void forUpdateDefault(Object entity, StringBuilder sql);
	
	public void forUpdateSome(Object entity, StringBuilder sql, String... fieldNames);
	
	public void forDeleteAll(Object entity, StringBuilder sql);
	
	public void forDeleteDefault(Object entity, StringBuilder sql);
	
	public void forDeleteSome(Object entity, StringBuilder sql, String... fieldNames);
	
	public <T> RowMapper<T> forFindAll(Class<T> entityType, StringBuilder sql);
	
	public <T> RowMapper<T> forFindById(Class<?> entityType, StringBuilder sql);
	
	public <T> RowMapper<T> forFindByIds(Object entity, StringBuilder sql);
	
	public void forTop(StringBuilder sql, int size);
	
	public void forPaging(StringBuilder sql, int start, int size);

}
