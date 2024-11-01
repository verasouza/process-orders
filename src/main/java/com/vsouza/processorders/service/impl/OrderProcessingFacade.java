package com.vsouza.processorders.service.impl;

import com.vsouza.processorders.dto.request.OrderFileRequest;
import com.vsouza.processorders.service.OrderProcessing;
import com.vsouza.processorders.service.ProcessOrderFiles;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class OrderProcessingFacade {

	private final ProcessOrderFiles processOrderFiles;
	private final OrderProcessing orderProcessing;

	public OrderProcessingFacade(ProcessOrderFiles processOrderFiles, OrderProcessing orderProcessing) {
		this.processOrderFiles = processOrderFiles;
		this.orderProcessing = orderProcessing;
	}

	public void processFileAndSaveOrders(MultipartFile multipartFile) {
		List<OrderFileRequest> orderFileRequests = processOrderFiles.processFile(multipartFile);
		orderProcessing.processMultipleOrders(orderFileRequests);

	}
}
