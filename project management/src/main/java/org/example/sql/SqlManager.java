package org.example.sql;

import org.example.model.Status.OperationStatus;
import org.example.persistance.Persistable;
import org.example.persistance.PersistableManager;

import java.sql.SQLException;
import java.sql.Statement;
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
        LOGGER.log(Level.INFO, String.format("Insert query: %s", insertQuery));
        Statement statement = null;
        try {
            statement = SQLConnection.connection.createStatement();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, String.format("SQL exception occured: %s", e));
            return OperationStatus.FAILURE;
        }
        if (statement != null) {
            try {
                statement.executeUpdate(insertQuery);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, String.format("SQL exception occured: %s", e));
                return OperationStatus.FAILURE;
            } finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.WARNING, String.format("SQL exception occured: %s", e));
                }
            }
        }
        return OperationStatus.SUCCESS;
    }

    @Override
    public <T extends Persistable> List<T> read(Class<T> type) {
        return List.of();
    }

    @Override
    public OperationStatus update(Persistable persistable) {
        return null;
    }

    @Override
    public OperationStatus delete(Persistable persistable) {
        return null;
    }
}
