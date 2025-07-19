package com.projectmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectmanagement.dto.ProjectDTO;
import com.projectmanagement.model.Project;
import com.projectmanagement.repo.ProjectRepo;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepo projectRepo;
	
	public Project createProject(ProjectDTO projectDTO) {
		Project project = new Project();
		project.setName(projectDTO.getName());
		project.setDescription(projectDTO.getDescription());
		project.setDeadline(projectDTO.getDeadline());
		return projectRepo.save(project);
	}
	
	public List<Project> getAllProject()
	{
		return projectRepo.findAll();
	}
	
	public Optional<Project> getProjectById(Long id)
	{
		return projectRepo.findById(id);
	}
	
	public Project updateProject(ProjectDTO projectDTO, Long id)
	{
		Project project = projectRepo.findById(id).get();
		project.setName(projectDTO.getName());
		project.setDescription(projectDTO.getDescription());
		project.setDeadline(projectDTO.getDeadline());
		return projectRepo.save(project);
	}
	
	public void deleteProject(Long id)
	{
		projectRepo.deleteById(id);
	}
}












