package com.projectmanagement.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projectmanagement.model.Task;

public interface TaskRepo  extends JpaRepository<Task, Long> {

	Page<Task> findByProjectId(Long projectId, Pageable pageable);

}
