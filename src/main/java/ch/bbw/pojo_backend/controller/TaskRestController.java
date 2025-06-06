package ch.bbw.pojo_backend.controller;

import ch.bbw.pojo_backend.model.Task;
import ch.bbw.pojo_backend.repository.TaskRepositoryMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api") // Basis-Pfad für alle Endpunkte
public class TaskRestController {

    private final TaskRepositoryMock tasksRepository;

    // Konstruktor-Injektion (Spring injectet automatisch TaskRepositoryMock)
    public TaskRestController(TaskRepositoryMock tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    // 2.3.1: Alle Tasks laden
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasks() {
        List<Task> all = tasksRepository.getTasks();
        return ResponseEntity.ok(all); // 200 OK + Liste im Body
    }

    // 2.3.2: Einzelner Task nach ID laden
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable long id) {
        Optional<Task> task = tasksRepository.getTask(id);
        return task
                .map(ResponseEntity::ok)                   // 200 OK + Task im Body
                .orElseGet(() -> ResponseEntity.notFound().build()); // 404 NOT FOUND
    }

    // 2.3.3: Task hinzufügen (POST)
    @PostMapping("/tasks")
    public ResponseEntity<Task> addTask(@RequestBody Task newTask) {
        Task created = tasksRepository.addTask(newTask);
        // 201 CREATED + neu angelegter Task im Body
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // 2.3.4a: Task komplett ersetzen (PUT)
    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable long id,
            @RequestBody Task updatedTask) {

        // ID aus PathVariable setzen (falls im JSON nicht vorhanden oder abweichend)
        updatedTask.setId(id);

        boolean existed = tasksRepository.updateTask(updatedTask);
        if (!existed) {
            return ResponseEntity.notFound().build(); // 404 NOT FOUND, wenn keine solche ID existierte
        }
        Task t = tasksRepository.getTask(id).get();
        return ResponseEntity.ok(t); // 200 OK + aktualisierter Task
    }

    // 2.3.4b: Task teilweise ändern (PATCH)
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<Task> patchTask(
            @PathVariable long id,
            @RequestBody Map<String, Object> updates) {
        Optional<Task> existing = tasksRepository.getTask(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 NOT FOUND
        }

        Task t = existing.get();
        // Nur die Felder verändern, die übergeben werden:
        if (updates.containsKey("description")) {
            Object desc = updates.get("description");
            if (desc instanceof String) {
                t.setDescription((String) desc);
            }
        }
        if (updates.containsKey("completed")) {
            Object comp = updates.get("completed");
            if (comp instanceof Boolean) {
                t.setCompleted((Boolean) comp);
            }
        }
        // t steht nun auf dem neuesten Stand
        return ResponseEntity.ok(t); // 200 OK + gepatchter Task
    }

    // 2.3.5: Einzelnen Task löschen
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        boolean removed = tasksRepository.deleteTask(id);
        if (!removed) {
            return ResponseEntity.notFound().build(); // 404 NOT FOUND
        }
        return ResponseEntity.noContent().build(); // 204 NO CONTENT (Löschen erfolgreich)
    }

    // 2.3.6: Alle Tasks löschen
    @DeleteMapping("/tasks")
    public ResponseEntity<Void> clearAllTasks() {
        tasksRepository.clearTasks();
        return ResponseEntity.noContent().build(); // 204 NO CONTENT
    }
}
