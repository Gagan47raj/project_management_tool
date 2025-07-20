package com.projectmanagement.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.projectmanagement.model.Task;

public interface TaskRepo  extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

	Page<Task> findByProjectId(Long projectId, Pageable pageable);

}
