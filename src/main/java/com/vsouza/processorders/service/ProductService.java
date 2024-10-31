package com.vsouza.processorders.service;

import com.vsouza.processorders.dto.entities.Product;
import com.vsouza.processorders.dto.mappers.ProductMapper;
import com.vsouza.processorders.dto.model.FileRequest;
import com.vsouza.processorders.dto.model.ProductResponse;
import com.vsouza.processorders.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class ProductService {

	final ProductRepository productRepository;
	final ProductMapper productMapper;

	private Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow();
	}

	public ProductResponse getProductResponseById(Long id) {
		Product product = getProductById(id);
		return productMapper.toProductResponse(product);

	}

	public List<ProductResponse> getProductResponseList() {
		List<Product> products = productRepository.findAll();
		List<ProductResponse> productResponses = new ArrayList<>();
		for (Product product : products) {
			productResponses.add(productMapper.toProductResponse(product));
		}
		return productResponses;
	}

	public Product processProduct(FileRequest orderData) {
		return productRepository.findById(orderData.getProductId())
				.orElseGet(() -> {
					Product newProduct = new Product();
					newProduct.setId(orderData.getProductId());
					newProduct.setProductPrice(orderData.getValue());
					newProduct.setOrderProducts(new ArrayList<>());
					return productRepository.save(newProduct);
				});
	}

	public List<ProductResponse> productResponses(List<Product> products) {
		List<ProductResponse> productResponses = new ArrayList<>();
		for (Product product : products) {
			productResponses.add(productMapper.toProductResponse(product));
		}
		return productResponses;
	}
}


