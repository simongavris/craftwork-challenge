package com.simongavris.taskmanagement.model;


import com.simongavris.taskmanagement.util.Priority;
import com.simongavris.taskmanagement.util.Status;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "tasks")
@EntityListeners(AuditingEntityListener.class)
public class Task {
    /**
     * Primary Key of the Table.
     * Used to be the UUID but for simplyifing the FIFO Queue
     * Integer was chosen.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * for better and more save destinction bewteen Elements
     * Random UUIDs are contained by the Objects
     */
    private UUID uuid;

    @NotNull
    private String title;

    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date resolvedAt;

    private Priority priority = Priority.MEDIUM;

    private Status status = Status.OPEN;

    private String description;


    public Task() {
        this.uuid = UUID.randomUUID();
    }

    public Task(@NotNull String title, Priority priority, Status status, String description) {
        this.title = title;
        this.priority = priority;
        this.status = status;
        this.description = description;
        this.uuid = UUID.randomUUID();
    }

    //Getter:
    public Integer getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getResolvedAt() {
        return resolvedAt;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Setter of field Title
     * when new Title is set, updatedAt gets updated.
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = new Date();
    }

    public Priority getPriority() {
        return priority;
    }

    /**
     * Setter of field Priority
     * when new priority is chosen, updatedAt gets updated.
     *
     * @param priority
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
        this.updatedAt = new Date();
    }

    public Status getStatus() {
        return status;
    }


    //Setter:

    /**
     * Setter of field Status
     * when new status is chosen, updatedAt gets updated.
     * when status is set to "Done", resolvedAt gets set.
     * when statusu is set from "Done" to something else,
     * resolveAt gets unset (null).
     *
     * @param status
     */
    public void setStatus(Status status) {
        if (status != null) {
            if (status.equals(Status.OPEN) && this.resolvedAt != null)
                this.resolvedAt = null;
            if (status.equals(Status.DONE))
                this.resolvedAt = new Date();

            this.status = status;
            this.updatedAt = new Date();
        }
    }

    public String getDescription() {
        return description;
    }

    /**
     * Setter of field Description
     * when new Description is set, updatedAt gets updated.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = new Date();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String toString() {
        return "Id: " + this.id +
                "\nTitle: " + this.title;
    }

}
