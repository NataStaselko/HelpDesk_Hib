package com.final_project.staselko.controller;

import com.final_project.staselko.model.enums.Urgency;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/urgencies")
public class EnumController {
    @GetMapping
    public ResponseEntity<List<String>> getAllUrgency() {
        List<String> list = new ArrayList<>();
        list.add(Urgency.CRITICAL.name());
        list.add(Urgency.HIGH.name());
        list.add(Urgency.AVERAGE.name());
        list.add(Urgency.LOW.name());
        return ResponseEntity.ok(list);
    }
}
