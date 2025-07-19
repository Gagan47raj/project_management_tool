package com.projectmanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectmanagement.model.Project;

public interface ProjectRepo extends JpaRepository<Project, Long> {

}
