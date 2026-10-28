package com.levarvizu.fullstacklab.mapper;

import com.levarvizu.fullstacklab.dto.ProductRequest;
import com.levarvizu.fullstacklab.dto.ProductResponse;
import com.levarvizu.fullstacklab.entity.Product;

public class ProductMapper {

    public static Product toEntity(
            ProductRequest request
    ) {

        Product product = new Product();

        product.setName(
                request.name()
        );

        product.setDescription(
                request.description()
        );

        product.setPrice(
                request.price()
        );

        return product;
    }

    public static ProductResponse toResponse(
            Product product
    ) {

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

}
