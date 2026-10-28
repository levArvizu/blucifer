package com.levarvizu.fullstacklab.controller;

import com.levarvizu.fullstacklab.dto.ProductRequest;
import com.levarvizu.fullstacklab.dto.ProductResponse;
import com.levarvizu.fullstacklab.service.ProductService;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize(
            "hasRole('ADMIN')"
    )
    public ProductResponse create(
            @Valid
            @RequestBody ProductRequest request
    ) {
        return service.create(request);
    }

    @GetMapping
    @PreAuthorize(
            "hasRole('ADMIN')"
    )
    public List<ProductResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponse findById(
            @PathVariable Long id
    ) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize(
            "hasRole('ADMIN')"
    )
    public ProductResponse update(
            @PathVariable Long id,
            @Valid
            @RequestBody ProductRequest request
    ) {

        return service.update(
                id,
                request
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(
            "hasRole('ADMIN')"
    )
    public void delete(
            @PathVariable Long id
    ) {
        service.delete(id);
    }
}
