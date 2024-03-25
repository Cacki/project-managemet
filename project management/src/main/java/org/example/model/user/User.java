package org.example.model.user;

import org.example.model.project.Project;
import org.example.model.role.Role;
import org.example.persistance.Persistable;

import java.util.Map;
import java.util.Set;

public class User implements Persistable {
    private int id;
    private String username;
    private Map<Project, Set<Role>> rolesOnProjects;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(int id, String username, Map<Project, Set<Role>> rolesOnProjects) {
        this.id = id;
        this.username = username;
        this.rolesOnProjects = rolesOnProjects;
    }

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<Project, Set<Role>> getRolesOnProjects() {
        return rolesOnProjects;
    }

    public void setRolesOnProjects(Map<Project, Set<Role>> rolesOnProjects) {
        this.rolesOnProjects = rolesOnProjects;
    }
}
