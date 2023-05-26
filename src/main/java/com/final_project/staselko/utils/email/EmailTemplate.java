package com.final_project.staselko.utils.email;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailTemplate {
    private String subject;
    private String textPart1;
    private String textPart2;
    private String href;
    private String textPart3;
    private String[] emails;
}
