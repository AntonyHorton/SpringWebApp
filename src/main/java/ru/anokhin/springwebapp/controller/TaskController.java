package ru.anokhin.springwebapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.anokhin.springwebapp.Dto.Task;
import ru.anokhin.springwebapp.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //Create task
    @PostMapping("/tasks")
    public Task create(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    //Receiving all tasks
    @GetMapping("/tasks")
    public Iterable<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    //Receiving tasks by id
    @GetMapping("/tasks/{id}")
    public Task getById(@PathVariable Long id){
        return taskRepository.findById(id).orElse(null);
    }

    //Receiving  different types of tasks
    //values depending on whether the id supplied or not
    @GetMapping(value = {"/tasks", "/tasks/{id}"})
    public List<Task> getTasks(@PathVariable(required = false) Long id){
        if (id != null) {
            List<Task> tasks = new ArrayList<>();
            tasks.add(taskRepository.findById(id).orElse(null));
            return tasks;
        }
        return (List<Task>) taskRepository.findAll();
    }

    //Editing tasks by id
    @PutMapping("/tasks/{id}")
    public Task update(@PathVariable Long id,
                       @RequestBody Task task){
        task.setId(id);
        return taskRepository.save(task);
    }

    //Delete tasks by id
    @DeleteMapping("/tasks/{id}")
    public void delete(@PathVariable Long id){
        taskRepository.deleteById(id);
    }

    //Delete all tasks
    @DeleteMapping("/tasks")
    public void deleteAll(){
        taskRepository.deleteAll();
    }

    //Marking a task as completed
    @PatchMapping("/tasks/{id}")
    public void patchMethod(@PathVariable Long id,
                            @RequestBody Task task){
        if(task.isDone())
            taskRepository.markAsDone(id);
    }

    @PatchMapping("/tasks/{id}:mark-as-done")
    public void patchMethod(@PathVariable Long id){
        taskRepository.markAsDone(id);
    }
}
