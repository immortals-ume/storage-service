package com.immortals.attachmentservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;


@Getter
@Setter
@Entity
@Table(name = "attachment")
public class Attachment implements Serializable {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "attachment_id", nullable = false)
    private Long attachmentId;


    @Column(name = "file_name", nullable = false, length = 4000)
    private String fileName;


    @Column(name = "attachment_type", nullable = false, length = 4000)
    private String attachmentType;


    @Column(name = "access_url", nullable = false, length = 4000)
    private String accessUrl;


    @Column(name = "source", length = 4000)
    private String source;


    @Column(name = "uploaded_on")
    private Timestamp uploadedOn;


    @Column(name = "size", nullable = false)
    private Long size;


    @Column(name = "uploaded_by")
    private Long uploadedBy;


    @Column(name = "created_date")
    private Timestamp createdDate;


    @Column(name = "updated_by")
    private Long updatedBy;


    @Column(name = "updated_date")
    private Timestamp updatedDate;


    @Column(name = "is_deleted")
    private Boolean isDeleted;


    @Column(name = "is_active")
    private Boolean isActive;


    @Column(name = "cache_control")
    private Boolean cacheControl;


    @Column(name = "content_type", length = 4000)
    private String contentType;
}
