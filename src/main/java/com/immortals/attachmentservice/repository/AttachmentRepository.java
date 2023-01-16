package com.immortals.attachmentservice.repository;

import com.immortals.attachmentservice.model.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository< Attachment,Long >{
}
