package com.juriconnect.model;

public enum LegalArea {
    PENAL("Direito Penal"),
    COMERCIAL("Direito Comercial"),
    CIVIL("Direito Civil"),
    TRABALHISTA("Direito Trabalhista"),
    ADMINISTRATIVO("Direito Administrativo"),
    IMOBILIARIO("Direito Imobiliário"),
    FISCAL("Direito Fiscal"),
    DIREITOS_HUMANOS("Direitos Humanos"),
    MARITIMO("Direito Marítimo"),
    AMBIENTAL("Direito Ambiental");
    
    private final String description;
    
    LegalArea(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}

