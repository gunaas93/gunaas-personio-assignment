package com.gunaas.rest.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity<T> {
    @Id
    @GeneratedValue
    @Column(unique = true, updatable = false)
    private long id;

    @CreatedDate
    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;

    @LastModifiedDate
    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;

    @LastModifiedBy
    @Column(name = "updated_by")
    private Long updatedBy;

    @Nullable
    public long getId() {
        return this.id;
    }

    public void setId(@Nullable long var1) {
        this.id = var1;
    }

    @Nullable
    public LocalDateTime getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(@Nullable LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Nullable
    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(@Nullable Long var1) {
        this.createdBy = var1;
    }

    @Nullable
    public LocalDateTime getUpdatedTime() {
        return this.updatedTime;
    }

    public void setUpdatedTime(@Nullable LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Nullable
    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(@Nullable Long updatedBy) {
        this.updatedBy = updatedBy;
    }
}