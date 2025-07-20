package com.projectmanagement.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
import com.projectmanagement.repo.TaskRepo;
import com.projectmanagement.service.TaskService;

import jakarta.persistence.criteria.Predicate;

@RestController
@RequestMapping("/api/task")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskRepo taskRepo;

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
	
	@GetMapping
	public List<TaskDTO> getFilteredAndSortedTasks(
	        @RequestParam(name = "status", required = false) String status,
	        @RequestParam(name = "priority", required = false) String priority,
	        @RequestParam(name = "assignedTo", required = false) String assignedTo,
	        @RequestParam(name = "projectId", required = false) Long projectId,
	        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
	        @RequestParam(name = "asc", defaultValue = "asc") String order
	) {
	    Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
	    Sort sort = Sort.by(direction, sortBy);

	    List<Task> tasks;

	    if (status != null || priority != null || assignedTo != null || projectId != null) {
	        tasks = taskRepo.findAll((root, query, cb) -> {
	            List<Predicate> predicates = new ArrayList<>();

	            if (status != null) predicates.add(cb.equal(root.get("status"), status));
	            if (priority != null) predicates.add(cb.equal(root.get("priority"), priority));
	            if (assignedTo != null) predicates.add(cb.equal(root.get("assignedTo"), assignedTo));
	            if (projectId != null) predicates.add(cb.equal(root.get("project").get("id"), projectId));

	            return cb.and(predicates.toArray(new Predicate[0]));
	        }, sort);
	    } else {
	        tasks = taskRepo.findAll(sort);
	    }

	    return tasks.stream()
	                .map(taskService::convertToDTO)
	                .toList();
	}

}
