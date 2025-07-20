package com.projectmanagement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projectmanagement.dto.TaskDTO;
import com.projectmanagement.model.Task;
import com.projectmanagement.service.TaskService;

@RestController
@RequestMapping("/api/task")
public class TaskController {
	
	@Autowired
	private TaskService taskService;

	@PostMapping
	private TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
		Task task = taskService.createTask(taskDTO);
		return taskService.convertToDTO(task);
	} 
	
	@GetMapping("/{id}")
	private TaskDTO getTaskById(@PathVariable("id") Long id) {
	    Task task = taskService.getTaskById(id).orElseThrow();
	    return taskService.convertToDTO(task);
	}

	
	@PutMapping("/{id}")
	private TaskDTO updateTask(@RequestBody TaskDTO taskDTO, @PathVariable("id") Long id) {
		Task task = taskService.updateTask(taskDTO, id);
		return taskService.convertToDTO(task);
	}
	
	@DeleteMapping("/{id}")
	private void deleteTask(@PathVariable("id") Long id) {
		taskService.deleteTask(id);
	}
	
	@GetMapping("/project/{id}")
	public Page<TaskDTO> getTasksByProjectId(
	        @PathVariable("id") Long projectId,
	        @RequestParam(name = "page",defaultValue = "0") int page,
	        @RequestParam(name = "size",defaultValue = "5") int size,
	        @RequestParam(name = "dueDate",defaultValue = "dueDate") String sortBy) {
	    return taskService.getTasks(projectId, page, size, sortBy)
	                      .map(taskService::convertToDTO);
	}

	
}
