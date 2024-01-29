package org.example.model.Status;

public enum OperationStatus {
    SUCCESS("Operation success!"),
    NOT_FOUND("Object not found!"),
    FAILURE("Operation failure!");

    private final String displayName;

    OperationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
