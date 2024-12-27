package com.example.api.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "weeds")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Weed {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scientific_name")
    private String scientificName;
    
    @Column(name = "popular_name")
    private String popularName;

    private String description;

    @Column(name = "quimic_component")
    private String quimicComponent;

    @OneToMany(mappedBy = "weed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Weed_Image> images;

}
