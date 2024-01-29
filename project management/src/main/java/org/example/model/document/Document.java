package org.example.model.document;

import org.example.model.project.Project;
import org.example.model.user.User;
import org.example.persistance.Persistable;

public class Document extends DocumentBase implements Persistable {

    private Document(DocumentBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.topic = builder.topic;
        this.content = builder.content;
        this.description = builder.description;
        this.creator = builder.creator;
        this.project = builder.project;
    }

    public static class DocumentBuilder extends DocumentBase {
        public DocumentBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public DocumentBuilder title(String title) {
            this.title = title;
            return this;
        }

        public DocumentBuilder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public DocumentBuilder content(String content) {
            this.content = content;
            return this;
        }

        public DocumentBuilder description(String description) {
            this.description = description;
            return this;
        }

        public DocumentBuilder creator(User creator) {
            this.creator = creator;
            return this;
        }

        public DocumentBuilder project(Project project) {
            this.project = project;
            return this;
        }

        // Build method to create an instance of Document
        public Document build() {
            return new Document(this);
        }
    }
}
