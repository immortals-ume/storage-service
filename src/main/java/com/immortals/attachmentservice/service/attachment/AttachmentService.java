package com.immortals.attachmentservice.service.attachment;


import com.immortals.attachmentservice.model.entity.Attachment;
import com.immortals.attachmentservice.model.payload.attachment.AttachmentPayload;

import java.util.List;

/**
 * Attachment Service Interface defines create , update , get , getById, delete can be performed on the attachments
 */
public interface AttachmentService {

    String saveMetadata(Attachment attachment);

    List<Attachment> getAttachmentPerUser(Long userId);

    List<Attachment> getAttachments();

    String updateMetadata(Long attachmentId, AttachmentPayload attachmentPayload);

    String deleteMetadata(Long attachmentId);
}
