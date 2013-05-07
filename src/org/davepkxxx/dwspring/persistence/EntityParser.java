package org.davepkxxx.dwspring.persistence;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.jdbc.core.RowMapper;

public class EntityParser<T> {
	
	private Class<T> entityType;
	
	private Field[] entityFields;
	
	private List<Field> columnFields;
	
	private List<Column> columns;
	
	private List<String> columnNames;
	
	private List<Field> idFields;
	
	private List<String> idColumnNames;
	
	private RowMapper<T> rowMapper;

	public EntityParser(Class<T> entityType) {
		this.entityType = entityType;
	}
	
	public Class<T> getEntityType() {
		return entityType;
	}

	public Field[] getEntityFields() {
		if (entityFields == null) {
			entityFields = entityType.getFields();
		}
		return entityFields;
	}

	public List<Field> getColumnFields() {
		if (columnFields == null) {
			columnFields = new ArrayList<Field>();
			for (Field field : entityType.getFields()) {
				if (field.isAnnotationPresent(Column.class)) {
					columnFields.add(field);
				}
			}
		}
		return columnFields;
	}
	
	public List<Column> getColumns() {
		if (columns == null) {
			columns = new ArrayList<Column>(getColumnFields().size());
			for (Field idField : getColumnFields()) {
				columns.add(idField.getAnnotation(Column.class));
			}
		}
		return columns;
	}
	
	public List<String> getColumnNames() {
		if (columnNames == null) {
			columnNames = new ArrayList<String>(getColumns().size());
			for (Column column : getColumns()) {
				columnNames.add(column.name());
			}
		}
		return columnNames;
	}
	
	public List<Field> getIdFields() {
		if (idFields == null) {
			idFields = new ArrayList<Field>();
			for (Field field : getEntityFields()) {
				if (field.isAnnotationPresent(Id.class)) {
					idFields.add(field);
				}
			}
		}
		return idFields;
	}
	
	public List<String> getIdColumnNames() {
		if (idColumnNames == null) {
			idColumnNames = new ArrayList<String>(getIdFields().size());
			for (Field idField : getIdFields()) {
				idColumnNames.add(idField.getAnnotation(Column.class).name());
			}
		}
		return idColumnNames;
	}
	
	public RowMapper<T> getRowMapper() {
		if (rowMapper == null) {
			rowMapper = new RowMapper<T>() {

				public T mapRow(ResultSet rs, int rowNum) throws SQLException {
					try {
						T instance = entityType.newInstance();
						for (String columnName : getColumnNames()) {
							
						}
						return instance;
					} catch (InstantiationException e) {
						throw new SQLException(e.getMessage(), e);
					} catch (IllegalAccessException e) {
						throw new SQLException(e.getMessage(), e);
					}
				}
				
			};
		}
		return rowMapper;
	}

}
