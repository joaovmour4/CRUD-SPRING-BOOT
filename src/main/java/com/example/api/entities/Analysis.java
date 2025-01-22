package com.example.api.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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
    private Long id;

    private String thumbnail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    @Column(name = "id_user")
    private Long idUser;

    @Nonnull
    private Boolean result;
    
    @Nonnull
    private Date analysis_date;
    
    @Builder.Default
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
        name = "analysis_weed",
        joinColumns = @JoinColumn(name = "analysis_id"),
        inverseJoinColumns = @JoinColumn(name = "weed_id")
    )
    private Set<Weed> weeds = new HashSet<>();
    
}
