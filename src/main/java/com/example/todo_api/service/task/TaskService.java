package com.example.todo_api.service.task;


import java.util.List;
import java.util.stream.Collectors;
import com.example.todo_api.repository.task.TaskRecord;
import com.example.todo_api.repository.task.TaskRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskEntity find(long taskId) {
        return taskRepository.select(taskId)
                .map(record -> new TaskEntity(record.getId(), record.getTitle()))
                .orElseThrow(() -> new TaskEntityNotFoundException(taskId));
    }

    public List<TaskEntity> find(int limit, Long offset) {
        return taskRepository.selectList(limit, offset)
                .stream()
                .map(record -> new TaskEntity(record.getId(), record.getTitle()))
                .collect(Collectors.toList());
    }


    public TaskEntity create(@NotNull String title) {
        var record = new TaskRecord(null, title);
        taskRepository.insert(record);
        return new TaskEntity(record.getId(), record.getTitle());
    }

    public TaskEntity update(Long taskId, @NotNull @Size(min = 1, max = 256) String title) {
        taskRepository.select(taskId).orElseThrow(() -> new TaskEntityNotFoundException(taskId));
        taskRepository.update(new TaskRecord(taskId, title));
        return find(taskId);
    }

    public void delete(Long taskId) {
        taskRepository.select(taskId).orElseThrow(() -> new TaskEntityNotFoundException(taskId));
        taskRepository.delete(taskId);
    }
}