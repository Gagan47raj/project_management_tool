package com.projectmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectmanagement.dto.ProjectDTO;
import com.projectmanagement.model.Project;
import com.projectmanagement.service.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@PostMapping
	public Project createProject(@RequestBody ProjectDTO project)
	{
		return projectService.createProject(project);
	}
	
	@GetMapping
	public List<ProjectDTO> getAllProject()
	{
		return projectService.getAllProject()
				.stream()
				.map(projectService::convertToDTO)
				.toList();
	}
	
	@GetMapping("/{id}")
	public ProjectDTO getProjectById(@PathVariable("id") Long id)
	{
		Project project = projectService.getProjectById(id).orElseThrow();
		return projectService.convertToDTO(project);
	}
	
	@PutMapping("/{id}")
	public Project updateProject(@RequestBody ProjectDTO project, @PathVariable("id") Long id)
	{
		return projectService.updateProject(project, id);
	}
	
	@DeleteMapping("/{id}")
	public void deleteProject(@PathVariable("id") Long id)
	{
		projectService.deleteProject(id);
	}
}
