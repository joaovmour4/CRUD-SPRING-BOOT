package com.example.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.api.entities.Weed;

public interface WeedRepository extends JpaRepository<Weed, Long> {
    @Query("SELECT w FROM Weed w JOIN FETCH w.images WHERE w.id = :id")
    Optional<Weed> findByIdWithImages(@Param("id") Long id);

    Optional<Weed> findByName(String name);
}
