package org.example.xml;

import org.example.persistance.Persistable;
import org.example.reflection.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.xml.XMLOperationsHelper.*;

public class SerializationManager {

    public static String serializeFile(List<Persistable> persistables) {
        String xmlName = persistables.getClass().getSimpleName();

        StringBuilder xmlBuilder = new StringBuilder(START_SYMBOL + xmlName + END_SYMBOL);

        for (Persistable persistable : persistables) {
            xmlBuilder.append(serializeObject(persistable));
        }

        xmlBuilder.append(CLOSE_SYMBOL + xmlName + END_SYMBOL);

        return xmlBuilder.toString();
    }

    public static String serializeObject(Persistable persistable) {
        StringBuilder xmlBuilder = new StringBuilder(START_SYMBOL + ReflectionUtils.getClassName(persistable) + END_SYMBOL);

        for (Field field : persistable.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = ReflectionUtils.getValue(persistable, field);
                String name = ReflectionUtils.getFieldName(field);

                xmlBuilder.append(START_SYMBOL)
                        .append(name)
                        .append(END_SYMBOL)
                        .append(value)
                        .append(CLOSE_SYMBOL)
                        .append(name)
                        .append(END_SYMBOL);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                //@TODO handle exception
            }
        }
        xmlBuilder.append(CLOSE_SYMBOL)
                .append(ReflectionUtils.getClassName(persistable))
                .append(END_SYMBOL);

        return xmlBuilder.toString();
    }

    public static <T extends Persistable> List<T> deserializeFile(String xml, Class<T> type) {
        List<T> objects = new ArrayList<>();

        String startTag = "<" + type.getSimpleName() + ">";
        String endTag = "</" + type.getSimpleName() + ">";
        Pattern pattern = Pattern.compile(startTag + "(.*?)" + endTag);
        Matcher matcher = pattern.matcher(xml);

        while (matcher.find()) {
            String objectXml = matcher.group(0);

            T obj = deserializeObject(objectXml, type);
            if (obj != null) {
                objects.add(obj);
            }
        }
        return objects;
    }

    public static <T extends Persistable> T deserializeObject(String xml, Class<T> type) {
        try {
            T obj = type.getDeclaredConstructor().newInstance();

            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                String name = ReflectionUtils.getFieldName(field);
                String fieldName = START_SYMBOL + name + END_SYMBOL;
                int startIndex = xml.indexOf(fieldName) + fieldName.length();
                int endIndex = xml.indexOf(CLOSE_SYMBOL + name + END_SYMBOL);
                String value = xml.substring(startIndex, endIndex);
                if (Persistable.class.isAssignableFrom(field.getType())) {
                    Class<?> type1 = field.getType();
                    //@TODO find user by id and set proper user/project/document etc
                    T obj1 = (T) type1.getDeclaredConstructor().newInstance();
                    obj1.setId(Integer.parseInt(value));
                    field.set(obj, obj1);
                } else {
                    // Convert string value to the type of the field and set it
                    field.set(obj, ReflectionUtils.convertStringToFieldType(value, field.getType()));
                }
            }

            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
