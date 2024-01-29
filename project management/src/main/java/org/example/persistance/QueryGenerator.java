package org.example.persistance;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class QueryGenerator {
    public static String generateInsertQuery(Persistable persistable) {
        Field[] fields = persistable.getClass().getDeclaredFields();
        String tableName = persistable.getClass().getSimpleName().toLowerCase()
                .replace("dto", "s");

        String columns = Arrays.stream(fields)
                .map(Field::getName)
                .collect(Collectors.joining(", "));

        String values = Arrays.stream(fields)
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(persistable);
                        return value != null ? "'" + value.toString() + "'" : "NULL";
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return "NULL";
                    }
                })
                .collect(Collectors.joining(", "));

        return String.format("INSERT INTO %s (%s) VALUES (%s);", tableName, columns, values);
    }

    private String getTableName(String tableName) {
        return tableName.replace("DTO", "s");
    }
}
