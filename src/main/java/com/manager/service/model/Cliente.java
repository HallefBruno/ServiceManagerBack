package com.manager.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Entity
@ToString
public class Cliente implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private String nome;
    
    @CPF(message = "{campo.cpf.invalido}")
    @NotNull(message = "{campo.cpf.obrigatorio}")
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_cadastro", updatable = false)
    private LocalDate dataCadastro;
    
    @PrePersist
    @PreUpdate
    private void prePersistPreUpdate() {
        this.cpf = StringUtils.getDigits(this.cpf);
        this.nome = StringUtils.strip(this.nome);
    }
    
}
