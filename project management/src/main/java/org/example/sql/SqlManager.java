package org.example.sql;

import org.example.model.Status.OperationStatus;
import org.example.persistance.Persistable;
import org.example.persistance.PersistableManager;
import org.example.reflection.ReflectionUtils;
import org.example.utils.SQLMessages;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlManager implements PersistableManager {
    private static final Logger LOGGER = Logger.getLogger(SqlManager.class.getName());
    private final QueryGenerator queryGenerator;

    public SqlManager(QueryGenerator queryGenerator) {
        this.queryGenerator = queryGenerator;
    }

    @Override
    public OperationStatus create(Persistable persistable) {
        String insertQuery = queryGenerator.generateInsertQuery(persistable);
        LOGGER.log(Level.INFO, String.format(SQLMessages.QUERY_INFO, insertQuery));
        return executeStatement(insertQuery);
    }

    @Override
    public <T extends Persistable> List<T> read(Class<T> type) {
        String selectQuery = queryGenerator.generateSelectQuery(type.getSimpleName());
        LOGGER.log(Level.INFO, String.format(SQLMessages.QUERY_INFO, selectQuery));
        return executeSelectStatement(selectQuery, type);
    }

    @Override
    public OperationStatus update(Persistable persistable) {
        String updateQuery = queryGenerator.generateUpdateQuery(persistable);
        LOGGER.log(Level.INFO, String.format(SQLMessages.QUERY_INFO, updateQuery));
        return executeStatement(updateQuery);
    }

    @Override
    public OperationStatus delete(Persistable persistable) {
        String deleteQuery = queryGenerator.generateDeleteQuery(persistable);
        LOGGER.log(Level.INFO, String.format(SQLMessages.QUERY_INFO, deleteQuery));
        return executeStatement(deleteQuery);
    }

    private OperationStatus executeStatement(String query) {
        Statement statement;
        try {
            statement = SQLConnection.connection.createStatement();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, String.format(SQLMessages.SQL_EXCEPTION_OCCURED, e));
            return OperationStatus.FAILURE;
        }
        if (statement != null) {
            try {
                statement.executeUpdate(query);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, String.format(SQLMessages.SQL_EXCEPTION_OCCURED, e));
                return OperationStatus.FAILURE;
            } finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.WARNING, String.format(SQLMessages.SQL_EXCEPTION_OCCURED, e));
                }
            }
        }
        return OperationStatus.SUCCESS;
    }

    private <T extends Persistable> List<T> executeSelectStatement(String query, Class<T> clazz) {
        Statement statement;
        ResultSet resultSet;
        List<T> result = new ArrayList<>();
        try {
            statement = SQLConnection.connection.createStatement();
            resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                T obj = clazz.getDeclaredConstructor().newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String columnName = ReflectionUtils.getFieldName(field);
                    Object columnValue = resultSet.getObject(columnName);

                    if (Persistable.class.isAssignableFrom(field.getType())) {
                        int id = (int) columnValue;
                        Persistable relatedObject = (Persistable) field.getType().getDeclaredConstructor().newInstance();
                        relatedObject.setId(id);
                        field.set(obj, relatedObject);
                    } else {
                        Object value = ReflectionUtils.convertStringToFieldType(columnValue.toString(), field.getType());
                        field.set(obj, value);
                    }
                }
                result.add(obj);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, String.format(SQLMessages.SQL_EXCEPTION_OCCURED, e));
        }
        return result;

    }
}
