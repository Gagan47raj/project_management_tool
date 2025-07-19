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
	public List<Project> getAllProject()
	{
		return projectService.getAllProject();
	}
	
	@GetMapping("/{id}")
	public Project getProjectById(@PathVariable Long id)
	{
		return projectService.getProjectById(id).orElseThrow();
	}
	
	@PutMapping("/{id}")
	public Project updateProject(@RequestBody ProjectDTO project, @PathVariable Long id)
	{
		return projectService.updateProject(project, id);
	}
	
	@DeleteMapping("/{id}")
	public void deleteProject(@PathVariable Long id)
	{
		projectService.deleteProject(id);
	}
}
