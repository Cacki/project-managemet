package org.example.model.document;

import org.example.model.project.Project;
import org.example.model.user.User;
import org.example.persistance.Persistable;

public class Document implements Persistable {
    private int id;
    private String title;
    private String topic;
    private String content;
    private String description;
    private User creator;
    private Project project;

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Document() {
    }

    private Document(DocumentBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.topic = builder.topic;
        this.content = builder.content;
        this.description = builder.description;
        this.creator = builder.creator;
        this.project = builder.project;
    }

    public static class DocumentBuilder {
        private int id;
        private String title;
        private String topic;
        private String content;
        private String description;
        private User creator;
        private Project project;

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
