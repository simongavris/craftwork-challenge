package com.simongavris.taskmanagement.model;



import com.simongavris.taskmanagement.util.Priority;
import com.simongavris.taskmanagement.util.Status;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name= "tasks")
@EntityListeners(AuditingEntityListener.class)
public class Task {

    @Id
    //@JsonIgnore
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    private String title;

    @Column(nullable = false, updatable = false)
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

    private int queueId = 0;

    public Task(){
        System.out.println("Constructor called");
        System.out.println(TaskQueue.getInstance().size());
        this.queueId = TaskQueue.getInstance().size() +1;
        TaskQueue.getInstance().add(this);
    }


    //Getter:
    public UUID getId() {
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

    public Priority getPriority() {
        return priority;
    }


    public Status getStatus() {
        return status;
    }


    public String getDescription() {
        return description;
    }

    public int getQueueId() {
        return queueId;
    }




    //Setter:


    public void setPriority(Priority priority) {
        this.priority = priority;
        this.updatedAt = new Date();
    }


    public void setStatus(Status status) {
        if(status != null){
            if (status.equals(Status.OPEN) && this.resolvedAt != null)
                this.resolvedAt = null;
            if (status.equals(Status.DONE))
                this.resolvedAt = new Date();

            this.status = status;
            this.updatedAt = new Date();
        }
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = new Date();
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = new Date();
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

}
