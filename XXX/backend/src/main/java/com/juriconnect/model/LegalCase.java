package com.juriconnect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "legal_cases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LegalCase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lawyer_id", nullable = false)
    private Lawyer lawyer;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LegalArea legalArea;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CaseStatus status = CaseStatus.PROCESSO_INICIADO;
    
    @OneToMany(mappedBy = "legalCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();
    
    @OneToMany(mappedBy = "legalCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum CaseStatus {
        PROCESSO_INICIADO("Processo Iniciado"),
        DOCUMENTACAO_RECEBIDA("Documentação Recebida"),
        PETICAO_PROTOCOLADA("Petição Protocolada"),
        AUDIENCIA_MARCADA("Audiência Marcada"),
        JULGAMENTO_REALIZADO("Julgamento Realizado"),
        SENTENCA_PROFERIDA("Sentença Proferida"),
        ACORDO_EXTRAJUDICIAL("Acordo Extrajudicial"),
        EXECUCAO_SENTENCA("Execução de Sentença"),
        ENCERRADO("Encerrado");
        
        private final String description;
        
        CaseStatus(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
}

