package org.example.sql;

import org.example.persistance.Persistable;
import org.example.reflection.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class QueryGenerator {
    private final String USER_PROJECT_ROLES = "user_project_roles";
    private final String INSERT_COMMAND = "INSERT INTO %s (%s) VALUES (%s);";
    private final String SELECT_FROM_WHERE_USER_ID = "SELECT * FROM %s WHERE user_id = %s;";
    private final String SELECT_ALL_COMMAND = "SELECT * FROM %s";
    private final String UPDATE_COMMAND = "UPDATE %s SET (%s) WHERE id = %s;";
    private final String DELETE_COMMAND = "DELETE FROM %s WHERE id = %s";

    //queries
    public String generateInsertQuery(Persistable persistable) {
        Field[] fields = ReflectionUtils.getFields(persistable);
        String tableName = getTableName(persistable);
        String columns = getColumns(fields);
        String values = getValues(fields, persistable);
        return String.format(INSERT_COMMAND, tableName, columns, values);
    }

    public String generateUpdateQuery(Persistable persistable) {
        String tableName = getTableName(persistable);
        int id = ReflectionUtils.getId(persistable);
        Field[] fields = ReflectionUtils.getFields(persistable);
        String fieldsAndValues = getFieldValuePair(fields, persistable);
        return String.format(UPDATE_COMMAND, tableName, fieldsAndValues, id);
    }

    public String generateDeleteQuery(Persistable persistable) {
        String tableName = getTableName(persistable);
        int id = ReflectionUtils.getId(persistable);
        return String.format(DELETE_COMMAND, tableName, id);
    }

    public String generateSelectQuery(Persistable persistable) {
        String tableName = getTableName(persistable);
        return String.format(SELECT_ALL_COMMAND, tableName);
    }

    //others
    public String generateGerUserProjectRoles(int id) {
        //@TODO refactor to pass table name
        return String.format(SELECT_FROM_WHERE_USER_ID, USER_PROJECT_ROLES, id);
    }

    //helpers
    private String getTableName(Persistable persistable) {
        return persistable.getClass().getSimpleName().toLowerCase() + "s";
    }

    private String getColumns(Field[] fields) {
        return Arrays.stream(fields)
                .map(ReflectionUtils::getFieldName)
                .collect(Collectors.joining(", "));
    }

    private String getValues(Field[] fields, Persistable persistable) {
        return Arrays.stream(fields)
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        Object value = ReflectionUtils.getValue(persistable, field);
                        if (value instanceof Integer) {
                            return value.toString();
                        } else {
                            value = value != null ? "'" + value + "'" : "NULL";
                        }
                        return value.toString();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return "NULL";
                    }
                })
                .collect(Collectors.joining(", "));
    }

    private String getFieldValuePair(Field[] fields, Persistable persistable) {
        return Arrays.stream(fields)
                .filter(field -> !ReflectionUtils.getFieldName(field).equals("id"))
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        String fieldName = ReflectionUtils.getFieldName(field);
                        Object value = ReflectionUtils.getValue(persistable, field);
                        if (value instanceof Integer) {
                            return fieldName + " = " + value;
                        } else {
                            value = value != null ? "'" + value + "'" : "NULL";
                        }
                        return fieldName + " = " + value;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.joining(", "));
    }
}
