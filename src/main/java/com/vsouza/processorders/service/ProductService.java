package com.vsouza.processorders.service;

import com.vsouza.processorders.entities.Product;
import com.vsouza.processorders.mappers.ProductMapper;
import com.vsouza.processorders.dto.request.OrderFileRequest;
import com.vsouza.processorders.dto.response.ProductResponse;
import com.vsouza.processorders.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class ProductService {

	final ProductRepository productRepository;
	final ProductMapper productMapper;

	private Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow();
	}

	public Product processProduct(OrderFileRequest orderData) {
		return productRepository.findById(orderData.getProductId())
				.orElseGet(() -> {
					Product newProduct = new Product();
					newProduct.setId(orderData.getProductId());
					newProduct.setProductPrice(orderData.getValue());
					newProduct.setOrderProducts(new ArrayList<>());
					return productRepository.save(newProduct);
				});
	}

}


