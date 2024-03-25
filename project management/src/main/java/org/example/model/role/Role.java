package org.example.model.role;

public enum Role {
    ADMIN(1),
    MANAGER(2),
    HR(3),
    ENGINEER(4);

    private final int roleId;

    Role(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }
}
