package com.final_project.staselko.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttachmentResponse {
    private Long id;
    private String name;
    private String path;
    private String type;
}
