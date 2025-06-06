package ch.bbw.pojo_backend.model;

public class Task {
    private long id;
    private String description;
    private boolean completed;

    // Standard-Konstruktor (wird von Spring/JSON benötigt)
    public Task() {
    }

    // Konstruktor mit allen Feldern
    public Task(long id, String description, boolean completed) {
        this.id = id;
        this.description = description;
        this.completed = completed;
    }

    // Getter und Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // Optional: toString(), hilft beim Debugging
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                '}';
    }
}
