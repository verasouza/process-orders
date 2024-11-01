package com.vsouza.processorders.service;

import com.vsouza.processorders.dto.request.OrderFileRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProcessOrderFiles {

	List<OrderFileRequest> processFile(MultipartFile file);
}
