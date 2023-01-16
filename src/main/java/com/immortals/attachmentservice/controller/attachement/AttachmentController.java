package com.immortals.attachmentservice.controller.attachement;

import com.immortals.attachmentservice.model.entity.Attachment;
import com.immortals.attachmentservice.model.payload.attachment.AttachmentPayload;
import com.immortals.attachmentservice.service.attachment.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }


    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveMetadata(@RequestBody Attachment attachment) {
        return attachmentService.saveMetadata(attachment);
    }

    @GetMapping(value = "/{userId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<Attachment> getAttachmentsPerUser(@PathVariable Long userId) {
        return attachmentService.getAttachmentPerUser(userId);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<Attachment> getAttachments() {
        return attachmentService.getAttachments();
    }

    @PutMapping(value = "update/{attachmentId}", consumes = "application/json")
    public String updateMetaData(@PathVariable Long attachmentId, @RequestBody AttachmentPayload attachmentPayload) {
        return attachmentService.updateMetadata(attachmentId, attachmentPayload);
    }

    @DeleteMapping("delete/{attachmentId}")
    public String deleteMetaData(@PathVariable Long attachmentId) {
        return attachmentService.deleteMetadata(attachmentId);
    }
}
