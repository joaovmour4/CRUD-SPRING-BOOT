package com.example.api.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.api.entities.Weed;

public interface WeedRepository extends JpaRepository<Weed, Long> {
    @Query("SELECT w FROM Weed w JOIN FETCH w.images WHERE w.id = :id")
    Optional<Weed> findByIdWithImages(@Param("id") Long id);

    @Query("SELECT w FROM Weed w WHERE w.id IN :weeds")
    Set<Weed> findAllByIds(@Param("weeds") List<Long> weeds);
    
    @Query("SELECT w FROM Weed w WHERE w.scientificName IN :weeds")
    Set<Weed> findAllByNames(@Param("weeds") List<String> weeds);

    @Query("SELECT w FROM Weed w JOIN FETCH w.images")
    Set<Weed> findAllWithImages();

    // Optional<Weed> findByScientificName(String name);

    Optional<Weed> findByPopularName(String name);
}
