package com.example.api.entities;

import java.sql.Date;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "analysies")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Analysis {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_analysis")
    private Long idAnalysis;

    @Column(name = "id_user")
    private Long idUser;

    // @Column(name = "id_especie")
    @Column(name = "id_weed")
    @ManyToMany
    @JoinTable(
        name = "analysis_weed",
        joinColumns = @JoinColumn(name = "id_analysis"),
        inverseJoinColumns = @JoinColumn(name = "weed_id")
    )
    private Long idWeed;
    
    @Nonnull
    private Boolean result;

    @Nonnull
    private Date analysis_date;
    
}
