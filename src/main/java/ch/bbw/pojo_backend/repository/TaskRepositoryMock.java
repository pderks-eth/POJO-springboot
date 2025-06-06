package ch.bbw.pojo_backend.repository;

import ch.bbw.pojo_backend.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryMock {

    private final List<Task> tasks;

    public TaskRepositoryMock() {
        // Beispiel-Daten (fünf Tasks) – direkt beim Start verfügbar
        tasks = new ArrayList<>() {{
            add(new Task(1, "Lunch with Teo", true));
            add(new Task(2, "Read modern Java recipes", false));
            add(new Task(3, "Change GUI on Tasks", true));
            add(new Task(4, "Business Logic", false));
            add(new Task(5, "Lunch with Jane", false));
        }};
    }

    // 1. Alle Tasks zurückgeben
    public List<Task> getTasks() {
        return tasks;
    }

    // 2. Einzelnen Task nach ID suchen
    public Optional<Task> getTask(long id) {
        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }

    // 3. Neuen Task anlegen (ID wird automatisch generiert: max(id) + 1)
    public Task addTask(Task newTask) {
        long nextId = tasks.stream()
                .map(Task::getId)
                .max(Comparator.naturalOrder())
                .orElse(0L) + 1;
        newTask.setId(nextId);
        tasks.add(newTask);
        return newTask;
    }

    // 4. Task löschen nach ID (gibt true zurück, falls erfolgreich gefunden und entfernt)
    public boolean deleteTask(long id) {
        return tasks.removeIf(t -> t.getId() == id);
    }

    // 5. Ganze Task-Liste löschen
    public void clearTasks() {
        tasks.clear();
    }

    // 6. Bestehenden Task (vollständig) updaten – nur, wenn er existiert: Felder überschreiben
    public boolean updateTask(Task updatedTask) {
        Optional<Task> existing = getTask(updatedTask.getId());
        if (existing.isEmpty()) {
            return false;
        }
        Task t = existing.get();
        t.setDescription(updatedTask.getDescription());
        t.setCompleted(updatedTask.isCompleted());
        return true;
    }
}
