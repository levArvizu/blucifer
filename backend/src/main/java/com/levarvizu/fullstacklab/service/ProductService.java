package com.levarvizu.fullstacklab.service;

import com.levarvizu.fullstacklab.dto.ProductRequest;
import com.levarvizu.fullstacklab.dto.ProductResponse;
import java.util.List;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    List<ProductResponse> findAll();

    ProductResponse findById(Long id);
    
    ProductResponse update( Long id, ProductRequest request);

    void delete(Long id);
}