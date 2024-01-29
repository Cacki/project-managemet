package org.example.reflection;

import org.example.persistance.Persistable;

import java.lang.reflect.Field;

public class ReflectionUtils {
    public static Object getValue(Persistable persistable, Field field) throws IllegalAccessException {
        Object value = field.get(persistable);
        if (Persistable.class.isAssignableFrom(field.getType())) {
            Persistable persistableObject = (Persistable) value;
            value = persistableObject.getId();
        }
        return value;
    }

    public static String getFieldName(Field field) {
        if (Persistable.class.isAssignableFrom(field.getType())) {
            return field.getType().getSimpleName().toLowerCase() + "_id";
        } else {
            return field.getName();
        }
    }

    public static Object convertStringToFieldType(String value, Class<?> fieldType) {
        if (fieldType.equals(String.class)) {
            return value;
        } else if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
            return Integer.parseInt(value);
        } else if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
            return Long.parseLong(value);
        } else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
            return Double.parseDouble(value);
        } else {
            // @TODO concert objects of persistable
            return value;
        }
    }

    public static int getId(Persistable persistable) {
        return persistable.getId();
    }

    public static Field[] getFields(Persistable persistable) {
        return persistable.getClass().getDeclaredFields();
    }

    public static String getClassName(Persistable persistable) {
        return persistable.getClass().getSimpleName();
    }
}
