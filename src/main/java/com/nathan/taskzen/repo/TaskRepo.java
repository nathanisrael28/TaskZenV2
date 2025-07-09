package com.nathan.taskzen.repo;

import com.nathan.taskzen.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Repo layer is also known as DAO(Data Access Object)
public interface TaskRepo extends JpaRepository<TaskEntity, Long> {

}
