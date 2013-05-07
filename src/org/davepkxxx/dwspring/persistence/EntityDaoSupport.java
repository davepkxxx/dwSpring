package org.davepkxxx.dwspring.persistence;

import org.springframework.jdbc.support.KeyHolder;

/**
 * Entiry access support.
 * @author David Dai
 */
public class EntityDaoSupport<T> extends DialectDaoSupport {
	
	private Class<T> entityType;
	
	private EntityJdbcTemplate entityJdbcTemplate;
	
	public Class<T> getEntityType() {
		return entityType;
	}

	public EntityJdbcTemplate getEntityJdbcTemplate() {
		return entityJdbcTemplate;
	}

	public void setEntityType(Class<T> entityType) {
		this.entityType = entityType;
	}

	public KeyHolder saveAll(T entity) {
		StringBuilder sql = new StringBuilder();
		getDialect().forSaveAll(entity, sql);
		return getEntityJdbcTemplate().save(sql.toString(), entity);
	}
	
	public KeyHolder saveDefault(T entity) {
		StringBuilder sql = new StringBuilder();
		getDialect().forSaveDefault(entity, sql);
		return getEntityJdbcTemplate().save(sql.toString(), entity);
	}
	
	public KeyHolder saveSome(T entity, String... fieldNames) {
		StringBuilder sql = new StringBuilder();
		getDialect().forSaveSome(entity, sql, fieldNames);
		return getEntityJdbcTemplate().save(sql.toString(), entity);
	}
	
	public int updateAll(T entity) {
		StringBuilder sql = new StringBuilder();
		getDialect().forUpdateAll(entity, sql);
		return getEntityJdbcTemplate().update(sql.toString(), entity);
	}
	
	public int updateDefault(T entity) {
		StringBuilder sql = new StringBuilder();
		getDialect().forUpdateDefault(entity, sql);
		return getEntityJdbcTemplate().update(sql.toString(), entity);
	}
	
	public int updateSome(T entity, String... fieldNames) {
		StringBuilder sql = new StringBuilder();
		getDialect().forUpdateSome(entity, sql, fieldNames);
		return getEntityJdbcTemplate().update(sql.toString(), entity);
	}
	
	public int deleteAll(T entity) {
		StringBuilder sql = new StringBuilder();
		getDialect().forDeleteAll(entity, sql);
		return getEntityJdbcTemplate().update(sql.toString(), entity);
	}
	
	public int deleteDefault(T entity) {
		StringBuilder sql = new StringBuilder();
		getDialect().forDeleteDefault(entity, sql);
		return getEntityJdbcTemplate().update(sql.toString(), entity);
	}
	
	public int deleteSome(T entity, String... fieldNames) {
		StringBuilder sql = new StringBuilder();
		getDialect().forDeleteSome(entity, sql, fieldNames);
		return getEntityJdbcTemplate().update(sql.toString(), entity);
	}
	
	public void findAll() {
		StringBuilder sql = new StringBuilder();
		getDialect().forFindAll(entityType, sql);
		// TODO 
	}
	
	public void findById(Object id) {
		StringBuilder sql = new StringBuilder();
		getDialect().forFindById(entityType, sql);
	}
	
	public void findByIds(T entity) {
		StringBuilder sql = new StringBuilder();
		getDialect().forFindByIds(entity, sql);
	}

}
