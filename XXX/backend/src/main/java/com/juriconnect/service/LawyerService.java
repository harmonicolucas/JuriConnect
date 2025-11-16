package com.juriconnect.service;

import com.juriconnect.dto.LawyerDTO;
import com.juriconnect.model.LegalArea;
import com.juriconnect.model.Lawyer;
import com.juriconnect.repository.LawyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LawyerService {
    
    @Autowired
    private LawyerRepository lawyerRepository;
    
    public List<LawyerDTO> getAvailableLawyers(LegalArea legalArea) {
        List<Lawyer> lawyers = lawyerRepository.findBySpecializationsContaining(legalArea);
        return lawyers.stream()
                .filter(lawyer -> lawyer.getActive())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<LawyerDTO> getAllLawyers() {
        return lawyerRepository.findByActiveTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public LawyerDTO getLawyerById(Long id) {
        Lawyer lawyer = lawyerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advogado não encontrado"));
        return convertToDTO(lawyer);
    }
    
    private LawyerDTO convertToDTO(Lawyer lawyer) {
        LawyerDTO dto = new LawyerDTO();
        dto.setId(lawyer.getId());
        dto.setFullName(lawyer.getFullName());
        dto.setEmail(lawyer.getEmail());
        dto.setContact(lawyer.getContact());
        dto.setOamNumber(lawyer.getOamNumber());
        dto.setSpecializations(lawyer.getSpecializations());
        dto.setPhotoPath(lawyer.getPhotoPath());
        dto.setCasesCompleted(lawyer.getCasesCompleted());
        dto.setAverageRating(lawyer.getAverageRating());
        dto.setTotalRatings(lawyer.getTotalRatings());
        return dto;
    }
}

