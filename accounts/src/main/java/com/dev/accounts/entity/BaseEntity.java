package com.dev.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {

    @CreatedDate
    @Column(name = "created_at",updatable = false)
    private LocalDateTime created_at;

    @CreatedBy
    @Column(name = "created_by")
    private String created_by;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updated_at;

    @LastModifiedBy
    @Column(name = "updated_by", insertable = false)
    private String updated_by;

}
