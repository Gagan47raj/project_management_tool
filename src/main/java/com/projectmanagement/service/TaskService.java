package com.projectmanagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.projectmanagement.dto.TaskDTO;
import com.projectmanagement.model.Project;
import com.projectmanagement.model.Task;
import com.projectmanagement.repo.ProjectRepo;
import com.projectmanagement.repo.TaskRepo;

@Service
public class TaskService {

	@Autowired
	private TaskRepo taskRepo;
	
	@Autowired
	private ProjectRepo projectRepo;
	
	public Task createTask(TaskDTO taskDTO) {
		Project project = projectRepo.findById(taskDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

Task task = new Task();
task.setTitle(taskDTO.getTitle());
task.setDescription(taskDTO.getDescription());
task.setDueDate(taskDTO.getDueDate());
task.setPriority(taskDTO.getPriority());
task.setStatus(taskDTO.getStatus());
task.setAssignedTo(taskDTO.getAssignedTo());
task.setProject(project);

return taskRepo.save(task);
	}
	
	public Page<Task> getTasks(Long projectId,int page, int size, String sortBy) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		return taskRepo.findByProjectId(projectId, pageable);
	}
	
	public Optional<Task> getTaskById(Long id)
	{
		return taskRepo.findById(id);
	}
	
	public Task updateTask(TaskDTO taskDTO, Long id)
	{
		Task task = taskRepo.findById(id).orElseThrow();
		Project project = projectRepo.findById(taskDTO.getProjectId()).orElseThrow();
		
		task.setTitle(taskDTO.getTitle());
		task.setDescription(taskDTO.getDescription());
		task.setDueDate(taskDTO.getDueDate());
		task.setPriority(taskDTO.getPriority());
		task.setStatus(taskDTO.getStatus());
		task.setAssignedTo(taskDTO.getAssignedTo());
		task.setProject(project);
		return taskRepo.save(task);
	}
	
	public TaskDTO convertToDTO(Task task) {
	    TaskDTO dto = new TaskDTO();
	    dto.setTitle(task.getTitle());
	    dto.setDescription(task.getDescription());
	    dto.setDueDate(task.getDueDate());
	    dto.setPriority(task.getPriority());
	    dto.setStatus(task.getStatus());
	    dto.setAssignedTo(task.getAssignedTo());
	    dto.setProjectId(task.getProject().getId());
	    return dto;
	}

	
	public void deleteTask(Long id)
	{
		taskRepo.deleteById(id);
	}
}







