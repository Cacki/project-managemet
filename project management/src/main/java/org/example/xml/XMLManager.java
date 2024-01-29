package org.example.xml;

import org.example.model.Status.OperationStatus;
import org.example.persistance.Persistable;
import org.example.persistance.PersistableManager;
import org.example.reflection.ReflectionUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.example.xml.SerializationManager.deserializeFile;
import static org.example.xml.SerializationManager.deserializeObject;
import static org.example.xml.XMLOperationsHelper.*;

public class XMLManager implements PersistableManager {
    private static String FILE_PATH_BASE = "resources/";

    public static String setFilePath(Persistable persistable) {
        return FILE_PATH_BASE += ReflectionUtils.getClassName(persistable) + ".xml";
    }

    public static String getRootTag(Persistable persistable) {
        return ReflectionUtils.getClassName(persistable) + "s";
    }

    public static String getStartOfFileTag(String rootTag) {
        return START_SYMBOL + rootTag + END_SYMBOL;
    }

    public static String getEndOfFileTag(String rootTag) {
        return CLOSE_SYMBOL + rootTag + END_SYMBOL;
    }

    @Override
    public OperationStatus create(Persistable persistable) {
        String path = setFilePath(persistable);
        String rootTagName = getRootTag(persistable);
        String startOfFileTag = getStartOfFileTag(rootTagName);
        String endOfFileTag = getEndOfFileTag(rootTagName);
        String serialized = SerializationManager.serializeObject(persistable);

        try (RandomAccessFile file = new RandomAccessFile(path, "rw")) {
            boolean endsWithDocumentsTag = checkEndsWithDocumentsTag(file, endOfFileTag);

            if (endsWithDocumentsTag) {
                moveToPreviousLine(file);
            } else {
                file.writeBytes(startOfFileTag + "\n");
            }
            file.writeBytes(serialized + "\n");
            file.writeBytes(endOfFileTag);
        } catch (IOException e) {
            e.printStackTrace();
            return OperationStatus.FAILURE;
        }
        return OperationStatus.SUCCESS;
    }


    @Override
    public <T extends Persistable> List<T> read(Class<T> type) {
        String filePath = FILE_PATH_BASE + type.getSimpleName() + ".xml";
        List<T> deserializedPersistables = new ArrayList<>();

        try {
            // Read the XML file
            StringBuilder xmlContent = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                xmlContent.append(line).append("\n");
            }
            reader.close();
            deserializedPersistables = deserializeFile(xmlContent.toString(), type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deserializedPersistables;
    }

    @Override
    public OperationStatus update(Persistable persistable) {
        String filePath = setFilePath(persistable);
        int persistableIdToRemove = persistable.getId();
        String objectTag = persistable.getClass().getSimpleName();
        String startOfPersistableTag = getStartOfFileTag(objectTag);
        String endOfPersistableTag = getEndOfFileTag(objectTag);

        try {
            // Read the XML file
            StringBuilder xmlContent = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                xmlContent.append(line).append("\n");
            }
            reader.close();
            int xmlContentSize = xmlContent.length();

            // Find and remove the Document element with the specified ID
            String xmlString = xmlContent.toString();
            int startIndex = xmlString.indexOf(startOfPersistableTag);
            while (startIndex != -1) {
                int endIndex = xmlString.indexOf(endOfPersistableTag, startIndex);
                String xmlPersistable = xmlString.substring(startIndex, endIndex + endOfPersistableTag.length());
                if (xmlPersistable.contains(OPEN_ID + persistableIdToRemove + CLOSE_ID)) {
                    Persistable deserializedPersistable = deserializeObject(xmlPersistable, persistable.getClass());
                    Field[] fields = ReflectionUtils.getFields(persistable);
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object newValue = ReflectionUtils.getValue(persistable, field);
                        Object oldValue = ReflectionUtils.getValue(deserializedPersistable, field);
                        if (!oldValue.equals(newValue)) {
                            String startFieldTag = getStartOfFileTag(ReflectionUtils.getFieldName(field));
                            String endFieldTag = getEndOfFileTag(ReflectionUtils.getFieldName(field));
                            int contentStartIndex = xmlContent.indexOf(startFieldTag) + startFieldTag.length();
                            int contentEndIndex = xmlContent.indexOf(endFieldTag, contentStartIndex);
                            xmlContent.replace(contentStartIndex, contentEndIndex, newValue.toString());
                        }
                    }
                    break;
                }
                startIndex = xmlString.indexOf(startOfPersistableTag, endIndex);
            }
            if (xmlContentSize == xmlContent.length()) {
                return OperationStatus.NOT_FOUND;
            }
            // Write the modified XML back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(xmlContent.toString());
            writer.close();
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
            return OperationStatus.FAILURE;
        }
        return OperationStatus.SUCCESS;
    }

    @Override
    public OperationStatus delete(Persistable persistable) {
        String filePath = setFilePath(persistable);
        int persistableIdToRemove = persistable.getId();
        String objectTag = persistable.getClass().getSimpleName();
        String startOfPersistableTag = getStartOfFileTag(objectTag);
        String endOfPersistableTag = getEndOfFileTag(objectTag);

        try {
            // Read the XML file
            StringBuilder xmlContent = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                xmlContent.append(line).append("\n");
            }
            reader.close();
            int xmlContentSize = xmlContent.length();

            // Find and remove the Document element with the specified ID
            String xmlString = xmlContent.toString();
            int startIndex = xmlString.indexOf(startOfPersistableTag);
            while (startIndex != -1) {
                int endIndex = xmlString.indexOf(endOfPersistableTag, startIndex);
                String xmlPersistable = xmlString.substring(startIndex, endIndex + endOfPersistableTag.length());
                if (xmlPersistable.contains(OPEN_ID + persistableIdToRemove + CLOSE_ID)) {
                    int lineStartIndex = xmlString.lastIndexOf("\n", startIndex) + 1;
                    int lineEndIndex = xmlString.indexOf("\n", endIndex) + 1;
                    xmlContent.delete(lineStartIndex, lineEndIndex);
                    break;
                }
                startIndex = xmlString.indexOf(startOfPersistableTag, endIndex);
            }
            if (xmlContentSize == xmlContent.length()) {
                return OperationStatus.NOT_FOUND;
            }
            // Write the modified XML back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(xmlContent.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return OperationStatus.FAILURE;
        }
        return OperationStatus.SUCCESS;
    }
}
