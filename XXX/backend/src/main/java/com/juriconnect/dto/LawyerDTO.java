package com.juriconnect.dto;

import com.juriconnect.model.LegalArea;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LawyerDTO {
    private Long id;
    private String fullName;
    private String email;
    private String contact;
    private String oamNumber;
    private Set<LegalArea> specializations;
    private String photoPath;
    private Integer casesCompleted;
    private Double averageRating;
    private Integer totalRatings;
}

