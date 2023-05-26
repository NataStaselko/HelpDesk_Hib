package com.final_project.staselko.controller;

import com.final_project.staselko.model.entity.Attachment;
import com.final_project.staselko.model.response.AttachmentResponse;
import com.final_project.staselko.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;
    private final String FILE_PATH = "http://localhost:8080/files/";


    @PostMapping
    public ResponseEntity<Void>uploadAttachments(@RequestParam(value = "files") MultipartFile[] files, @RequestParam(value = "ticketId") Long ticketId){
        attachmentService.saveAllAttachment(files, ticketId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getAttachmentByTicket(@PathVariable(value = "id") Long attachmentId,
                                                          @RequestParam(value = "file") String token){
        final Attachment attachment = attachmentService.getAttachmentById(attachmentId);
        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        String mime = fileTypeMap.getContentType(attachment.getName());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mime))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"")
                .body(new ByteArrayResource(attachment.getBlob()));
    }

    @GetMapping
    public ResponseEntity<List<AttachmentResponse>> getAllAttachmentsByTicket(@RequestParam(value = "ticketId") Long ticketId) {
        List<AttachmentResponse> list = attachmentService.getAllAttachment(ticketId).stream()
                .map(attachment -> new AttachmentResponse(attachment.getId(), attachment.getName(), FILE_PATH + attachment.getId(),
                        attachment.getName().substring(attachment.getName().lastIndexOf(".") + 1)))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable(value = "id") Long attachmentId){
        attachmentService.deleteAttachment(attachmentId);
        return ResponseEntity.ok().build();
    }

}
