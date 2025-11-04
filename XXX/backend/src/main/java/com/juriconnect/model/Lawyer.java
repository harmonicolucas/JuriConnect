package com.juriconnect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lawyers")
@PrimaryKeyJoinColumn(name = "user_id")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Lawyer extends User {
    
    @Column(nullable = false, unique = true)
    private String oamNumber; // Número da carteira da OAM
    
    @Column(nullable = false)
    private String oamDocumentPath; // Caminho do documento da OAM
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "lawyer_specializations", joinColumns = @JoinColumn(name = "lawyer_id"))
    @Column(name = "specialization")
    private Set<LegalArea> specializations = new HashSet<>();
    
    private String photoPath;
    
    private Integer casesCompleted = 0;
    
    private Double averageRating = 0.0;
    
    private Integer totalRatings = 0;
}

