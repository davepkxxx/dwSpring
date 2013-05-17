package org.davepkxxx.dwspring.persistence;

import java.util.List;

public interface Dialect {
	
	public String toInsert(String... columnNames);
	
	public String toUpdate(String... columnNames);
	
	public String toDelete();
	
	public String toSelect(String... columnNames);
	
	public String toWhere(String... columnNames);
	
	public void forTop(StringBuilder sql, List<Object> params, int size);
	
	public void forPaging(StringBuilder sql, List<Object> params, int start, int size);

}
