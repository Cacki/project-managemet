package org.example.model.project;

import org.example.model.document.Document;
import org.example.model.user.User;
import org.example.persistance.Persistable;

import java.util.Set;

public class Project extends ProjectBase implements Persistable {
    private Project(ProjectBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.creator = builder.creator;
        this.documents = builder.documents;
    }

    public static class ProjectBuilder extends ProjectBase {
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