package com.example.api.repositories;

import com.example.api.entities.ProductVariation;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {
    @Query("select pv from ProductVariation pv where pv.product.id = :productId and pv.id= :productVariationId")
    Optional<ProductVariation> findByProductIdAdProductVariationId(@Param("productId") Long productId, @Param("productVariationId") Long productVariationId);
}
