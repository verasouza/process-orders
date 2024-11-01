package com.vsouza.processorders;

import com.vsouza.processorders.dto.request.OrderFileRequest;
import com.vsouza.processorders.service.impl.ProcessOrderFilesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProcessOrderFileTest {

	@Mock
	ProcessOrderFilesImpl processOrderFiles;

	@Mock
	OrderFileRequest orderFileRequest;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldProcessOrderFilesAndThenReturnOrders() {

		//simular anexo
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "orderFile", "text/plain", "orderFile".getBytes());
		List<OrderFileRequest> orderFileRequests = List.of(
				OrderFileRequest.builder()
						.orderId(2L)
						.userId(2L)
						.userName("user")
						.productId(2L)
						.value(BigDecimal.TEN)
						.date(LocalDate.now())
						.build()
				);

		when(processOrderFiles.processFile(mockMultipartFile)).thenReturn(orderFileRequests);

		List<OrderFileRequest> result = processOrderFiles.processFile(mockMultipartFile);
		assertEquals(orderFileRequests, result);

	}
}
