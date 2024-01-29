package org.example.persistance;

import org.example.model.Status.OperationStatus;

import java.io.FileNotFoundException;
import java.util.List;

public interface PersistableManager {
    OperationStatus create(Persistable persistable) throws FileNotFoundException;

    <T extends Persistable> List<T> read(Class<T> type);

    OperationStatus update(Persistable persistable);

    OperationStatus delete(Persistable persistable);
}
