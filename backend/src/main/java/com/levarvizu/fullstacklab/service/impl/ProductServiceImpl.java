package com.levarvizu.fullstacklab.service.impl;

import com.levarvizu.fullstacklab.dto.ProductRequest;
import com.levarvizu.fullstacklab.dto.ProductResponse;
import com.levarvizu.fullstacklab.entity.Product;
import com.levarvizu.fullstacklab.mapper.ProductMapper;
import com.levarvizu.fullstacklab.repository.ProductRepository;
import com.levarvizu.fullstacklab.kafka.ProductKafkaProducer;
import com.levarvizu.fullstacklab.dto.ProductCreatedEvent;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.levarvizu.fullstacklab.exception.ProductNotFoundException;
import com.levarvizu.fullstacklab.service.ProductService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductKafkaProducer producer;

    public ProductServiceImpl(ProductRepository repository, ProductKafkaProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    @Override
    public ProductResponse create(ProductRequest request) {

        Product product
                = ProductMapper.toEntity(request);

        Product saved = repository.save(product);

        producer.sendProductCreated(
                new ProductCreatedEvent(
                        saved.getId(),
                        saved.getName(),
                        saved.getPrice()
                )
        );

        return ProductMapper.toResponse(saved);
    }

    @Override
    public List<ProductResponse> findAll() {

        return repository
                .findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable("products")
    public ProductResponse findById(Long id) {

        System.out.println(
                "Consultando PostgreSQL..."
        );

        Product product
                = repository.findById(id)
                        .orElseThrow(
                                () -> new ProductNotFoundException(id)
                        );

        return ProductMapper.toResponse(product);
    }

    @Override
    @CacheEvict(
            value = "products",
            key = "#id"
    )
    public ProductResponse update(
            Long id,
            ProductRequest request
    ) {

        Product product
                = repository.findById(id)
                        .orElseThrow(
                                ()
                                -> new ProductNotFoundException(id)
                        );

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());

        Product updated
                = repository.save(product);

        return ProductMapper.toResponse(updated);
    }

    @Override
    @CacheEvict(
            value = "products",
            key = "#id"
    )
    public void delete(Long id) {

        Product product
                = repository.findById(id)
                        .orElseThrow(
                                ()
                                -> new ProductNotFoundException(id)
                        );

        repository.delete(product);
    }
}
