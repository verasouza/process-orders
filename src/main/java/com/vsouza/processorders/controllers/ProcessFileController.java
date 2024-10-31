package com.vsouza.processorders.controllers;

import com.vsouza.processorders.service.ProcessOrderFiles;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/files")
public class ProcessFileController {

    final ProcessOrderFiles processOrderFiles;


    @PostMapping("/upload")
    public ResponseEntity processFile(@RequestParam("file") MultipartFile file) {
        processOrderFiles.processFile(file);
        return ResponseEntity.ok().build();
    }

}
