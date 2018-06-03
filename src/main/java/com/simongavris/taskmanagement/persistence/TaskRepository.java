package com.simongavris.taskmanagement.persistence;


import com.simongavris.taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{

    /**
     * Method Query to find Task by Uuid.
     * Even though the Database-Structure does not require it,
     * Uuids should obviously be unique.
     * @param uuid of the task that should be returned
     * @return Optional containing the Task
     */
    Optional<Task> findByUuid(UUID uuid);

}
