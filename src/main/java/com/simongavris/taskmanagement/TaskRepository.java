package com.simongavris.taskmanagement;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID>{

}
