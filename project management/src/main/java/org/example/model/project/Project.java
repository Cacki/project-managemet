package org.example.model.project;

import org.example.model.document.Document;
import org.example.model.user.User;
import org.example.persistance.Persistable;

import java.util.Set;

public class Project implements Persistable {
    private int id;
    private String name;
    private String description;
    private User creator;
    private Set<Document> documents;

    public Project() {
    }

    private Project(ProjectBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.creator = builder.creator;
        this.documents = builder.documents;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public static class ProjectBuilder {
        private int id;
        private String name;
        private String description;
        private User creator;
        private Set<Document> documents;

        public ProjectBuilder(int id) {
            this.id = id;
        }

        public ProjectBuilder(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public ProjectBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProjectBuilder creator(User creator) {
            this.creator = creator;
            return this;
        }

        public ProjectBuilder documents(Set<Document> documents) {
            this.documents = documents;
            return this;
        }

        public Project build() {
            return new Project(this);
        }

    }
}