package org.davepkxxx.dwspring.persistence;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

public class DialectDaoSupport extends NamedParameterJdbcDaoSupport {
	
	private Dialect dialect;
	
	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	public void top(StringBuilder sql, int size) {
		dialect.forTop(sql, size);
	}
	
	public void paging(StringBuilder sql, int start, int size) {
		dialect.forPaging(sql, start, size);
	}

}
