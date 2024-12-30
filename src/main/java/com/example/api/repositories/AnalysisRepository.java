package com.example.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.api.entities.Analysis;

@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    
    @Query("SELECT a FROM Analysis JOIN FETCH a.weeds WHERE a.id = :id")
    Optional<Analysis> findByIdWithResultWeeds(@Param("id") Long id);

}
